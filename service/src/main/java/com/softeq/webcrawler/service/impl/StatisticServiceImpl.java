package com.softeq.webcrawler.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.softeq.webcrawler.entity.Crawl;
import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Statistic;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.repository.StatisticRepository;
import com.softeq.webcrawler.service.KeywordService;
import com.softeq.webcrawler.service.StatisticService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
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
  private final KeywordService keywordService;

  public Statistic saveStatistic(Statistic statistic) {
    return statisticRepository.save(statistic);
  }

  @Override
  public InputStream getCSVStatistics(Long statisticId) {
    return createCSVFile(statisticId);
  }

  @Override
  public InputStream getCSVTopHitsStatistics(Long statisticId, Integer recordCount) {
    return null;
  }

  @Override
  public Statistic getStatisticById(Long statisticId) {
    return statisticRepository.getOne(statisticId);
  }

  public InputStream createCSVFile(Long statisticId) {
    Statistic statistic = getStatisticById(statisticId);

    List<Keyword> keywords = keywordService.getAllKeywordsByStatistic(statistic);

    String header = createHeader(keywords);
    String records = createRecords(statistic.getCrawlList());

    String csvFile = header + records;

    return new ByteArrayInputStream(csvFile.getBytes(CHARSET));
  }

  private String createHeader(List<Keyword> keywords) {
    StringJoiner joiner = new StringJoiner(COLUMN_DELIMITER);

    joiner.add(URL_HEADER);

    keywords.forEach(keyword -> joiner.add(keyword.getName()));

    joiner.add(TOTALHITS_HEADER);

    joiner.add(ROW_DELIMITER);

    return joiner.toString();
  }

  private String createRecords(List<Crawl> crawls) {
    Multimap<Url, Pair<Keyword, Integer>> multimap = ArrayListMultimap.create();

    crawls.forEach(crawl -> multimap.put(crawl.getUrl(), Pair.of(crawl.getKeyword(), crawl.getNumberOfHits())));

    Set<Url> urls = multimap.keySet();

    StringJoiner joiner = new StringJoiner(ROW_DELIMITER);

    urls.forEach(o -> {
      String record = createRecord(o, multimap.get(o));
      joiner.add(record);
    });

    return joiner.toString();
  }

  private String createRecord(Url url, Collection<Pair<Keyword, Integer>> hits) {
    AtomicReference<Integer> totalHits = new AtomicReference<>(0);
    StringJoiner joiner = new StringJoiner(COLUMN_DELIMITER);

    joiner.add(url.getName());

    hits.forEach(o -> {
      joiner.add(o.getRight().toString());
      totalHits.updateAndGet(v -> v + o.getRight());
    });

    joiner.add(totalHits.get().toString());

    return joiner.toString();
  }

}
