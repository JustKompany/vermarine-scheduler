package com.vermarine.configuration;

import java.util.Map;

public class EnvironmentBasedConfiguration {
  private final Map<String, String> environment;

  public EnvironmentBasedConfiguration(Map<String, String> environment) {
    this.environment = environment;
  }

  protected int get(String key, int defaultValue) throws NumberFormatException {
    String stringValue = environment.get(key);
    if(stringValue == null) {
      return defaultValue;
    }
    return Integer.parseInt(stringValue);
  }

  protected String get(String key, String defaultValue) {
    String stringValue = environment.get(key);
    return stringValue == null ? defaultValue : stringValue;
  }

  protected String getRequired(String key) throws RequiredConfigurationMissingException {
    String stringValue = environment.get(key);
    if(stringValue == null) {
      throw new RequiredConfigurationMissingException(key);
    }
    return stringValue;
  }
}
