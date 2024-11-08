package com.phonereplay.wallet_project;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraphBitcoin extends AppCompatActivity {

    private final ArrayList<Entry> entries = new ArrayList<>();
    private final Handler handler = new Handler();
    private LineChart lineChart;
    private LineData lineData;
    private LineDataSet lineDataSet;
    private BinanceApi binanceApi;
    private int xValue = 0;
    private TextView currentPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_bitcoin);

        lineChart = findViewById(R.id.lineChart);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(false);

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.binance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        binanceApi = retrofit.create(BinanceApi.class);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchPrice();
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }

    private void fetchPrice() {
        Call<PriceResponse> call = binanceApi.getPrice("BTCUSDT");
        call.enqueue(new Callback<PriceResponse>() {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                if (response.isSuccessful()) {
                    PriceResponse priceResponse = response.body();
                    if (priceResponse != null) {
                        float price = Float.parseFloat(priceResponse.getPrice());
                        updateChart(price);
                        updatePriceText(price); // Atualiza o TextView com o preço atual
                    }
                }
            }

            @Override
            public void onFailure(Call<PriceResponse> call, Throwable t) {
                // Lidar com erros de rede
            }
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
        currentPriceText.setText(String.format("$%.2f", price));
    }
}
