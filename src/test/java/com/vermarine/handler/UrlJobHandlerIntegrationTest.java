package com.vermarine.handler;

import com.vermarine.scheduler.UrlJob;
import com.vermarine.scheduler.UrlJobHandler;
import com.vermarine.scheduler.UrlJobResult;
import com.vermarine.scheduler.UrlJobTestImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UrlJobHandlerIntegrationTest {
  @Test
  public void testHandle() {
    UrlJob urlJob = UrlJobTestImpl.builder().build();
    UrlJobHandler urlJobHandler = new UrlJobHandler();

    UrlJobResult handle = urlJobHandler.handle(urlJob);

    assertEquals("simple-response", handle.getBody());
  }
}
