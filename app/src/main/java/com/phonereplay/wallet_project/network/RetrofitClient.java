package com.phonereplay.wallet_project.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

  private static Retrofit retrofit;

  private String BASE_URL;

  public RetrofitClient(String BASE_URL) {
    this.BASE_URL = BASE_URL;
  }

  public Retrofit getInstance() {
    if (retrofit == null) {
      retrofit =
          new Retrofit.Builder()
              .baseUrl(this.BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
    }
    return retrofit;
  }
}
