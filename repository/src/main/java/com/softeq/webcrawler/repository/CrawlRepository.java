package com.softeq.webcrawler.repository;

import com.softeq.webcrawler.entity.Crawl;
import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlRepository extends JpaRepository<Crawl, Long> {

  @Query(value = "select crawl.numberOfHits from Crawl as crawl where crawl.keyword = :keyword and crawl.url = :url")
  Integer getNumberOfHitsByKeywordAndUrl(Keyword keyword, Url url);


}
