package com.softeq.webcrawler.service.impl;

import com.softeq.webcrawler.entity.Crawl;
import com.softeq.webcrawler.repository.CrawlRepository;
import com.softeq.webcrawler.service.CrawlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlServiceImpl implements CrawlService {

  private final CrawlRepository crawlRepository;

  @Override
  public Crawl saveCrawl(Crawl crawl) {
    return crawlRepository.save(crawl);
  }

}
