package com.vermarine.configuration;

public class RequiredConfigurationMissingException extends Exception {
  private final String configurationName;

  public RequiredConfigurationMissingException(String configurationName) {
    this.configurationName = configurationName;
  }

  @Override
  public String getMessage() {
    return "Missing configuration [" + configurationName + "]";
  }

  public String getConfigurationName() {
    return configurationName;
  }
}
