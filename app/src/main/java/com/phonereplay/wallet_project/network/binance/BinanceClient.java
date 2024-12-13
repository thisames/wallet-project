package com.phonereplay.wallet_project.network.binance;

import com.phonereplay.wallet_project.PriceResponse;
import com.phonereplay.wallet_project.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

public class BinanceClient {

  private static final String BASE_URL = "https://api.binance.com/";

  private String currentSymbol = "BTCUSDT";

  RetrofitClient retrofitClient;

  private float price;

  public BinanceClient() {
    this.retrofitClient = new RetrofitClient(BASE_URL);
  }

  public BinanceApi getBinanceApi() {
    return retrofitClient.getInstance().create(BinanceApi.class);
  }

  public Float fetchPriceSync() {
    Call<PriceResponse> call = getBinanceApi().getPrice(currentSymbol);

    new Thread(
            () -> {
              try {
                Response<PriceResponse> response = call.execute();

                if (response.isSuccessful()) {
                  PriceResponse priceResponse = response.body();
                  if (priceResponse != null) {
                    this.price = Float.parseFloat(priceResponse.getPrice());
                  }
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
            })
        .start();

    return this.price;
  }

  public void setCurrentSymbol(String currentSymbol) {
    this.currentSymbol = currentSymbol;
  }
}
