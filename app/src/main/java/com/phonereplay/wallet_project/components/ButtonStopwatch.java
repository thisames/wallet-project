package com.phonereplay.wallet_project.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import com.phonereplay.wallet_project.MainActivity;
import java.util.Timer;
import java.util.TimerTask;

public class ButtonStopwatch extends AppCompatButton {

  private final Timer time = new Timer();

  private int timeStamp = 3000;

  private Activity activity;

  public ButtonStopwatch(@NonNull Context context) {
    super(context);
  }

  public ButtonStopwatch(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public ButtonStopwatch(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void initialize(Activity activity) {
    this.activity = activity;
    setEnabled(false);
    start();
  }

  private void start() {
    if (activity == null) {
      throw new IllegalStateException("Activity must be set before starting the stopwatch.");
    }

    time.schedule(
        new TimerTask() {

          @Override
          public void run() {
            if (timeStamp == 0) {
              time.cancel();
              activity.runOnUiThread(
                  () -> {
                    setEnabled(true);
                    setText(android.R.string.ok);
                    setOnClickListener(
                        v -> {
                          Intent intent = new Intent(activity, MainActivity.class);
                          activity.startActivity(intent);
                        });
                  });
            } else {
              String secondText = timeStamp / 1000 + "s";
              activity.runOnUiThread(() -> setText(secondText));
              timeStamp -= 1000;
            }
          }
        },
        0,
        1000);
  }

  public void setTimeStamp(int timeStamp) {
    timeStamp = timeStamp * 1000;
    this.timeStamp = timeStamp;
  }
}
