package uk.co.sam.mediacontroller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Sam Dixon on 22/03/2016.
 */
public class BluetoothHandler {
    private BluetoothHandler bluetoothHandler;
    private BluetoothAdapter bluetoothAdapter;
    private View view;
    private Activity activity;

    public BluetoothHandler(Activity activity, View view) {
        this.view = view;
        this.activity = activity;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Snackbar.make(this.view, "Bluetooth unavailable on this device.", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(this.view, "Bluetooth unavailable on this device.", Snackbar.LENGTH_LONG).show();

        }
    }
}
