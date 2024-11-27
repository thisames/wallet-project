package com.phonereplay.wallet_project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class GraphConfig extends AppCompatActivity {

    GraphBitcoinConfig config = GraphBitcoinConfig.getInstance();

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

        TextInputEditText timePicker = findViewById(R.id.timeInput);

        timePicker.setText(String.valueOf(config.getTimeUpdateGraph()));

        timePicker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    try {
                        int time = Integer.parseInt(s.toString());
                        // TODO - necessario implementar uma funcao de conversao do time digitado para o tempo em milisegundos.
                        //https://github.com/thisames/wallet-project/issues/24
                        config.setTimeUpdateGraph(time);
                    } catch (NumberFormatException e) {
                        timePicker.setError("Invalid input! Please enter a valid number.");
                    }
                }
            }
        });
    }
}