package com.phonereplay.wallet_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.bitcoinj.core.Coin;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.Wallet;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "BitcoinWallet";
    private WalletAppKit walletAppKit;

    private TextView addressText;
    private TextView privateKeyText;
    private TextView balanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressText = findViewById(R.id.addressText);
        privateKeyText = findViewById(R.id.privateKeyText);
        balanceText = findViewById(R.id.balanceText);
        Button createWalletButton = findViewById(R.id.createWalletButton);
        Button openGraphButton = findViewById(R.id.openGraphButton);

        // Verifica se a carteira existe e carrega os dados
        if (doesWalletExist()) {
            new WalletLoadTask(this).execute();
        } else {
            Log.d(TAG, "Nenhuma carteira encontrada. Use o botão para criar uma nova carteira.");
        }

        createWalletButton.setOnClickListener(v -> {
            if (walletAppKit == null || !walletAppKit.isRunning()) {
                new WalletCreateTask(this).execute();
            } else {
                Log.d(TAG, "Carteira já está carregada e rodando.");
            }
        });

        openGraphButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GraphBitcoin.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (walletAppKit != null) {
            walletAppKit.stopAsync();
            walletAppKit.awaitTerminated();
        }
    }

    private boolean doesWalletExist() {
        File walletDir = new File(getFilesDir(), "test_wallet");
        return walletDir.exists() && walletDir.isDirectory();
    }

    public void updateUIWithWalletData(Wallet wallet) {
        String address = wallet.currentReceiveAddress().toString();
        String privateKey = wallet.currentReceiveKey().getPrivateKeyEncoded(wallet.getParams()).toString();
        Coin balance = wallet.getBalance();

        addressText.setText(address);
        privateKeyText.setText(privateKey);
        balanceText.setText(balance.toFriendlyString());

        Log.d(TAG, "Bitcoin Address: " + address);
        Log.d(TAG, "Private Key (WIF): " + privateKey);
        Log.d(TAG, "Saldo da Carteira: " + balance.toFriendlyString());
    }

}
