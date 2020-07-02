package com.softeq.webcrawler.job;

import com.softeq.webcrawler.config.CrawlingJobConfig;
import com.softeq.webcrawler.crawler.Crawler;
import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.manager.JobManager;
import com.softeq.webcrawler.service.CrawlService;
import com.softeq.webcrawler.service.UrlService;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CrawlingJob {

  private final JobManager jobManager;
  private final ExecutorService executorService;
  private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
  private List<Keyword> keywords;
  private Statistic statistic;
  private CrawlingJobConfig crawlingJobConfig;
  private final AtomicInteger totalVisitedPages = new AtomicInteger(0);

  //private final

  public CrawlingJob(JobManager jobManager) {
    this.jobManager = jobManager;
    this.crawlingJobConfig = jobManager.getCrawlingJobConfig();
    this.executorService = Executors.newFixedThreadPool(crawlingJobConfig.getThreadCount());
  }

  public void start(Statistic statistic, Url seedUrl, List<Keyword> keywords) {
    this.statistic = statistic;
    this.keywords = keywords;
    submitNewUrl(seedUrl);
  }

  public void submitNewUrl(Url url) {
    if (!visitedUrls.contains(url.getUrl()) && visitedUrls.size() <= crawlingJobConfig.getMaxVisitedPages()) {
      // todo: check for link depth and visit limit
      url = this.getUrlService().saveUrl(url);

      Crawler crawler = new Crawler(url, keywords, this);
      executorService.execute(crawler);

      visitedUrls.add(url.getUrl());
    }
  }

  public AtomicInteger getTotalVisitedPages() {
    return totalVisitedPages;
  }

  public Statistic getStatistic() {
    return statistic;
  }

  public CrawlService getCrawlService() {
    return jobManager.getCrawlService();
  }

  public UrlService getUrlService() {
    return jobManager.getUrlService();
  }
}
