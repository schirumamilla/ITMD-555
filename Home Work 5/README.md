#  Screenshots
![Image 1](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%205/images/01.png)

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
