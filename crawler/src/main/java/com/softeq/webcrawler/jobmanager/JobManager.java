package com.softeq.webcrawler.jobmanager;

import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.job.StatisticJob;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobManager {

  //private final KeywordService keywordService;
  //private final UrlService urlService;
  //private final CrawlService crawlService;

  public void submitStatisticJob(Statistic statistic, Url seedUrl, List<Keyword> keywords) {
    StatisticJob statisticJob = new StatisticJob();
    statisticJob.start(statistic, seedUrl, keywords);
  }

}
