package com.softeq.webcrawler.controller;

import com.softeq.webcrawler.dto.CrawlingJobDTO;
import com.softeq.webcrawler.dto.StatisticDTO;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.service.CrawlingService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crawling-jobs")
public class CrawlingJobController {

  private final CrawlingService crawlingService;

  @PostMapping("/")
  public StatisticDTO submitCrawlingJob(@RequestBody @Valid CrawlingJobDTO crawlingJobDTO) {
    Statistic statistic = crawlingService.submitNewCrawlingJob(crawlingJobDTO.getSeedUrl(), crawlingJobDTO.getKeywords());
    return StatisticDTO.builder().statisticId(statistic.getId()).build();
  }


}
