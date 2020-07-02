package com.softeq.webcrawler.service;

import com.softeq.webcrawler.entity.Statistic;
import java.util.List;

public interface CrawlingService {

  Statistic submitNewCrawlingJob(String seedUrl, List<String> keywords);

}
