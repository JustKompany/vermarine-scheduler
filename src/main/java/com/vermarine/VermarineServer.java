package com.vermarine;

import com.vermarine.configuration.RequiredConfigurationMissingException;
import com.vermarine.database.DatabaseFacade;
import com.vermarine.scheduler.UrlJobActiveJdbcImpl;
import com.vermarine.scheduler.UrlJobExecutor;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Map;

public class VermarineServer extends AbstractVerticle {
  public void start() throws SchedulerException, RequiredConfigurationMissingException {
    Map<String, String> environment = System.getenv();
    VermarineConfiguration configuration = new VermarineConfiguration(environment);

    HttpServer httpServer = vertx.createHttpServer();

    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.start();

    DatabaseFacade databaseFacade = new DatabaseFacade(environment);

    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());

    router.route(HttpMethod.POST, "/cron/dailyAtHourAndMinute/:hour/:minute")
      .handler(event -> {
        final String hour = event.pathParam("hour");
        final String minute = event.pathParam("minute");

        JsonObject body = event.getBodyAsJson();
        String url = body.getString("url");
        String method = body.getString("method");

        Long urlJobId = null;
        try {
          urlJobId = databaseFacade.execute(() -> {
            UrlJobActiveJdbcImpl urlJob = new UrlJobActiveJdbcImpl(url, method);
            urlJob.save();
            return urlJob.getLongId();
          });
        } catch (Exception e) {
          System.out.println("Failed to save urlJob: " + e.getMessage());
        }

        CronTrigger trigger = TriggerBuilder.newTrigger()
          .startNow()
          .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(Integer.parseInt(hour), Integer.parseInt(minute)))
          .build();

        JobDetail jobDetail = JobBuilder.newJob(UrlJobExecutor.class)
          .usingJobData("url_job_id", urlJobId)
          .withIdentity("url_job_" + urlJobId, "url-jobs")
          .build();

        try {
          scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {}

        Date nextFireTime = trigger.getNextFireTime();
        System.out.println("Next Fire Time: " + nextFireTime);

        event.response().setChunked(true);
        event.response().write("success\n");
        event.response().end();
      });

    httpServer.requestHandler(router::accept).listen(configuration.getPort());
  }
}
