package com.phonereplay.wallet_project;

import static com.phonereplay.wallet_project.MainActivity.TAG;

import android.os.AsyncTask;
import android.util.Log;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.Wallet;

import java.io.File;

public class WalletLoadTask extends AsyncTask<Void, Void, Wallet> {
    private final MainActivity mainActivity;

    public WalletLoadTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Wallet doInBackground(Void... voids) {
        Log.d(TAG, "Carregando carteira existente...");
        NetworkParameters params = TestNet3Params.get();
        File walletDir = new File(mainActivity.getFilesDir(), "test_wallet");

        WalletAppKit walletAppKit = new WalletAppKit(params, walletDir, "wallet");
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
            mainActivity.updateUIWithWalletData(wallet);
        } else {
            Log.e(TAG, "Falha ao carregar a carteira.");
        }
    }
}
