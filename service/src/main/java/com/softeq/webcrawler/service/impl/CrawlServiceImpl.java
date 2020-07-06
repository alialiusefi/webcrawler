package com.softeq.webcrawler.service.impl;

import com.softeq.webcrawler.entity.Crawl;
import com.softeq.webcrawler.repository.CrawlRepository;
import com.softeq.webcrawler.repository.CrawlViewRepository;
import com.softeq.webcrawler.service.CrawlService;
import com.softeq.webcrawler.view.CrawlView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlServiceImpl implements CrawlService {

  private final CrawlRepository crawlRepository;
  private final CrawlViewRepository crawlViewRepository;

  @Override
  public Crawl saveCrawl(Crawl crawl) {
    return crawlRepository.save(crawl);
  }

  @Override
  public List<CrawlView> getTopCrawlsByStatisticIdSortByTotalHitsDesc(Long statisticId, Integer limitParam) {
    return crawlViewRepository.getTopCrawlsByStatisticIdSortByTotalHitsDesc(statisticId, limitParam);
  }

  @Override
  public List<CrawlView> getCrawlsByStatisticId(Long statisticId) {
    return crawlViewRepository.getCrawlsByStatisticId(statisticId);
  }

}
