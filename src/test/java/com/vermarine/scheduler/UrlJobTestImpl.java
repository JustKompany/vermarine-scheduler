package com.vermarine.scheduler;

import java.sql.Timestamp;

public class UrlJobTestImpl implements UrlJob {
  private Timestamp createdOn;
  private String url;
  private String method;

  public UrlJobTestImpl(Timestamp createdOn, String url, String method) {
    this.createdOn = createdOn;
    this.url = url;
    this.method = method;
  }

  @Override
  public Timestamp getCreatedOn() {
    return createdOn;
  }

  @Override
  public String getUrl() {
    return url;
  }

  @Override
  public String getMethod() {
    return method;
  }

  public static UrlJobTestBuilder builder() {
    return new UrlJobTestBuilder();
  }

  public static class UrlJobTestBuilder {
    private Timestamp createdOn = new Timestamp(System.currentTimeMillis());
    private String url = "http://localhost:8081/simple-response";
    private String method = "GET";

    public UrlJobTestBuilder setCreatedOn(Timestamp createdOn) {
      this.createdOn = createdOn;
      return this;
    }

    public UrlJobTestBuilder setUrl(String url) {
      this.url = url;
      return this;
    }

    public UrlJobTestBuilder setMethod(String method) {
      this.method = method;
      return this;
    }

    public UrlJobTestImpl build() {
      return new UrlJobTestImpl(createdOn, url, method);
    }
  }
}
