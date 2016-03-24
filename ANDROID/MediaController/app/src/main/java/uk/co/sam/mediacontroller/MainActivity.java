package uk.co.sam.mediacontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Button> mButtons;
    private BluetoothHandler mBluetoothHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button button0 = (Button) findViewById(R.id.main_button_0);
        button0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("p");
            }
        });
        final Button button1 = (Button) findViewById(R.id.main_button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("t");
            }
        });
        final Button button2 = (Button) findViewById(R.id.main_button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("n");
            }
        });
        final Button button3 = (Button) findViewById(R.id.main_button_3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("u");
            }
        });
        final Button button4 = (Button) findViewById(R.id.main_button_4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("d");
            }
        });

        mButtons = new ArrayList<>();
        ViewGroup layout = (ViewGroup) findViewById(R.id.main_layout);
        int childCount = layout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                mButtons.add((Button) child);
            }
        }
        disableButtons();
        mBluetoothHandler = new BluetoothHandler(this, toolbar);
    }

    public static void disableButtons() {
        for (Button button : mButtons) {
            button.setEnabled(false);
        }
    }

    public static void enableButtons() {
        for (Button button : mButtons) {
            button.setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            mBluetoothHandler.onActivityResult(requestCode, resultCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            //noinspection SimplifiableIfStatement
            case R.id.action_connect:
                if (mBluetoothHandler.isEnabled()) {
                    mBluetoothHandler.showPairedDialog();
                } else {
                    mBluetoothHandler.enableBluetooth();
                }
                return true;
            case R.id.action_disconnect:
                if (mBluetoothHandler.isEnabled()) {
                    mBluetoothHandler.disconnectDevice();
                } else {
                    Snackbar.make(findViewById(R.id.action_disconnect), "Thing here", Snackbar.LENGTH_SHORT).show();
                }
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}