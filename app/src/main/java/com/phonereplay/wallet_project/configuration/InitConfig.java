package com.phonereplay.wallet_project.configuration;

import com.phonereplay.wallet_project.BuildConfig;
import com.phonereplay.wallet_project.NetParams;
import org.bitcoinj.params.AbstractBitcoinNetParams;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

public class InitConfig implements NetParams {

  @Override
  public AbstractBitcoinNetParams getNetParams() {

    String environment = BuildConfig.ENVIRONMENT;

    if ("prod".equals(environment)) {
      return MainNetParams.get();
    }
    return TestNet3Params.get();
  }
}
