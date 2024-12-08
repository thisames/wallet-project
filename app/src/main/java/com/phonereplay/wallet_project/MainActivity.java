package com.phonereplay.wallet_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import org.bitcoinj.core.Coin;
import org.bitcoinj.wallet.Wallet;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = "BitcoinWallet";

  private static final String WALLET_FILE_NAME = "user_wallet";

  private TextView addressText;
  private TextView privateKeyText;
  private TextView balanceText;

  private Wallet wallet;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    addressText = findViewById(R.id.addressText);
    privateKeyText = findViewById(R.id.privateKeyText);
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
      wallet = Wallet.loadFromFile(walletFile);

      updateUIWithWalletData(wallet);
      Log.d(TAG, "Wallet carregada com sucesso.");
    } catch (Exception e) {
      Log.e(TAG, "Erro ao carregar carteira: " + e.getMessage(), e);
    }
  }

  private void updateUIWithWalletData(Wallet wallet) {
    String address = wallet.currentReceiveAddress().toString();
    String privateKey =
        wallet.currentReceiveKey().getPrivateKeyEncoded(wallet.getParams()).toString();
    Coin balance = wallet.getBalance();

    addressText.setText(address);
    privateKeyText.setText(privateKey);
    balanceText.setText(balance.toFriendlyString());

    Log.d(TAG, "Endereço Bitcoin: " + address);
    Log.d(TAG, "Chave Privada (WIF): " + privateKey);
    Log.d(TAG, "Saldo: " + balance.toFriendlyString());
  }
}
