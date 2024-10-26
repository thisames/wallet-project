package com.phonereplay.wallet_project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando as views
        addressText = findViewById(R.id.addressText);
        privateKeyText = findViewById(R.id.privateKeyText);
        Button createWalletButton = findViewById(R.id.createWalletButton);

        // Configurar ação do botão para criar ou carregar carteira
        createWalletButton.setOnClickListener(v -> {
            if (walletAppKit == null || !walletAppKit.isRunning()) {
                new WalletInitTask().execute();
            } else {
                Log.d(TAG, "Carteira já está carregada e rodando.");
            }
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

    private class WalletInitTask extends AsyncTask<Void, Void, Wallet> {

        @Override
        protected Wallet doInBackground(Void... voids) {
            Log.d(TAG, "Iniciando configuração da carteira...");
            NetworkParameters params = TestNet3Params.get();
            File walletDir = new File(getFilesDir(), "test_wallet");

            // Verificar se o WalletAppKit já foi inicializado
            if (walletAppKit != null && walletAppKit.isRunning()) {
                Log.d(TAG, "Carteira já existe. Carregando carteira existente.");
                return walletAppKit.wallet();
            }

            // Excluir carteira anterior, caso exista
            if (walletDir.exists()) {
                for (File file : walletDir.listFiles()) {
                    file.delete();
                }
                Log.d(TAG, "Carteira anterior excluída.");
            }

            walletAppKit = new WalletAppKit(params, walletDir, "wallet");

            // Permitir que o WalletAppKit inicie sem bloquear a interface
            walletAppKit.setBlockingStartup(false);

            // Adicionar listener para monitorar o estado do WalletAppKit
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

            // Inicializar e sincronizar a carteira
            walletAppKit.startAsync();
            Log.d(TAG, "WalletAppKit startAsync() chamado.");

            try {
                walletAppKit.awaitRunning();
                Log.d(TAG, "WalletAppKit está rodando.");
            } catch (Exception e) {
                Log.e(TAG, "Erro ao iniciar WalletAppKit", e);
                return null;
            }

            Log.d(TAG, "Configuração da carteira concluída.");
            return walletAppKit.wallet();
        }

        @Override
        protected void onPostExecute(Wallet wallet) {
            if (wallet != null) {
                // Atualizar a interface com o endereço e chave privada
                String address = wallet.currentReceiveAddress().toString();
                String privateKey = wallet.currentReceiveKey().getPrivateKeyEncoded(wallet.getParams()).toString();
                addressText.setText(address);
                privateKeyText.setText(privateKey);

                Log.d(TAG, "Bitcoin Address: " + address);
                Log.d(TAG, "Private Key (WIF): " + privateKey);
            } else {
                Log.e(TAG, "Falha ao inicializar a carteira. Verifique os logs para mais informações.");
            }
        }
    }
}
