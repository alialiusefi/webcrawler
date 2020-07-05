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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlingJob {

  private final JobManager jobManager;
  private final ExecutorService executorService;
  private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
  private final AtomicInteger totalVisitedPages = new AtomicInteger(0);
  private List<Keyword> keywords;
  private Statistic statistic;
  private CrawlingJobConfig crawlingJobConfig;

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
    if (!visitedUrls.contains(url.getName())
        && visitedUrls.size() <= crawlingJobConfig.getMaxVisitedPages()
        && totalVisitedPages.get() <= crawlingJobConfig.getMaxVisitedPages()
        && getUrlDepth(url.getName()) <= crawlingJobConfig.getLinkDepth()) {
      url = this.getUrlService().saveUrl(url);

      Crawler crawler = new Crawler(url, keywords, this);
      executorService.execute(crawler);
      visitedUrls.add(url.getName());
    }
  }

  private Integer getUrlDepth(String url) {
    Pattern pattern = Pattern.compile("/\\/.+?/g");
    Matcher matcher = pattern.matcher(url);
    return ((Long) matcher.results().count()).intValue();
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
