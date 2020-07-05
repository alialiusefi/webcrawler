package com.softeq.webcrawler.service;

import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import java.util.List;

public interface KeywordService {

  Keyword saveKeyword(Keyword keyword);

  List<Keyword> getAllKeywordsByStatistic(Statistic statistic);
}
