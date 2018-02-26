#  Screenshots
![Image 1](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%205/images/01.png)
![Image 2](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%205/images/02.png)
![Image 3](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%205/images/03.png)
![Image 4](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%205/images/04.png)
![Image 5](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%205/images/05.png)
![Image 6](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%205/images/06.png)
#  Activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context="com.sathish.myslideshow.MainActivity">


   <android.support.constraint.Guideline
       android:id="@+id/guideline"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:orientation="horizontal"
       app:layout_constraintGuide_percent="0.35" />

   <ImageView
       android:id="@+id/imageView"
       android:layout_width="0dp"
       android:layout_height="0dp"
       android:layout_marginBottom="8dp"
       android:layout_marginEnd="8dp"
       android:layout_marginStart="8dp"
       android:layout_marginTop="8dp"
       android:scaleType="fitXY"
       app:layout_constraintBottom_toTopOf="@+id/guideline"
       app:layout_constraintEnd_toEndOf="parent"
       app:layout_constraintStart_toStartOf="parent"
       app:layout_constraintTop_toTopOf="parent"
       app:srcCompat="@drawable/logo" />

   <TextView
       android:id="@+id/textView"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_marginEnd="32dp"
       android:layout_marginStart="32dp"
       android:layout_marginTop="24dp"
       android:text="Photo Album Slide Show"
       android:textSize="16sp"
       app:layout_constraintEnd_toEndOf="parent"
       app:layout_constraintStart_toStartOf="parent"
       app:layout_constraintTop_toTopOf="@+id/guideline" />

   <SeekBar
       android:id="@+id/seekBar"
       android:layout_width="0dp"
       android:layout_height="wrap_content"
       android:layout_marginEnd="32dp"
       android:layout_marginStart="32dp"
       android:layout_marginTop="32dp"
       app:layout_constraintEnd_toEndOf="parent"
       app:layout_constraintStart_toStartOf="parent"
       app:layout_constraintTop_toBottomOf="@+id/textView"
       android:max="20"/>

   <TextView
       android:id="@+id/timeTextView"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_marginEnd="32dp"
       android:layout_marginStart="32dp"
       android:layout_marginTop="32dp"
       android:text="0"
       app:layout_constraintEnd_toEndOf="parent"
       app:layout_constraintStart_toStartOf="parent"
       app:layout_constraintTop_toBottomOf="@+id/seekBar" />

   <Button
       android:id="@+id/button"
       android:layout_width="0dp"
       android:layout_height="wrap_content"
       android:layout_marginEnd="32dp"
       android:layout_marginStart="32dp"
       android:layout_marginTop="32dp"
       android:onClick="btnClick"
       android:text="Start Slide Show"
       app:layout_constraintEnd_toEndOf="parent"
       app:layout_constraintStart_toStartOf="parent"
       app:layout_constraintTop_toBottomOf="@+id/timeTextView" />
</android.support.constraint.ConstraintLayout>

```
# MainActivity.java
```
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
```
# Activityslideshow.xml
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context="com.sathish.myslideshow.SlideShowActivity">
```
