package com.example.kevin.clocktimer;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    // views/buttons to be changed within the methods of the program
    TextView timeDisplay;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;
    Button countdownButton;
    CountDownTimer countdownTimer;
    ImageView clock;

    // method to reset the timer back to 30 seconds
    public void resetTimer()
    {
        timeDisplay.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countdownButton.setText("START!");
        countdownTimer.cancel();
        counterIsActive = false;
    }

    // starts the countdown from where the user set the seekbar
    public void countdown(View view)
    {
        // checks if the counter is going, otherwise countdown
        if(counterIsActive)
        {
            resetTimer();
        } else
            {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            countdownButton.setText("STOP!");

            countdownTimer = new CountDownTimer((timerSeekBar.getProgress() * 1000) + 100, 1000) {
                @Override
                public void onTick(long l) { updateTimer((int) l / 1000); }

                @Override
                public void onFinish() {
                    MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.buzz);
                    clock = (ImageView)findViewById(R.id.clock);
                    clock.animate().rotationX(720).setDuration(2000);
                    mPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    // updates the timer to the text view
    public void updateTimer(int progress){
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);

        String secondsString = Integer.toString(seconds);
        if(secondsString.equals("0")){ secondsString = "00"; }
        else if(seconds < 10){ secondsString = "0" + secondsString; }

        timeDisplay.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // declares our views/buttons
        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timeDisplay = (TextView)findViewById(R.id.timeDisplay);
        countdownButton = (Button)findViewById(R.id.countdownButton);

        // sets the timer to 30 seconds along with the max being 10 minutes
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
