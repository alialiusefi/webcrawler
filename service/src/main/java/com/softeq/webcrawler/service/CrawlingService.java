package com.softeq.webcrawler.service;

import com.softeq.webcrawler.entity.Statistic;
import java.util.List;
import java.util.Optional;

public interface CrawlingService {

  Statistic submitNewCrawlingJob(String seedUrl, List<String> keywords, Optional<Integer> linkDepth, Optional<Integer> maxVisitedPages);

}
