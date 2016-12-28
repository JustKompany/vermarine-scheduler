package com.vermarine;

import java.util.Map;

public class Configuration {
  private final Map<String, String> environment;

  public Configuration() {
    environment = System.getenv();
  }

  public int getPort() {
    return get("VERMARINE_PORT", 8080);
  }

  private int get(String key, int defaultValue) {
    String stringValue = environment.get("key");
    if(stringValue == null) {
      return defaultValue;
    }
    return Integer.parseInt(stringValue);
  }
}
