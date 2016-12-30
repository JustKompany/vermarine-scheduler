package com.vermarine.scheduler;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;

public class UrlJobHandler {
  public UrlJobResult handle(UrlJob urlJob) {
    String url = urlJob.getUrl();

    HttpClient httpClient = HttpClientBuilder.create()
      .build();

    HttpGet httpGet = new HttpGet(url);

    final HttpResponse httpResponse;
    try {
      httpResponse = httpClient.execute(httpGet);
    } catch (IOException e) {
      throw new RuntimeException("Unable to execute", e);
    }

    final InputStream content;
    try {
      content = httpResponse.getEntity().getContent();
    } catch (IOException e) {
      throw new RuntimeException("Unable to get content", e);
    }

    final String body;
    try {
      body = IOUtils.toString(content);
    } catch (IOException e) {
      throw new RuntimeException("Unable to get body as String", e);
    }

    return new UrlJobResult(body);
  }
}
