package com.phonereplay.wallet_project.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.phonereplay.wallet_project.MainActivity;
import com.phonereplay.wallet_project.R;
import com.phonereplay.wallet_project.WalletAbstraction;

import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.io.IOException;

public class DialogImportSeedsFragment extends DialogFragment {

  private EditText editText;

  private final File walletFile;

  public DialogImportSeedsFragment(File walletFile) {
    this.walletFile = walletFile;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    Dialog dialog = super.onCreateDialog(savedInstanceState);

    dialog.setContentView(R.layout.dialog_import_seeds);

    editText = dialog.findViewById(R.id.editText);

    Button button = dialog.findViewById(R.id.confirm_import_button);
      button.setOnClickListener(v -> {
          //String enteredSeeds = "film sketch soda talent decline village beef anxiety edit climb garbage easy";
          //String enteredSeeds = "jump habit segment capable almost dash theory caught glove put chaos onion";
          String enteredSeeds = "property shoot tonight pole razor fallow impact heavy lamp light movie tortoise";

          WalletAbstraction walletAbstraction = new WalletAbstraction();

          // Input validation
          if (isValidSeedPhrase(enteredSeeds)) {
              try {
                  Wallet wallet = walletAbstraction.importWallet(enteredSeeds, walletFile);
                  Intent intent = new Intent(requireContext(), MainActivity.class);
                  startActivity(intent);
                  dismiss(); // Dismiss the dialog
              } catch (IOException | UnreadableWalletException e) {
                  // Handle exceptions and display error messages
                  showErrorDialog(e.getMessage());
              }
          } else {
              // Display error message for invalid input
              editText.setError("Invalid seed phrase");
          }
      });

    return dialog;
  }

    private boolean isValidSeedPhrase(String seedPhrase) {
        // Add your validation logic here
        return seedPhrase.matches("[a-zA-Z0-9 ]+"); // Simple example
    }

    // Helper method to display error dialog
    private void showErrorDialog(String message) {
        // ... implementation for showing an error dialog
    }
}
