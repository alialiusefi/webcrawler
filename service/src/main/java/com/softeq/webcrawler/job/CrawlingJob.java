package com.softeq.webcrawler.job;

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
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CrawlingJob {

  private static final String DOUBLE_SLASH = "//";
  private final JobManager jobManager;
  private final ExecutorService executorService;
  private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
  private final AtomicInteger totalVisitedPages = new AtomicInteger(0);
  private final Integer maxVisitedPages;
  private final Integer linkDepth;
  private final ConcurrentLinkedDeque<Crawler> crawlersTasks = new ConcurrentLinkedDeque<>();
  private List<Keyword> keywords;
  private Statistic statistic;

  public CrawlingJob(JobManager jobManager, Integer threadCount, Integer linkDepth, Integer maxVisitedPages) {
    this.jobManager = jobManager;
    this.linkDepth = linkDepth;
    this.maxVisitedPages = maxVisitedPages;
    this.executorService = Executors.newFixedThreadPool(threadCount);
  }

  public void start(Statistic statistic, Url seedUrl, List<Keyword> keywords) {
    this.statistic = statistic;
    this.keywords = keywords;
    submitNewUrl(seedUrl);
  }

  public void submitNewUrl(Url url) {
    if (!visitedUrls.contains(url.getName())
        && visitedUrls.size() <= maxVisitedPages
        && totalVisitedPages.get() <= maxVisitedPages
        && getUrlDepth(url.getName()) <= linkDepth) {
      url = this.getUrlService().saveUrl(url);

      Crawler crawler = new Crawler(url, keywords, this);
      crawlersTasks.add(crawler);
      executorService.execute(crawler);
      visitedUrls.add(url.getName());
    }
  }

  private Integer getUrlDepth(String url) {
    int httpsIdx = url.indexOf(DOUBLE_SLASH);
    String trimmedUrl;
    if (httpsIdx != -1) {
      trimmedUrl = url.substring(httpsIdx + DOUBLE_SLASH.length());
    } else {
      trimmedUrl = url;
    }
    String[] tokens = trimmedUrl.split("/");
    return tokens.length - 1;
  }

  public boolean isDone() {
    for (Crawler i :
        crawlersTasks) {
      if (!i.isDone()) {
        return false;
      }
    }
    return true;
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