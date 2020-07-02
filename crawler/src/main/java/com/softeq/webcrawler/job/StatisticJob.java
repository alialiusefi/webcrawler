package com.softeq.webcrawler.job;

import com.softeq.webcrawler.crawler.Crawler;
import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.jobmanager.JobManager;
import com.softeq.webcrawler.service.CrawlService;
import com.softeq.webcrawler.service.UrlService;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Value;

public class StatisticJob {

  private final JobManager jobManager;
  private final ExecutorService executorService;
  private List<Keyword> keywords;
  private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
  private Statistic statistic;
  @Value("${app.crawler.thread-count:5}")
  private Integer threadCount;
  @Value("${app.crawler.link-depth:8}")
  private Integer linkDepth;
  @Value("${app.crawler.max-visited-pages:10000}")
  private Integer maxVisitedPages;
  private AtomicInteger totalVisitedPages;

  //private final

  public StatisticJob(JobManager jobManager) {
    this.jobManager = jobManager;
    this.executorService = Executors.newFixedThreadPool(threadCount);
  }

  public void start(Statistic statistic, Url seedUrl, List<Keyword> keywords) {
    this.statistic = statistic;
    this.keywords = keywords;
    submitNewUrl(seedUrl);
  }

  public void submitNewUrl(Url url) {
    if (!visitedUrls.contains(url.getUrl()) && visitedUrls.size() <= maxVisitedPages) {
      // todo: check for link depth
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
