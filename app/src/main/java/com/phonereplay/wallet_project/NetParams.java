package com.phonereplay.wallet_project;

import org.bitcoinj.params.AbstractBitcoinNetParams;

public interface NetParams {

  AbstractBitcoinNetParams getNetParams();
}
