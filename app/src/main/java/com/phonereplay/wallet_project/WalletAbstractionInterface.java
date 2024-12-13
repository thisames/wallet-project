package com.phonereplay.wallet_project;

import androidx.annotation.NonNull;
import java.io.File;
import java.io.IOException;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

public interface WalletAbstractionInterface {

  Wallet createWallet(DeterministicSeed seed, File walletFile) throws IOException;

  @NonNull
  DeterministicSeed getDeterministicSeed();
}
