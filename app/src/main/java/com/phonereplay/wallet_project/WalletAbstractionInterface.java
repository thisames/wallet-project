package com.phonereplay.wallet_project;

import java.io.File;
import java.io.IOException;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

public interface WalletAbstractionInterface {

  Wallet createWallet(File walletFile) throws IOException;

  Wallet importWallet(String seeds, File walletFile) throws IOException, UnreadableWalletException;
}
