package com.softeq.webcrawler.service.impl;

import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.repository.StatisticRepository;
import com.softeq.webcrawler.service.CrawlService;
import com.softeq.webcrawler.service.StatisticService;
import com.softeq.webcrawler.view.CrawlView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

  private static final String URL_HEADER = "URL";
  private static final String TOTALHITS_HEADER = "TOTAL HITS";
  private static final String COLUMN_DELIMITER = ",";
  private static final String ROW_DELIMITER = "\n";
  private static final Charset CHARSET = StandardCharsets.UTF_8;
  private final StatisticRepository statisticRepository;
  private final CrawlService crawlService;

  public Statistic saveStatistic(Statistic statistic) {
    return statisticRepository.save(statistic);
  }

  @Override
  public InputStream getCSVStatistics(Long statisticId) {
    return createCSVFile(statisticId);
  }

  //todo: builder here for csv!
  @Override
  public InputStream getCSVTopHitsStatistics(Long statisticId, Integer recordCount) {
    List<CrawlView> topCrawls = crawlService.getTopCrawlsByStatisticIdSortByTotalHitsDesc(statisticId, recordCount);
    String header = createHeader(topCrawls.get(0));
    String records = createRecords(topCrawls);

    String csvFile = header + records;

    return new ByteArrayInputStream(csvFile.getBytes(CHARSET));
  }

  @Override
  public Statistic getStatisticById(Long statisticId) {
    return statisticRepository.getOne(statisticId);
  }

  public InputStream createCSVFile(Long statisticId) {

    List<CrawlView> views = crawlService.getCrawlsByStatisticId(statisticId);
    String header = createHeader(views.get(0));
    String records = createRecords(views);

    String csvFile = header + records;

    return new ByteArrayInputStream(csvFile.getBytes(CHARSET));
  }

  private String createHeader(CrawlView crawlView) {
    StringJoiner joiner = new StringJoiner(COLUMN_DELIMITER);

    joiner.add(URL_HEADER);

    List<String> keywords = Arrays.asList(crawlView.getKeywords().split(","));
    keywords.forEach(joiner::add);

    joiner.add(TOTALHITS_HEADER);

    joiner.add(ROW_DELIMITER);

    return joiner.toString();
  }

  private String createRecords(List<CrawlView> crawls) {
    StringJoiner joiner = new StringJoiner(ROW_DELIMITER);

    crawls.forEach(o -> {
      String record = createRecord(o);
      joiner.add(record);
    });

    return joiner.toString();
  }

  private String createRecord(CrawlView view) {
    StringJoiner joiner = new StringJoiner(COLUMN_DELIMITER);

    joiner.add(view.getUrls());

    List<String> hits = Arrays.asList(view.getHitsPerKeyword().split(","));
    hits.forEach(joiner::add);

    joiner.add(view.getTotalHits().toString());

    return joiner.toString();
  }

}
