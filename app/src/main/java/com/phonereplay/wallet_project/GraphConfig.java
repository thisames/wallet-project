package com.phonereplay.wallet_project;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.phonereplay.wallet_project.dialog.DialogSelectUpdateTimeGraph;

public class GraphConfig extends AppCompatActivity {

  GraphBitcoinConfig config = GraphBitcoinConfig.getInstance();

  Button openUpdateTimeGraphDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_graph_config);
    ViewCompat.setOnApplyWindowInsetsListener(
        findViewById(R.id.main),
        (v, insets) -> {
          Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
          return insets;
        });

    openUpdateTimeGraphDialog = findViewById(R.id.openUpdateTimeGraphDialog);

    updateButtonText();
    paintTimeUpdateGraphTextButton();

    openUpdateTimeGraphDialog.setOnClickListener(
        v -> {
          DialogSelectUpdateTimeGraph dialog = new DialogSelectUpdateTimeGraph();
          dialog.show(getSupportFragmentManager(), "UpdateTimeGraphDialog");
        });
  }

  public void updateButtonText() {
    openUpdateTimeGraphDialog.setText(
        "Tempo de atualização de gráfico " + config.getTimeUpdateGraph());
  }

  public void paintTimeUpdateGraphTextButton() {
    CharSequence text = openUpdateTimeGraphDialog.getText();

    for (int i = 0; i < text.length(); i++) {
      if (Character.isDigit(text.charAt(i))) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(
            new ForegroundColorSpan(Color.parseColor("#717175")),
            i,
            i + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        openUpdateTimeGraphDialog.setText(spannableString);
      }
    }
  }
}
