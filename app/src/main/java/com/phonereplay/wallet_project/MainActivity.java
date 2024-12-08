package com.phonereplay.wallet_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.bitcoinj.core.Coin;
import org.bitcoinj.wallet.Wallet;

import java.io.File;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = "BitcoinWallet";

  private static final String WALLET_FILE_NAME = "user_wallet";

  private TextView addressText;
  private TextView balanceText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    addressText = findViewById(R.id.addressText);
    balanceText = findViewById(R.id.balanceText);
    Button openGraphButton = findViewById(R.id.openGraphButton);

    openGraphButton.setOnClickListener(
        v -> {
          Intent intent = new Intent(MainActivity.this, GraphBitcoinActivity.class);
          startActivity(intent);
        });

    File walletFile = new File(getFilesDir(), WALLET_FILE_NAME);
    if (walletFile.exists()) {
      loadWallet(walletFile);
    } else {
      Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
      startActivity(intent);
      Log.d(TAG, "Nenhuma carteira existente encontrada. Crie uma nova.");
    }
  }

  private void loadWallet(File walletFile) {
    try {
      Wallet wallet = Wallet.loadFromFile(walletFile);

      updateUIWithWalletData(wallet);
      Log.d(TAG, "Wallet carregada com sucesso.");
    } catch (Exception e) {
      Log.e(TAG, "Erro ao carregar carteira: " + e.getMessage(), e);
    }
  }

  private void updateUIWithWalletData(Wallet wallet) {
    String address = wallet.currentReceiveAddress().toString();
    Coin balance = wallet.getBalance();

    addressText.setText(address);
    balanceText.setText(balance.toFriendlyString());

    Log.d(TAG, "Endereço Bitcoin: " + address);
    Log.d(TAG, "Saldo: " + balance.toFriendlyString());
  }
}
