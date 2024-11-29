package com.phonereplay.wallet_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.bitcoinj.core.Coin;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.KeyChainGroupStructure;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.security.SecureRandom;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "BitcoinWallet";

    private static final String WALLET_FILE_NAME = "user_wallet";

    private TextView addressText;
    private TextView privateKeyText;
    private TextView balanceText;
    private TextView seedPhraseText;

    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressText = findViewById(R.id.addressText);
        privateKeyText = findViewById(R.id.privateKeyText);
        balanceText = findViewById(R.id.balanceText);
        seedPhraseText = findViewById(R.id.seedPhraseText);
        Button createWalletButton = findViewById(R.id.createWalletButton);

        // Tentar carregar a carteira salva ao iniciar
        File walletFile = new File(getFilesDir(), WALLET_FILE_NAME);
        if (walletFile.exists()) {
            loadWallet(walletFile);
        } else {
            Log.d(TAG, "Nenhuma carteira existente encontrada. Crie uma nova.");
        }

        //os comentarios sao importantes manter nesse codigo.
        createWalletButton.setOnClickListener(v -> {
            try {
                // Gerar entropia aleatória
                SecureRandom secureRandom = new SecureRandom();
                byte[] entropy = new byte[16]; // 16 bytes = 128 bits de entropia
                secureRandom.nextBytes(entropy);

                // Criar a seed determinística
                DeterministicSeed seed = new DeterministicSeed(entropy, "", System.currentTimeMillis() / 1000);

                // Criar a carteira determinística a partir da seed
                wallet = Wallet.fromSeed(
                        TestNet3Params.get(),
                        seed,
                        Script.ScriptType.P2WPKH, // SegWit nativo (Bech32)
                        KeyChainGroupStructure.DEFAULT // Usar estrutura BIP-32
                );

                // Salvar a carteira em um arquivo
                wallet.saveToFile(walletFile);

                // Atualizar a interface com os dados da nova carteira
                updateUIWithWalletData(wallet);

                // Exibir a seed phrase na interface
                List<String> seedWords = seed.getMnemonicCode();
                seedPhraseText.setText("Seed Phrase: " + String.join(" ", seedWords));
                Log.d(TAG, "Seed Phrase: " + String.join(" ", seedWords));
            } catch (Exception e) {
                Log.e(TAG, "Erro ao criar carteira: " + e.getMessage(), e);
            }
        });
    }

    private void loadWallet(File walletFile) {
        try {
            // Carregar a carteira existente do arquivo
            wallet = Wallet.loadFromFile(walletFile);

            // Atualizar a interface com os dados da carteira carregada
            updateUIWithWalletData(wallet);

            // Exibir a seed phrase na interface
            DeterministicSeed seed = wallet.getKeyChainSeed();
            List<String> seedWords = seed.getMnemonicCode();
            seedPhraseText.setText("Seed Phrase: " + String.join(" ", seedWords));
            Log.d(TAG, "Wallet carregada com sucesso.");
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar carteira: " + e.getMessage(), e);
        }
    }

    void updateUIWithWalletData(Wallet wallet) {
        String address = wallet.currentReceiveAddress().toString();
        String privateKey = wallet.currentReceiveKey().getPrivateKeyEncoded(wallet.getParams()).toString();
        Coin balance = wallet.getBalance();

        addressText.setText(address);
        privateKeyText.setText(privateKey);
        balanceText.setText(balance.toFriendlyString());

        Log.d(TAG, "Endereço Bitcoin: " + address);
        Log.d(TAG, "Chave Privada (WIF): " + privateKey);
        Log.d(TAG, "Saldo: " + balance.toFriendlyString());
    }
}
