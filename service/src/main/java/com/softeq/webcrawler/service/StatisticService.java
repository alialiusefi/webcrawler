package com.softeq.webcrawler.service;

import com.softeq.webcrawler.entity.Statistic;
import java.io.InputStream;

public interface StatisticService {

  Statistic saveStatistic(Statistic statistic);

  InputStream getCSVStatistics(Long statisticId);

  InputStream getCSVTopHitsStatistics(Long statisticId, Integer recordCount);

}
