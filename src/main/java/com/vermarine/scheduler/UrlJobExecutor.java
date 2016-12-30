package com.vermarine.scheduler;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class UrlJobExecutor implements Job {
  private static UrlJobHandler urlJobHandler;

  public static void setUrlJobHandler(UrlJobHandler urlJobHandler) {
    UrlJobExecutor.urlJobHandler = urlJobHandler;
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    if(urlJobHandler == null) {
      throw new JobExecutionException("urlJobHandler is null");
    }

    System.out.println("Preparing to make handle url job");

    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    Long jobId = (Long) jobDataMap.get("url_job_id");
    UrlJobActiveJdbcImpl urlJob = UrlJobActiveJdbcImpl.findById(jobId);

    UrlJobResult urlJobResult;
    try {
      urlJobResult = urlJobHandler.handle(urlJob);
    } catch(Throwable throwable) {
      System.out.println("Failure: " + throwable.getMessage());
      throw throwable;
    }
    System.out.println("UrlJobResult: " + urlJobResult.getBody());
  }
}
