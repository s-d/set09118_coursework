package uk.co.sam.mediacontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Button> mFunctionButtons;
    private static ArrayList<Button> mOtherButtons;

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
                mBluetoothHandler.writeValue("previousTrack");
            }
        });
        final Button button1 = (Button) findViewById(R.id.main_button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("togglePlay");
            }
        });
        final Button button2 = (Button) findViewById(R.id.main_button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("nextTrack");
            }
        });
        final Button button3 = (Button) findViewById(R.id.main_button_3);
        button3.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mBluetoothHandler.writeValue("volumeUp");
                return false;
            }
        });


        final Button button4 = (Button) findViewById(R.id.main_button_4);
        button4.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mBluetoothHandler.writeValue("volumeDown");
                return false;
            }
        });

        mFunctionButtons = new ArrayList<>();
        mOtherButtons = new ArrayList<>();
        ViewGroup layout = (ViewGroup) findViewById(R.id.main_button_layout);
        int childCount = layout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                mFunctionButtons.add((Button) child);
            }
        }

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        final Button buttonConnect = new Button(this);
        buttonConnect.setText("Connect to Media Controller");
        mainLayout.addView(buttonConnect);
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) buttonConnect.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        buttonConnect.setLayoutParams(layoutParams);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mBluetoothHandler.isEnabled()) {
                    mBluetoothHandler.showPairedDialog();
                } else {
                    mBluetoothHandler.enableBluetooth();
                }
            }
        });
        mOtherButtons.add(buttonConnect);
        hideButtons();

        mBluetoothHandler = new BluetoothHandler(this, toolbar);
    }


    public static void hideButtons() {
        for (Button button : mFunctionButtons) {
            button.setVisibility(View.GONE);
        }
        for (Button button : mOtherButtons) {
            button.setVisibility(View.VISIBLE);
        }

    }

    public static void showButtons() {
        for (Button button : mFunctionButtons) {
            button.setVisibility(View.VISIBLE);
        }
        for (Button button : mOtherButtons) {
            button.setVisibility(View.GONE);
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
            case R.id.action_disconnect:
                if (mBluetoothHandler.isConnected()) {
                    mBluetoothHandler.disconnectDevice();
                } else {
                    Snackbar.make(this.findViewById(R.id.main_layout), "Nothing to disconnect", Snackbar.LENGTH_LONG).show();
                }
            case R.id.action_settings:
                hideButtons();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (mBluetoothHandler.isConnected()) {
            showButtons();
        } else {
            hideButtons();
        }
    }

}