package com.example.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

  private int ms = 0;
  private int seconds = 0;
  private int minutes = 0;

  private Button btnStart;
  private Button btnReset;

  private TextView timerText;

  private Timer timer;

  private boolean running = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    timerText = findViewById(R.id.timer_text);

    btnStart = findViewById(R.id.btn_start);
    btnReset = findViewById(R.id.btn_reset);


    btnStart.setOnClickListener(onStartTimer);
    btnReset.setOnClickListener(onResetTimer);

    updateTimerText();

  }

  View.OnClickListener onResetTimer = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      stopTimer();
      ms = 0;
      seconds = 0;
      minutes = 0;
      updateTimerText();
    }
  };

  View.OnClickListener onStartTimer = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if(running){
        stopTimer();
      } else {
        startTimer();
      }
    }
  };

  private void stopTimer(){
    running = false;
    btnStart.setText(R.string.btn_start);
    timer.cancel();
  }

  private void startTimer(){
    timer = new Timer();
    btnStart.setText(R.string.btn_stop);
    running = true;
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        runTimer();
      }
    }, 0, 100);
  }

  private void runTimer(){
    this.runOnUiThread(timerTick);
  }

  private void updateMs(){
    ms++;
    if(ms == 10){
      updateSeconds();
    }
  }

  private void updateSeconds(){
    ms = 0;
    seconds++;
    if(seconds == 60){
      seconds = 0;
      minutes++;
    }
  }

  private void updateTimerText(){
    timerText.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d", minutes, seconds,ms));
  }

  private Runnable timerTick = new Runnable() {
    @Override
    public void run() {
      updateTimerText();
      updateMs();
    }
  };
}
