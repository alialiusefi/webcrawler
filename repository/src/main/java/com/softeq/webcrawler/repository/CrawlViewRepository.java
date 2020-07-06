package com.softeq.webcrawler.repository;

import com.softeq.webcrawler.view.CrawlView;
import java.util.List;

public interface CrawlViewRepository {

  List<CrawlView> getTopCrawlsByStatisticIdSortByTotalHitsDesc(Long statisticId, Integer limitParam);

  List<CrawlView> getCrawlsByStatisticId(Long statisticId);

}
