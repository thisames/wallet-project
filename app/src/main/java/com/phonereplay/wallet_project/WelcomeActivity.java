package com.phonereplay.wallet_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.KeyChainGroupStructure;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.security.SecureRandom;

public class WelcomeActivity extends AppCompatActivity {

  public static final String TAG = "BitcoinWallet";

  private static final String WALLET_FILE_NAME = "user_wallet";
  private Wallet wallet;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_welcome);
    ViewCompat.setOnApplyWindowInsetsListener(
        findViewById(R.id.main),
        (v, insets) -> {
          Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
          return insets;
        });

    File walletFile = new File(getFilesDir(), WALLET_FILE_NAME);

    Button createWalletButton = findViewById(R.id.createWalletButton);
    Button importWalletButton = findViewById(R.id.importWalletButton);

    importWalletButton.setOnClickListener(
        v -> {
          // TODO implementar importação de carteira
        });

    /**
     * Listener para o botão "createWalletButton". Este método é chamado quando o botão de criação
     * de carteira é pressionado. Ele realiza os seguintes passos: 1. Gera entropia aleatória para
     * criar uma seed determinística. 2. Utiliza a seed para criar uma carteira determinística
     * compatível com BIP-32. 3. Salva a carteira gerada em um arquivo local. 4. Exibe um modal ao
     * concluir o processo com sucesso.
     *
     * <p>Requisitos: - Biblioteca `bitcoinj` para manipulação de carteiras e chaves privadas. -
     * Permissão para salvar arquivos localmente.
     *
     * @throws Exception Caso ocorra qualquer erro no processo de criação ou salvamento da carteira.
     */
    createWalletButton.setOnClickListener(
        v -> {
          try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] entropy = new byte[16];
            secureRandom.nextBytes(entropy);

            DeterministicSeed seed =
                new DeterministicSeed(entropy, "", System.currentTimeMillis() / 1000);

            wallet =
                Wallet.fromSeed(
                    TestNet3Params.get(),
                    seed,
                    Script.ScriptType.P2WPKH,
                    KeyChainGroupStructure.DEFAULT);

            wallet.saveToFile(walletFile);

            showModal();
          } catch (Exception e) {
            Log.e(TAG, "Erro ao criar carteira: " + e.getMessage(), e);
          }
        });
  }

  private void showModal() {
    DialogCopySeedsFragment dialog =
        new DialogCopySeedsFragment(wallet.getKeyChainSeed().getMnemonicCode());
    dialog.show(getSupportFragmentManager(), "startGameDialog");
  }
}
