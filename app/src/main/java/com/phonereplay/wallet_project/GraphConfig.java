package com.phonereplay.wallet_project;

import android.os.Bundle;

import android.widget.NumberPicker;
import android.widget.TimePicker;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.TimeUnit;

public class GraphConfig extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_graph_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TimePicker timePicker = findViewById(R.id.timePicker);
        NumberPicker secondsPicker = findViewById(R.id.secondsPicker);

        timePicker.setHour(0);
        timePicker.setMinute(1);
        secondsPicker.setValue(0);

        long updateIntervalMillis = TimeUnit.HOURS.toMillis(timePicker.getHour()) +
                TimeUnit.MINUTES.toMillis(timePicker.getMinute()) +
                TimeUnit.SECONDS.toMillis(secondsPicker.getValue());
    }
}