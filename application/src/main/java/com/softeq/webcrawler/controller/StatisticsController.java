package com.softeq.webcrawler.controller;

import com.softeq.webcrawler.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
@Api("Download CSV statistics files")
public class StatisticsController {

  private static final String FILENAME = "csv-stats.csv";
  private final StatisticService statisticService;

  @GetMapping("/{statisticId}")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Returns a CSV file with all crawls"),
      @ApiResponse(code = 404, message = "Doesn't exist statistics with that statisticId")
  })
  @ApiOperation(value = "Get all statistics in csv file")
  public ResponseEntity<byte[]> getStatistics(@ApiParam("StatisticId that was given by the crawling job")
  @PathVariable Long statisticId) throws IOException {
    return createResponse(statisticService.getCSVStatistics(statisticId).readAllBytes());
  }

  @GetMapping("/{statisticId}/search/top-hits")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Returns a CSV file with top crawls"),
      @ApiResponse(code = 404, message = "Doesn't exist statistics with that statisticId")
  })
  @ApiOperation(value = "Get top statistics in csv file sorted by total hits")
  public ResponseEntity<byte[]> getTopStatistics(@ApiParam("StatisticId that was given by the crawling job")
  @PathVariable Long statisticId, @ApiParam("Amount of records to show") @RequestParam(defaultValue = "10") Integer amountOfRecords)
      throws IOException {
    return createResponse(statisticService.getCSVTopHitsStatistics(statisticId, amountOfRecords).readAllBytes());
  }

  private ResponseEntity<byte[]> createResponse(byte[] file) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentDisposition(ContentDisposition.builder("attachment; filename=" + FILENAME).build());
    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
    return new ResponseEntity<>(file, headers, HttpStatus.OK);
  }
}

