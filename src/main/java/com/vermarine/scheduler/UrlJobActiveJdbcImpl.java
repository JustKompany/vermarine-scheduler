package com.vermarine.scheduler;

import org.javalite.activejdbc.Model;

import java.sql.Timestamp;

public class UrlJobActiveJdbcImpl extends Model implements UrlJob {
  public UrlJobActiveJdbcImpl() {}

  public UrlJobActiveJdbcImpl(String url, String method) {
    setTimestamp("created_on", new Timestamp(System.currentTimeMillis()));
    setString("url", url);
    setString("method", method);
  }

  @Override
  public Timestamp getCreatedOn() {
    return getTimestamp("created_on");
  }

  @Override
  public String getUrl() {
    return getString("url");
  }

  @Override
  public String getMethod() {
    return getString("method");
  }

}
