package com.vermarine.scheduler;

import java.sql.Timestamp;

public interface UrlJob {
  Timestamp getCreatedOn();

  String getUrl();

  String getMethod();
}
