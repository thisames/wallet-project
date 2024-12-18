package com.phonereplay.wallet_project;

import androidx.annotation.NonNull;

import com.phonereplay.wallet_project.configuration.InitConfig;

import org.bitcoinj.params.AbstractBitcoinNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.KeyChainGroupStructure;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

public class WalletAbstraction implements WalletAbstractionInterface {

  public Wallet createWallet(File walletFile) throws IOException {
    InitConfig initConfig = new InitConfig();
    AbstractBitcoinNetParams bitCoinNetParams = initConfig.getNetParams();

    DeterministicSeed seed = getDeterministicSeed();

    Wallet wallet =
        Wallet.fromSeed(
            bitCoinNetParams, seed, Script.ScriptType.P2WPKH, KeyChainGroupStructure.DEFAULT);

    wallet.saveToFile(walletFile);

    return wallet;
  }

  public Wallet importWallet(String seeds, File walletFile)
          throws IOException, UnreadableWalletException {

    InitConfig initConfig = new InitConfig();
    AbstractBitcoinNetParams bitCoinNetParams = initConfig.getNetParams();

    long creationTime = System.currentTimeMillis() / 1000;

    DeterministicSeed seed = new DeterministicSeed(seeds, null, "", creationTime);

    Wallet wallet =
            Wallet.fromSeed(
                    bitCoinNetParams, seed, Script.ScriptType.P2WPKH, KeyChainGroupStructure.DEFAULT);

    wallet.saveToFile(walletFile);

    return wallet;
  }


  private @NonNull DeterministicSeed getDeterministicSeed() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] entropy = new byte[16];
    secureRandom.nextBytes(entropy);

    return new DeterministicSeed(entropy, "", System.currentTimeMillis() / 1000);
  }
}
