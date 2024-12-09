package com.phonereplay.wallet_project;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.phonereplay.wallet_project.components.ButtonStopwatch;
import java.util.List;

public class DialogCopySeedsFragment extends DialogFragment {

  private final List<String> seedWords;

  public DialogCopySeedsFragment(List<String> seedWords) {
    this.seedWords = seedWords;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    Dialog dialog = super.onCreateDialog(savedInstanceState);

    dialog.setContentView(R.layout.dialog_copy_seeds);

    TextView seedWordsText = dialog.findViewById(R.id.seedWordsText);
    seedWordsText.setText(String.join(" ", seedWords));

    dialog.setOnShowListener(
        dialogInterface -> {
          ButtonStopwatch positiveButton = dialog.findViewById(R.id.positive_button);
          positiveButton.setTimeStamp(30);
          positiveButton.initialize(requireActivity());
        });
    return dialog;
  }
}
