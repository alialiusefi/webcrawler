package com.softeq.webcrawler.jobmanager;

import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.job.StatisticJob;
import com.softeq.webcrawler.service.CrawlService;
import com.softeq.webcrawler.service.KeywordService;
import com.softeq.webcrawler.service.UrlService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobManager {

  private final KeywordService keywordService;
  private final UrlService urlService;
  private final CrawlService crawlService;

  public void submitStatisticJob(Statistic statistic, Url seedUrl, List<Keyword> keywords) {
    StatisticJob statisticJob = new StatisticJob(this);
    statisticJob.start(statistic, seedUrl, keywords);
  }

  public CrawlService getCrawlService() {
    return crawlService;
  }

  public UrlService getUrlService() {
    return urlService;
  }

  public KeywordService getKeywordService() {
    return keywordService;
  }
}
