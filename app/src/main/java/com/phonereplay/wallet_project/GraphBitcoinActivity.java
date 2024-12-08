package com.phonereplay.wallet_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraphBitcoinActivity extends AppCompatActivity {

  private final ArrayList<Entry> entries = new ArrayList<>();
  private final Handler handler = new Handler(Objects.requireNonNull(Looper.myLooper()));
  GraphBitcoinConfig config = GraphBitcoinConfig.getInstance();
  private LineChart lineChart;
  private LineData lineData;
  private LineDataSet lineDataSet;
  private BinanceApi binanceApi;
  private int xValue = 0;
  private TextView currentPriceText;
  private float previousPrice = -1;
  private boolean isInDollars = true;
  private String currentSymbol = "BTCUSDT";
  private String currencySymbol = "$";

  private int timeUpdateGraph;

  @Override
  protected void onResume() {
    setTimeUpdateGraph();
    super.onResume();
  }

  private void setTimeUpdateGraph() {
    this.timeUpdateGraph = convertToMilliseconds(config.getTimeUpdateGraph());
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_graph_bitcoin);
    MaterialButton button = findViewById(R.id.button_to_graph_config);
    setTimeUpdateGraph();

    currentPriceText = findViewById(R.id.currentPriceText);

    lineChart = findViewById(R.id.lineChart);
    lineDataSet = new LineDataSet(entries, "BTC Price");
    lineDataSet.setColor(Color.BLUE);
    lineDataSet.setCircleColor(Color.BLUE);
    lineDataSet.setLineWidth(2f);
    lineDataSet.setCircleRadius(3f);
    lineDataSet.setDrawCircleHole(false);
    lineDataSet.setValueTextColor(Color.BLACK);
    lineData = new LineData(lineDataSet);
    lineChart.setData(lineData);

    lineChart.getDescription().setEnabled(false);
    lineChart.animateX(1000);
    lineChart.getXAxis().setGranularity(1f);
    lineChart.getAxisLeft().setGranularity(0.5f);
    lineChart.getAxisRight().setEnabled(false);

    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.binance.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    binanceApi = retrofit.create(BinanceApi.class);

    if (previousPrice == -1) {
      fetchPrice();
    }

    currentPriceText.setOnClickListener(v -> toggleCurrency());

    button.setOnClickListener(
        v -> {
          Intent intent = new Intent(GraphBitcoinActivity.this, GraphConfig.class);
          startActivity(intent);
        });

    handler.postDelayed(
        new Runnable() {

          @Override
          public void run() {
            fetchPrice();
            handler.postDelayed(this, timeUpdateGraph);
          }
        },
        timeUpdateGraph);
  }

  private void toggleCurrency() {
    isInDollars = !isInDollars;
    if (isInDollars) {
      currentSymbol = "BTCUSDT";
      currencySymbol = "$";
    } else {
      currentSymbol = "BTCBRL";
      currencySymbol = "R$";
    }

    entries.clear();
    xValue = 0;
    previousPrice = -1;

    fetchPrice();
  }

  private int convertToMilliseconds(int time) {
    return time * 1000;
  }

  private void fetchPrice() {
    Call<PriceResponse> call = binanceApi.getPrice(currentSymbol);
    call.enqueue(
        new Callback<PriceResponse>() {

          @Override
          public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
            if (response.isSuccessful()) {
              PriceResponse priceResponse = response.body();
              if (priceResponse != null) {
                float price = Float.parseFloat(priceResponse.getPrice());
                updateChart(price);
                updatePriceText(price);
              }
            }
          }

          @Override
          public void onFailure(Call<PriceResponse> call, Throwable t) {}
        });
  }

  private void updateChart(float price) {
    entries.add(new Entry(xValue++, price));
    lineDataSet.notifyDataSetChanged();
    lineData.notifyDataChanged();
    lineChart.notifyDataSetChanged();
    lineChart.invalidate();
  }

  private void updatePriceText(float price) {
    if (previousPrice != -1) {
      if (price > previousPrice) {
        currentPriceText.setTextColor(Color.parseColor("#428200"));
      } else if (price < previousPrice) {
        currentPriceText.setTextColor(Color.parseColor("#F63756"));
      } else {
        currentPriceText.setTextColor(Color.BLACK);
      }
    }

    currentPriceText.setText(String.format("%s%.2f", currencySymbol, price));

    previousPrice = price;
  }
}
