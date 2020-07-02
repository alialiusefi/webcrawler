package com.softeq.webcrawler.repository;

import com.softeq.webcrawler.entity.Crawl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlRepository extends JpaRepository<Crawl,Long> {
}
