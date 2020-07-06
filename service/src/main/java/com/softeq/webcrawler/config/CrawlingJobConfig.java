package com.softeq.webcrawler.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class CrawlingJobConfig {

  @Value("${app.crawler.thread-count:5}")
  private Integer threadCount;
  @Value("${app.crawler.default-link-depth:8}")
  private Integer defaultLinkDepth;
  @Value("${app.crawler.default-max-visited-pages:10000}")
  private Integer defaultMaxVisitedPages;


}
