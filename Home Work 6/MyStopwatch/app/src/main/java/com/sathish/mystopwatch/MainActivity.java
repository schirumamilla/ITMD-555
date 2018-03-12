package com.sathish.mystopwatch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ServiceClass myService;
    private Intent intent;
    Button startService, stopService, startTime, stopTime, resetTime;
    Boolean isBound = false, isRunning = false;
    TextView timeText;
    private int secs = 0;
    private int mins = 0;
    private int millis = 0;
    private long currentTime = 0L;
    public static Handler sHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService = (Button) findViewById(R.id.startService);
        stopService = (Button) findViewById(R.id.stopService);
        startTime = (Button) findViewById(R.id.startTime);
        stopTime = (Button) findViewById(R.id.stopTime);
        resetTime = (Button) findViewById(R.id.resetTime);
        timeText = (TextView) findViewById(R.id.timeText);

        intent = new Intent(this, ServiceClass.class);

        MainActivity.sHandler = new Handler() {

            @Override
            public void handleMessage(Message timeMsg) {
                super.handleMessage(timeMsg);
                Log.v("time",timeMsg.obj.toString());
                currentTime = Long.valueOf(timeMsg.obj.toString());

                secs = (int) (currentTime / 1000);
                mins = secs / 60;
                secs = secs % 60;
                millis = (int) (currentTime % 1000);
                updatetime();
            }
        };
        disableButton();
    }

    public void startService(View view){
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    public void stopService(View view){
        if(isBound){
            unbindService(myConnection);
            stopService(new Intent(getApplicationContext(),ServiceClass.class));
            isBound = false;
            isRunning = false;
        }
        disableButton();
    }

    public void startTime(View view){
        if(isBound){
            myService.start();
            isRunning = true;
        }
        disableButton();
    }

    public void stopTime(View view){
        if(isBound){
            myService.stop();
            isRunning = false;
        }
        disableButton();
    }

    public void resetTime(View view){
        if(isBound){
            myService.reset();
            isRunning = false;
        }
        disableButton();
    }

    public void updatetime(){
        timeText.setText("" + mins + ":" + String.format("%02d", secs) + ":"
                + String.format("%03d", millis));
    }

    public  void disableButton(){
        if(isBound){
            startService.setEnabled(false);
            stopService.setEnabled(true);
            if(isRunning){
                startTime.setEnabled(false);
                stopTime.setEnabled(true);
                resetTime.setEnabled(true);
            }else{
                startTime.setEnabled(true);
                stopTime.setEnabled(false);
                resetTime.setEnabled(true);
            }
        }else{
            startService.setEnabled(true);
            stopService.setEnabled(false);
            startTime.setEnabled(false);
            stopTime.setEnabled(false);
            resetTime.setEnabled(false);
        }
    }

    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            ServiceClass.LocalBinder binder = (ServiceClass.LocalBinder) service;
            myService = binder.getService();
            isBound = true;
            disableButton();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            myService.stop();
            isBound = false;
        }
    };
}
