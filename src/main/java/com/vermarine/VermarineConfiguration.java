package com.vermarine;

import com.vermarine.configuration.EnvironmentBasedConfiguration;

import java.util.Map;

public class VermarineConfiguration extends EnvironmentBasedConfiguration {

  public VermarineConfiguration(Map<String, String> environment) {
    super(environment);
  }

  public int getPort() {
    return get("VERMARINE_PORT", 8080);
  }

}
