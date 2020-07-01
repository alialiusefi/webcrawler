package com.softeq.webcrawler.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.job.StatisticJob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Builder
@Slf4j
public class Crawler implements Runnable {

  private final Url url;
  private final List<Keyword> keywords;
  private final StatisticJob statisticJob;
  /*private Parser*/

  private final WebClient webclient;

  private Integer currentDepth;

  public Crawler(Url url, List<Keyword> keywords, StatisticJob statisticJob) {
    this.url = url;
    this.keywords = keywords;
    this.statisticJob = statisticJob;
    this.webclient = new WebClient(BrowserVersion.BEST_SUPPORTED);
  }

  @Override
  public void run() {
    try {
      HtmlPage page = webclient.getPage(url.getUrl());

      Document document = Jsoup.parse(page.asXml());

      List<String> texts = extractTextFromDocument(document);

      List<String> urls = extractUrlsFromTexts(texts);
      urls.forEach(statisticJob::submitNewUrl);


    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }

  }

  //private matchElementWithKeyword();

  private List<String> extractTextFromDocument(Document document) {
    List<String> textsExtracted = new ArrayList<>();

    Elements elements = document.getAllElements();
    elements.forEach(
        element -> {
          if (element.hasText()) {
            String text = element.text();
            textsExtracted.add(text);
          }
        }
    );

    return textsExtracted;
  }

  private void submitCrawlToStatisticJob(Keyword keyword, Url url, Integer amountOfHits) {

  }

  private List<String> extractUrlsFromTexts(List<String> texts) {
    // check if texts has url, remove if found
    throw new RuntimeException("a");
  }
}
