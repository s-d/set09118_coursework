package uk.co.sam.mediacontroller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        assert button0 != null;
        button0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("p");
            }
        });
        final Button button1 = (Button) findViewById(R.id.main_button_1);
        assert button1 != null;
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("t");
            }
        });
        final Button button2 = (Button) findViewById(R.id.main_button_2);
        assert button2 != null;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothHandler.writeValue("n");
            }
        });
        final Button button3 = (Button) findViewById(R.id.main_button_3);
        assert button3 != null;
        button3.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }
            Runnable mAction = new Runnable() {
                @Override public void run() {
                    mBluetoothHandler.writeValue("u");
                    mHandler.postDelayed(this, 50);
                }
            };
        });
        final Button button4 = (Button) findViewById(R.id.main_button_4);
        assert button4 != null;
        button4.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }
            Runnable mAction = new Runnable() {
                @Override public void run() {
                    mBluetoothHandler.writeValue("d");
                    mHandler.postDelayed(this, 50);
                }
            };
        });

        mFunctionButtons = new ArrayList<>();
        mOtherButtons = new ArrayList<>();
        ViewGroup layout = (ViewGroup) findViewById(R.id.main_button_layout);
        assert layout != null;
        int childCount = layout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                mFunctionButtons.add((Button) child);
            }
        }

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        final Button buttonConnect = new Button(this);
        buttonConnect.setText(R.string.main_activity_connect_button);
        assert mainLayout != null;
        mainLayout.addView(buttonConnect);
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) buttonConnect.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        buttonConnect.setLayoutParams(layoutParams);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mBluetoothHandler.isEnabled()) {
                    mBluetoothHandler.showPairedDevicesDialog();
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
        int id = item.getItemId();
        switch (id) {
            case R.id.action_disconnect:
                    mBluetoothHandler.disconnectDevice();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        mBluetoothHandler.disconnectDevice();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        hideButtons();
    }

}