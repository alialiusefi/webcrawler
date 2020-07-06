package com.softeq.webcrawler.manager;

import com.softeq.webcrawler.config.CrawlingJobConfig;
import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.job.CrawlingJob;
import com.softeq.webcrawler.service.CrawlService;
import com.softeq.webcrawler.service.UrlService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobManager {

  private final UrlService urlService;
  private final CrawlService crawlService;
  private final CrawlingJobConfig crawlingJobConfig;

  public void submitCrawlingJob(Statistic statistic, Url seedUrl, List<Keyword> keywords, Optional<Integer> optionalLinkDepth,
      Optional<Integer> optionalMaxVisitedPages) {
    Integer linkDepth = optionalLinkDepth.orElse(crawlingJobConfig.getDefaultLinkDepth());
    Integer maxVisitedPages = optionalMaxVisitedPages.orElse(crawlingJobConfig.getDefaultMaxVisitedPages());

    CompletableFuture.runAsync(() -> {
      CrawlingJob crawlingJob = new CrawlingJob(this,crawlingJobConfig.getThreadCount(), linkDepth, maxVisitedPages);
      crawlingJob.start(statistic, seedUrl, keywords);
    }).join();
  }

  public CrawlService getCrawlService() {
    return crawlService;
  }

  public UrlService getUrlService() {
    return urlService;
  }

  public CrawlingJobConfig getCrawlingJobConfig() {
    return crawlingJobConfig;
  }
}
