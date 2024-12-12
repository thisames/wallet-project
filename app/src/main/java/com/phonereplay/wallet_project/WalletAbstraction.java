package com.phonereplay.wallet_project;

import androidx.annotation.NonNull;

import com.phonereplay.wallet_project.configuration.InitConfig;

import org.bitcoinj.params.AbstractBitcoinNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.KeyChainGroupStructure;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

public class WalletAbstraction implements WalletAbstractionInterface {

  public Wallet createWallet(DeterministicSeed seed, File walletFile) throws IOException {
    InitConfig initConfig = new InitConfig();
    AbstractBitcoinNetParams bitCoinNetParams = initConfig.getNetParams();

    Wallet wallet =
        Wallet.fromSeed(
            bitCoinNetParams, seed, Script.ScriptType.P2WPKH, KeyChainGroupStructure.DEFAULT);

    wallet.saveToFile(walletFile);

    return wallet;
  }

  public @NonNull DeterministicSeed getDeterministicSeed() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] entropy = new byte[16];
    secureRandom.nextBytes(entropy);

    return new DeterministicSeed(entropy, "", System.currentTimeMillis() / 1000);
  }
}
