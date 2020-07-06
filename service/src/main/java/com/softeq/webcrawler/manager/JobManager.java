package com.softeq.webcrawler.manager;

import com.softeq.webcrawler.config.CrawlingJobConfig;
import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.job.CrawlingJob;
import com.softeq.webcrawler.service.CrawlService;
import com.softeq.webcrawler.service.StatisticService;
import com.softeq.webcrawler.service.UrlService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class JobManager {

  private final UrlService urlService;
  private final CrawlService crawlService;
  private final CrawlingJobConfig crawlingJobConfig;
  private final StatisticService statisticService;

  public void submitCrawlingJob(Statistic statistic, Url seedUrl, List<Keyword> keywords, Optional<Integer> optionalLinkDepth,
      Optional<Integer> optionalMaxVisitedPages) {
    Integer linkDepth = optionalLinkDepth.orElse(crawlingJobConfig.getDefaultLinkDepth());
    Integer maxVisitedPages = optionalMaxVisitedPages.orElse(crawlingJobConfig.getDefaultMaxVisitedPages());

    CompletableFuture.runAsync(() -> {
          CrawlingJob crawlingJob = new CrawlingJob(this, crawlingJobConfig.getThreadCount(), linkDepth, maxVisitedPages);

          crawlingJob.start(statistic, seedUrl, keywords);
        }
    ).join();
  }

}
