package com.softeq.webcrawler.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class CrawlingJobConfig {

  @Value("${app.crawler.thread-count:5}")
  private Integer threadCount;
  @Value("${app.crawler.link-depth:8}")
  private Integer linkDepth;
  @Value("${app.crawler.max-visited-pages:10000}")
  private Integer maxVisitedPages;


}
