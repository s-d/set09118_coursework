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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothHandler {

    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805f9b34fb");
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private View mView;
    private Activity mActivity;
    private BluetoothDevice mDevice;
    private BluetoothSocket mSocket;
    private OutputStream mOutput;
    private ProgressDialog progress;


    //class constructor
    public BluetoothHandler(Activity activity, View view) {
        //get activity resources
        this.mView = view;
        this.mActivity = activity;
        //check mobile device has a Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            //tell user no adapter is available
            Snackbar.make(this.mView, "Bluetooth unavailable on this device.", Snackbar.LENGTH_LONG).show();
        }
    }

    //turns on Bluetooth
    public void enableBluetooth() {
        //check if Bluetooth is already enabled
        if (!isEnabled()) {
            //start Bluetooth
            Intent btOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //inform activity that bluetooth has been started
            mActivity.startActivityForResult(btOnIntent, REQUEST_ENABLE_BT);
        } else {
            //do nothing
        }
    }

    //returns whether bluetooth is on or off
    public boolean isEnabled() {
        return mBluetoothAdapter.isEnabled();
    }

    //result method as to whether bluetooth was successfully turned on
    protected void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == REQUEST_ENABLE_BT) {
            //if bluetooth could not be enabled
            if (resultCode != Activity.RESULT_OK) {
                //tell user
                Snackbar.make(mView, "Could not enable Bluetooth", Snackbar.LENGTH_LONG).show();
            } else {
                //show previously paired devices to user
                showPairedDevicesDialog();
            }
        }
    }

    //shows a dialog of paired bluetooth devices
    public void showPairedDevicesDialog() {
        //define various dialog attributes
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
        final LayoutInflater inflater = mActivity.getLayoutInflater();
        final View dialogLayout =
                inflater.inflate(R.layout.paired_dialog,
                        (ViewGroup) mActivity.findViewById(R.id.paired_dialog_list));
        final ListView pairedListView = (ListView) dialogLayout
                .findViewById(R.id.paired_dialog_list);
        final Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();
        final ArrayAdapter<String> btArrayAdapter =
                new ArrayAdapter<>(mActivity, android.R.layout.simple_list_item_1);

        //construct the dialog
        dialogBuilder.setTitle("Paired Devices");
        dialogBuilder.setView(dialogLayout);
        pairedListView.setAdapter(btArrayAdapter);
        //fill dialog with every paired device
        for (BluetoothDevice device : pairedDevices) {
            btArrayAdapter.add(device.getName());
        }

        //add cancel button to dialog
        dialogBuilder.setNegativeButton(R.string.bluetooth_handler_paired_dialog,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //close the dialog
                        dialog.dismiss();
                    }
                });

        //create  dialog
        final AlertDialog ad = dialogBuilder.create();

        //set clickListener for every item on the list
        pairedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pairedName = ((TextView) view).getText().toString();
                for (BluetoothDevice device : pairedDevices) {
                    if (pairedName.equals(device.getName())) {
                        //set selected device
                        mDevice = device;
                        //dismiss dialog
                        ad.dismiss();
                        try {
                            //connect to selected device
                            connectDevice();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        });
        //display dialog
        ad.show();
    }

    //connect to a selected device
    void connectDevice() throws IOException {
        //create a progress dialog
        progress = ProgressDialog.show(mActivity, null, "Connecting to " + mDevice.getName(), true);
        //create new thread for connection process
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //connect to device
                    mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
                    mSocket.connect();
                    //open output stream
                    mOutput = mSocket.getOutputStream();
                    //dismiss load dialog
                    progress.dismiss();
                    //tell user connection was successful
                    Snackbar.make(mView, "Connection successful", Snackbar.LENGTH_SHORT).show();

                    //create thread to update UI
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //reveal buttons on main page
                            MainActivity.showButtons();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    //dismiss load bar
                    progress.dismiss();
                    //inform user connection failed
                    Snackbar snack = Snackbar.make(mView, "Connection failed", Snackbar.LENGTH_LONG);
                    //add retry button to snack bar
                    snack.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                //try to connect again
                                connectDevice();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    //show failed snack bar
                    snack.show();
                }
            }
            //start thread
        }).start();
    }

    //disconnect a connected device
    public void disconnectDevice() {
        //check that the socket
        if (mSocket != null) {
            //check socket is in use
            if (mSocket.isConnected()) {
                try {
                    //close output stream
                    mOutput.close();
                    //close socket
                    mSocket.close();
                    //tell user disconnect was successful
                    Snackbar.make(mView, "Disconnected from " + mDevice.getName(), Snackbar.LENGTH_SHORT).show();
                    //hide buttons on UI
                    MainActivity.hideButtons();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //tell user nothing to disconnect
                Snackbar.make(mView, "Not connected to a device", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    //sends a value to connect device
    public void writeValue(String val) {
        try {
            mOutput.write(val.getBytes());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

}