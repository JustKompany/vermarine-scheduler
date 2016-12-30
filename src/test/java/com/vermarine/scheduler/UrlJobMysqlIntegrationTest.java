package com.vermarine.scheduler;

import com.vermarine.database.DatabaseFacade;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;

public class UrlJobMysqlIntegrationTest {
  private static DatabaseFacade testDatabaseFacade;

  @BeforeClass
  public static void beforeClass() throws Exception {
    Map<String, String> testDatabaseProperties = new HashMap<>();
    testDatabaseProperties.put("VERAMINE_DATABASE_DRIVER", "com.mysql.jdbc.Driver");
    testDatabaseProperties.put("VERAMINE_DATABASE_URL", "jdbc:mysql://localhost/vermarine");
    testDatabaseProperties.put("VERAMINE_DATABASE_USERNAME", "root");
    testDatabaseProperties.put("VERAMINE_DATABASE_PASSWORD", "password");
    testDatabaseFacade = new DatabaseFacade(testDatabaseProperties);
  }

  @Before
  public void before() {
    testDatabaseFacade.open();
  }

  @Test
  public void testSaveAndFindById() {
    UrlJobActiveJdbcImpl urlJob = new UrlJobActiveJdbcImpl("www.example.com", "GET");
    urlJob.save();

    UrlJobActiveJdbcImpl loadedUrlJob = UrlJobActiveJdbcImpl.findById(urlJob.getId());
    Assert.assertEquals("www.example.com", loadedUrlJob.getUrl());
  }

  @After
  public void after() {
    testDatabaseFacade.close();
  }
}
