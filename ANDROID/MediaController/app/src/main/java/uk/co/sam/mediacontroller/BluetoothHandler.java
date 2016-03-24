package uk.co.sam.mediacontroller;

import android.app.Activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Sam Dixon on 22/03/2016.
 */
public class BluetoothHandler {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private View mView;
    private Activity mActivity;
    private BluetoothDevice mDevice;
    private BluetoothSocket mSocket;
    private OutputStream mOutput;
    private UUID muuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private static final String TAG = "BluetoothUtils";
    private BufferedReader reader;
    private static final String REQUIRED_DEVICE_NAME = "MEDIA_CONTROLLER";


    public BluetoothHandler(Activity activity, View view) {
        this.mView = view;
        this.mActivity = activity;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Snackbar.make(this.mView, "Bluetooth unavailable on this device.", Snackbar.LENGTH_LONG).show();
        }
    }

    public boolean isEnabled() {
        return mBluetoothAdapter.isEnabled();
    }

    public void enableBluetooth() {
        if (!isEnabled()) {
            Intent btOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(btOnIntent, REQUEST_ENABLE_BT);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode != Activity.RESULT_OK) {
                Snackbar.make(mView, "Could not enable Bluetooth", Snackbar.LENGTH_LONG).show();
            } else {
                showPairedDialog();
            }
        }
    }

    public void showPairedDialog() {
        Log.d("bluetooth", "paired devices");

        //define various dialog attributes
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        final LayoutInflater inflater = mActivity.getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.paired_dialog, (ViewGroup) mActivity.findViewById(R.id.paired_dialog_list));
        final ListView pairedListView = (ListView) dialogLayout.findViewById(R.id.paired_dialog_list);
        final Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        final ArrayAdapter<String> btArrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_list_item_1);

        //build dialog
        builder.setTitle("Paired Devices");
        builder.setView(dialogLayout);
        pairedListView.setAdapter(btArrayAdapter);
        for (BluetoothDevice device : pairedDevices) {
            btArrayAdapter.add(device.getName());
        }

        //dialog cancel button
        builder.setNegativeButton(R.string.bluetooth_handler_paired_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        //create  dialog
        final AlertDialog ad = builder.create();

        //clickable list
        pairedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pairedName = ((TextView) view).getText().toString();
                Log.i("Clicked", pairedName);
                for (BluetoothDevice device : pairedDevices) {
                    Log.i("Checking", device.getName());
                    if (pairedName.equals(device.getName())) {
                        mDevice = device;
                        Log.i("BT Device Set", mDevice.getName());
                        ad.dismiss();
                        try {
                            openBT();
                            Log.d("Bluetooth", "attempting connection");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("Bluetooth", "no worky");
                        }
                        break;
                    }
                }
            }
        });
        //display dialog
        ad.show();
    }

    void openBT() throws IOException {
        final ProgressDialog progressDialog = ProgressDialog.show(mActivity, null, "Connecting to Media Controller", true);
        progressDialog.show();
        mSocket = mDevice.createRfcommSocketToServiceRecord(muuid);
        mSocket.connect();
        mOutput = mSocket.getOutputStream();
        progressDialog.dismiss();
        MainActivity.enableButtons();
    }

    void disconnectDevice() {
        if (mSocket != null && mSocket.isConnected()) {
            try {
                mOutput.close();
                mSocket.close();
                MainActivity.disableButtons();
                Snackbar.make(mView, "Disconnected from " + mDevice.getName(), Snackbar.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Snackbar.make(mView, "Nothing to disconnect", Snackbar.LENGTH_SHORT).show();
        }
    }

    void writeValue(String val) {
        try {
            mOutput.write(val.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}