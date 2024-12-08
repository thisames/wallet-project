package com.phonereplay.wallet_project;

public class GraphBitcoinConfig {

  private static GraphBitcoinConfig instance;
  private int timeUpdateGraph = 5;

  private GraphBitcoinConfig() {}

  public static synchronized GraphBitcoinConfig getInstance() {
    if (instance == null) {
      instance = new GraphBitcoinConfig();
    }
    return instance;
  }

  public int getTimeUpdateGraph() {
    return timeUpdateGraph;
  }

  public void setTimeUpdateGraph(int timeUpdateGraph) {
    this.timeUpdateGraph = timeUpdateGraph;
  }
}
