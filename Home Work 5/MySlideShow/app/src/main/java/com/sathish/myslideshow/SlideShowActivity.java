package com.sathish.myslideshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SlideShowActivity extends AppCompatActivity {
    String MY_PREFS_NAME = "com.sathish.sharedpreferencetest.pref";
    TextView cityNameTxt, cityDescTxt, intervalTxt;
    Button indexBtn;
    ImageView cityImage;
    int seekBarVal;
    int index = 0;
    CountDownTimer countDownTimer;
    boolean isTimerRunning = false;

    String[] cityNameArray = new String[] { "Amsterdam", "Berlin", "Copenhagen", "Lasvegas", "London", "Newyork", "Paris", "San francisco", "Venice", "Washington"};

    String[] cityDescArray = new String[]{  "Beautiful canals, houses & coffee shops",
                                            "Berlin Wall",
                                            "Tivoli Gardens, The freetown of Christiania",
                                            "Gambling, shopping",
                                            "Clock Tower, London Eye",
                                            "Times Square, Empire State Building",
                                            "Eiffel Tower",
                                            "Golden Gate Bridge",
                                            "The Floating City",
                                            "U.S. Capitol Building"};

    int[] cityImageArray = new int[] {  R.drawable.amsterdam,
                                        R.drawable.berlin,
                                        R.drawable.copenhagen,
                                        R.drawable.lasvegas,
                                        R.drawable.london,
                                        R.drawable.newyork,
                                        R.drawable.paris,
                                        R.drawable.sanfrancisco,
                                        R.drawable.venice,
                                        R.drawable.washington};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        cityNameTxt = (TextView) findViewById(R.id.cityNameTxt);
        cityDescTxt = (TextView) findViewById(R.id.cityDescTxt);
        intervalTxt = (TextView) findViewById(R.id.intervalTxt);
        cityImage = (ImageView) findViewById(R.id.cityImage);
        indexBtn = (Button) findViewById(R.id.indexBtn);

        Intent intent = getIntent();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        seekBarVal = prefs.getInt("seekBarVal", 0);
        if (seekBarVal == 0) {
            seekBarVal = 1;
        }
        intervalTxt.setText("Slide Interval : "+ Integer.toString(seekBarVal)+" sec");
        startSlideShow();
    }

    public void startSlideShow(){
        cityNameTxt.setText(cityNameArray[index]);
        cityDescTxt.setText(cityDescArray[index]);
        cityImage.setImageBitmap(null);
        cityImage.setImageResource(cityImageArray[index]);
        indexBtn.setText("Index -> "+ Integer.toString(index+1));
        startTimer();
    }

    public void startTimer(){
        if(isTimerRunning == true){
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(seekBarVal*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                isTimerRunning = true;
            }
            public void onFinish() {
                index++;
                if(index >= cityNameArray.length){
                    index=0;
                }
                isTimerRunning = false;
                startSlideShow();
            }
        }.start();
        isTimerRunning = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed(){
        if(isTimerRunning == true){
            countDownTimer.cancel();
        }
        countDownTimer.cancel();
        finish();
    }
}
