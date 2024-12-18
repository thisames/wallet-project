package com.phonereplay.wallet_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.phonereplay.wallet_project.dialog.DialogCopySeedsFragment;
import com.phonereplay.wallet_project.dialog.DialogImportSeedsFragment;

import org.bitcoinj.wallet.Wallet;

import java.io.File;

public class WelcomeActivity extends AppCompatActivity {

  public static final String TAG = "BitcoinWallet";

  private static final String WALLET_FILE_NAME = "user_wallet";

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
            DialogImportSeedsFragment dialog = new DialogImportSeedsFragment(walletFile);
            dialog.show(getSupportFragmentManager(), "ImportSeedsDialog");
        }

        );

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
            WalletAbstraction walletAbstraction = new WalletAbstraction();
            Wallet wallet = walletAbstraction.createWallet(walletFile);

            showModal(wallet);
          } catch (Exception e) {
            Log.e(TAG, "Erro ao criar carteira: " + e.getMessage(), e);
          }
        });
  }

  private void showModal(Wallet wallet) {
    DialogCopySeedsFragment dialog =
        new DialogCopySeedsFragment(wallet.getKeyChainSeed().getMnemonicCode());
    dialog.show(getSupportFragmentManager(), "startGameDialog");
  }
}
