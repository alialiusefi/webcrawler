package com.softeq.webcrawler.controller;

import com.softeq.webcrawler.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

  private final StatisticService statisticService;

  @GetMapping("/{statisticId}")
  public ResponseEntity getStatistics(@PathVariable Long statisticId) {
    throw new RuntimeException("q");
  }

  @GetMapping("/{statisticId}/search/top-hits")
  public ResponseEntity getTopStatistics(@PathVariable Long statisticId,
      @RequestParam(defaultValue = "10") Integer amountOfRecords) {
    throw new RuntimeException("q");
  }
}

