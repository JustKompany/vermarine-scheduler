package com.vermarine.database;

import com.vermarine.configuration.EnvironmentBasedConfiguration;
import com.vermarine.configuration.RequiredConfigurationMissingException;
import org.javalite.activejdbc.Base;

import java.util.Map;
import java.util.concurrent.Callable;

public class DatabaseFacade extends EnvironmentBasedConfiguration {
  private final String driver;
  private final String url;
  private final String username;
  private final String password;

  public DatabaseFacade(Map<String, String> environment) throws RequiredConfigurationMissingException {
    super(environment);

    driver = getRequired("VERAMINE_DATABASE_DRIVER");
    url = getRequired("VERAMINE_DATABASE_URL");
    username = getRequired("VERAMINE_DATABASE_USERNAME");
    password = getRequired("VERAMINE_DATABASE_PASSWORD");
  }

  public <T> T execute(Callable<T> callable) throws Exception {
    open();
    try {
      return callable.call();
    } finally {
      close();
    }
  }

  public void open() {
    Base.open(driver, url, username, password);
  }

  public void close() {
    Base.close();
  }
}
