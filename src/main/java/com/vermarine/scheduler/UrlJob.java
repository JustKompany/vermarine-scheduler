package com.vermarine.scheduler;

import org.javalite.activejdbc.Model;

import java.sql.Timestamp;

public class UrlJob extends Model {
  public UrlJob() {}

  public UrlJob(String url) {
    setTimestamp("created_on", new Timestamp(System.currentTimeMillis()));
    setString("url", url);
  }

  public Timestamp getCreatedOn() {
    return getTimestamp("created_on");
  }

  public String getUrl() {
    return getString("url");
  }
}
