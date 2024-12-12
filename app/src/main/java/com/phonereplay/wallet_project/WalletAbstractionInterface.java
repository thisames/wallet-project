package com.phonereplay.wallet_project;

import androidx.annotation.NonNull;

import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.io.IOException;

public interface WalletAbstractionInterface {

  Wallet createWallet(DeterministicSeed seed, File walletFile) throws IOException;

  @NonNull
  DeterministicSeed getDeterministicSeed();
}
