package com.phonereplay.wallet_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.Wallet;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BitcoinWallet";
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
            new WalletLoadTask().execute();
        } else {
            Log.d(TAG, "Nenhuma carteira encontrada. Use o botão para criar uma nova carteira.");
        }

        createWalletButton.setOnClickListener(v -> {
            if (walletAppKit == null || !walletAppKit.isRunning()) {
                new WalletCreateTask().execute();
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

    private void updateUIWithWalletData(Wallet wallet) {
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

    private class WalletLoadTask extends AsyncTask<Void, Void, Wallet> {

        @Override
        protected Wallet doInBackground(Void... voids) {
            Log.d(TAG, "Carregando carteira existente...");
            NetworkParameters params = TestNet3Params.get();
            File walletDir = new File(getFilesDir(), "test_wallet");

            walletAppKit = new WalletAppKit(params, walletDir, "wallet");
            walletAppKit.setBlockingStartup(false);

            walletAppKit.addListener(new Service.Listener() {
                @Override
                public void running() {
                    Log.d(TAG, "WalletAppKit está rodando.");
                }

                @Override
                public void failed(Service.State from, Throwable failure) {
                    Log.e(TAG, "WalletAppKit falhou: " + failure.getMessage(), failure);
                }
            }, MoreExecutors.directExecutor());

            walletAppKit.startAsync();

            try {
                walletAppKit.awaitRunning();
                Log.d(TAG, "WalletAppKit carregado com sucesso.");
            } catch (Exception e) {
                Log.e(TAG, "Erro ao carregar WalletAppKit", e);
                return null;
            }

            return walletAppKit.wallet();
        }

        @Override
        protected void onPostExecute(Wallet wallet) {
            if (wallet != null) {
                updateUIWithWalletData(wallet);
            } else {
                Log.e(TAG, "Falha ao carregar a carteira.");
            }
        }
    }

    private class WalletCreateTask extends AsyncTask<Void, Void, Wallet> {

        @Override
        protected Wallet doInBackground(Void... voids) {
            Log.d(TAG, "Criando nova carteira...");
            NetworkParameters params = TestNet3Params.get();
            File walletDir = new File(getFilesDir(), "test_wallet");

            walletAppKit = new WalletAppKit(params, walletDir, "wallet");
            walletAppKit.setBlockingStartup(false);

            walletAppKit.addListener(new Service.Listener() {
                @Override
                public void running() {
                    Log.d(TAG, "WalletAppKit está rodando.");
                }

                @Override
                public void failed(Service.State from, Throwable failure) {
                    Log.e(TAG, "WalletAppKit falhou: " + failure.getMessage(), failure);
                }
            }, MoreExecutors.directExecutor());

            walletAppKit.startAsync();

            try {
                walletAppKit.awaitRunning();
                Log.d(TAG, "WalletAppKit carregado com sucesso.");
            } catch (Exception e) {
                Log.e(TAG, "Erro ao criar WalletAppKit", e);
                return null;
            }

            return walletAppKit.wallet();
        }

        @Override
        protected void onPostExecute(Wallet wallet) {
            if (wallet != null) {
                updateUIWithWalletData(wallet);
            } else {
                Log.e(TAG, "Falha ao criar a carteira.");
            }
        }
    }
}
