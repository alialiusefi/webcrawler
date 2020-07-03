package com.softeq.webcrawler.controller;

import com.softeq.webcrawler.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

  private ResponseEntity<byte[]> createResponse(byte[] file) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentDisposition(ContentDisposition.builder("attachment; filename=" + "csv-stats.csv").build());
    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
    return new ResponseEntity<>(file, headers, HttpStatus.OK);
  }
}

