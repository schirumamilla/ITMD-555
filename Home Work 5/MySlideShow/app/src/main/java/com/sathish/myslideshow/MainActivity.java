package com.sathish.myslideshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView timeTextView;
    String MY_PREFS_NAME = "com.sathish.sharedpreferencetest.pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        seekBar =(SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateTimeText();
            }
        });
    }

    public void updateTimeText(){
        int seekBarVal = seekBar.getProgress();
        timeTextView.setText(Integer.toString(seekBarVal));
    }

    public void btnClick(View view){
        Intent intent = new Intent(this, SlideShowActivity.class);
        int seekBarVal = seekBar.getProgress();
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("seekBarVal", seekBarVal);
        editor.apply();
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int seekBarVal = prefs.getInt("seekBarVal", 0);
        seekBar.setProgress(seekBarVal);
        updateTimeText();
    }

}
