package com.softeq.webcrawler.service.impl;

import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.manager.JobManager;
import com.softeq.webcrawler.service.CrawlingService;
import com.softeq.webcrawler.service.KeywordService;
import com.softeq.webcrawler.service.StatisticService;
import com.softeq.webcrawler.service.UrlService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlingServiceImpl implements CrawlingService {

  private final KeywordService keywordService;
  private final UrlService urlService;
  private final StatisticService statisticService;
  private final JobManager jobManager;

  @Override
  public Statistic submitNewCrawlingJob(String seedUrl, List<String> keywords) {
    List<Keyword> keywordEntityList = new ArrayList<>();
    keywords.forEach(keywordStr -> {
      Keyword keywordEntity = Keyword.builder().name(keywordStr).build();
      keywordEntity = keywordService.saveKeyword(keywordEntity);
      keywordEntityList.add(keywordEntity);
    });

    Url url = Url.builder().name(seedUrl).build();
    url = urlService.saveUrl(url);

    Statistic statistic = Statistic.builder().build();
    statistic = statisticService.saveStatistic(statistic);

    jobManager.submitCrawlingJob(statistic, url, keywordEntityList);

    return statistic;
  }
}
