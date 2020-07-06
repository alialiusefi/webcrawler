package com.softeq.webcrawler.controller;

import com.softeq.webcrawler.dto.CrawlingJobDTO;
import com.softeq.webcrawler.dto.StatisticDTO;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.service.CrawlingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crawling-jobs")
@Api("Start crawling the web!")
public class CrawlingJobController {

  private final CrawlingService crawlingService;

  @PostMapping("/")
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  @ApiOperation(value = "Submit crawling job", notes = "Multiple keywords are supported")
  @ApiResponses(value = {
      @ApiResponse(code = 202, message = "Job accepted for processing"),
      @ApiResponse(code = 400, message = "Bad request given")
  })
  public StatisticDTO submitCrawlingJob(@ApiParam("DTO object to pass keywords & seed url") @RequestBody @Valid CrawlingJobDTO crawlingJobDTO) {
    Statistic statistic = crawlingService.submitNewCrawlingJob(crawlingJobDTO.getSeedUrl(), crawlingJobDTO.getKeywords(),
        Optional.of(crawlingJobDTO.getLinkDepth()), Optional.of(crawlingJobDTO.getMaxVisitedPages()));

    return StatisticDTO.builder().statisticId(statistic.getId()).build();
  }


}
