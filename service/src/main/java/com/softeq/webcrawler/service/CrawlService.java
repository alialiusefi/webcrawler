package com.softeq.webcrawler.service;

import com.softeq.webcrawler.entity.Crawl;
import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Url;

public interface CrawlService {

  Crawl saveCrawl(Crawl crawl);

  Integer getNumberOfHitsByKeywordAndUrl(Keyword keyword, Url url);
}
