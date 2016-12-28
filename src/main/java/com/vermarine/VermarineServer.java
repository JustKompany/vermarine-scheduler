package com.vermarine;

import com.vermarine.scheduler.UrlJobExecutor;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class VermarineServer extends AbstractVerticle {
  public void start() throws SchedulerException {
    Configuration configuration = new Configuration();

    HttpServer httpServer = vertx.createHttpServer();

    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.start();

    Router router = Router.router(vertx);

    router.route(HttpMethod.POST, "/cron/dailyAtHourAndMinute/:hour/:minute")
      .handler(event -> {
        final String hour = event.pathParam("hour");
        final String minute = event.pathParam("minute");

        CronTrigger trigger = TriggerBuilder.newTrigger()
          .startNow()
          .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(Integer.parseInt(hour), Integer.parseInt(minute)))
          .build();

        JobDetail jobDetail = JobBuilder.newJob(UrlJobExecutor.class)
          .usingJobData("url_job_id", (String) null)
          .withIdentity("url_job" + hour, "url-jobs")
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
