package com.phonereplay.wallet_project.network.binance;

import com.phonereplay.wallet_project.PriceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BinanceApi {

  @GET("api/v3/ticker/price")
  Call<PriceResponse> getPrice(@Query("symbol") String symbol);
}
