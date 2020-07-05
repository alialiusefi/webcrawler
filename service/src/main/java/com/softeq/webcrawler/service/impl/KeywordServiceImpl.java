package com.softeq.webcrawler.service.impl;

import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.repository.KeywordRepository;
import com.softeq.webcrawler.service.KeywordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

  private final KeywordRepository keywordRepository;

  @Override
  public Keyword saveKeyword(Keyword keyword) {
    return keywordRepository.save(keyword);
  }

  @Override
  public List<Keyword> getAllKeywordsByStatistic(Statistic statistic) {
    return keywordRepository.getAllKeywordsByStatisticId(statistic.getId());
  }
}
