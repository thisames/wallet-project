package com.phonereplay.wallet_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DialogCopySeedsFragment extends DialogFragment {

    private static int delay = 30000;
    private final List<String> seedWords;
    private String secondText;

    public DialogCopySeedsFragment(Wallet wallet) {
        DeterministicSeed seed = wallet.getKeyChainSeed();
        seedWords = seed.getMnemonicCode();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_copy_seeds, null);

        TextView seedWordsText = dialogView.findViewById(R.id.seedWordsText);
        seedWordsText.setText(String.join(" ", seedWords));


        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok, (dialogInterface, id) -> {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                })
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setEnabled(false);

            final Timer time = new Timer();

            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (delay == 0) {
                        time.cancel();
                        requireActivity().runOnUiThread(() -> {
                            positiveButton.setEnabled(true);
                            positiveButton.setText(android.R.string.ok);
                            positiveButton.setOnClickListener(v -> {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            });
                        });
                    } else {
                        secondText = delay / 1000 + "s";
                        requireActivity().runOnUiThread(() -> positiveButton.setText(secondText));
                        delay -= 1000;
                    }
                }
            }, 0, 1000);
        });
        return dialog;
    }
}
