package com.sathish.mystopwatch;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;

public class ServiceClass extends Service {
    private final IBinder mBinder = new LocalBinder();
    Boolean isRunning = false;
    private long startTime = 0;
    private long timeInMilliseconds = 0;
    private long timeSwapBuff = 0;
    private long updatedTime = 0;
    private Message timeMsg;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isRunning){
            timeSwapBuff += timeInMilliseconds;
            MainActivity.sHandler.removeCallbacks(updateTimer);
            isRunning = false;
        }
    }

    public class LocalBinder extends Binder {
        public ServiceClass getService(){
            return ServiceClass.this;
        }
    }

    public void start(){
        if (!isRunning) {
            startTime = SystemClock.uptimeMillis();
            MainActivity.sHandler.postDelayed(updateTimer, 0);
            isRunning = true;
        }
    }

    public void stop(){
        if(isRunning){
            timeSwapBuff += timeInMilliseconds;
            MainActivity.sHandler.removeCallbacks(updateTimer);
            isRunning = false;
        }
    }

    public void reset(){
        MainActivity.sHandler.removeCallbacks(updateTimer);
        isRunning=false;
        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedTime = 0L;
        timeMsg = new Message();
        timeMsg.obj = updatedTime;
        MainActivity.sHandler.sendMessage(timeMsg);
    }

    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            timeMsg = new Message();
            timeMsg.obj = updatedTime;
            MainActivity.sHandler.sendMessage(timeMsg);
            MainActivity.sHandler.postDelayed(this, 0);
        }
    };
}
