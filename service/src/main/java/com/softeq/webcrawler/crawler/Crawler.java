package com.softeq.webcrawler.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.softeq.webcrawler.entity.Crawl;
import com.softeq.webcrawler.entity.Keyword;
import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.job.CrawlingJob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Slf4j
public class Crawler implements Runnable {

  private final Url url;
  private final List<Keyword> keywords;
  private final CrawlingJob crawlingJob;
  /*private Parser*/

  private final WebClient webclient;

  public Crawler(Url url, List<Keyword> keywords, CrawlingJob crawlingJob) {
    this.url = url;
    this.keywords = keywords;
    this.crawlingJob = crawlingJob;
    this.webclient = new WebClient(BrowserVersion.BEST_SUPPORTED);
  }

  @Override
  public void run() {
    try {
      HtmlPage page = webclient.getPage(url.getUrl());
      crawlingJob.getTotalVisitedPages().incrementAndGet();

      Document document = Jsoup.parse(page.asXml());

      List<String> texts = extractTextFromDocument(document);

      List<String> urls = removeUrlsFromTexts(texts);
      urls.forEach(urlStr -> {
        Url currUrl = Url.builder().url(urlStr).build();
        crawlingJob.submitNewUrl(currUrl);
      });

      HashMap<Keyword, Integer> hits = searchForKeyWordsInText(texts);

      Set<Entry<Keyword, Integer>> set = hits.entrySet();
      set.forEach(this::saveCrawlResults);

    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }

  }

  private HashMap<Keyword, Integer> searchForKeyWordsInText(List<String> texts) {
    HashMap<Keyword, Integer> hits = new HashMap<>();

    keywords.forEach(keyword -> hits.put(keyword, 0));

    texts.forEach(text -> {
      String lowerCaseText = text.toLowerCase();
      String withoutPunctuation = lowerCaseText.replaceAll("\\p{Punct}", "");

      keywords.forEach(
          keyword -> {
            if (keyword.getKeyword().equals(withoutPunctuation)) {
              hits.replace(keyword, hits.get(keyword) + 1);
            }
          }
      );
    });

    return hits;
  }

  private List<String> extractTextFromDocument(Document document) {
    List<String> textsExtracted = new ArrayList<>();

    Elements elements = document.getAllElements();
    elements.forEach(
        element -> {
          if (element.hasText()) {
            String text = element.text();
            log.debug(text);
            textsExtracted.add(text);
          }
        }
    );

    return textsExtracted;
  }

  private void saveCrawlResults(Entry<Keyword, Integer> entry) {
    Crawl crawl = Crawl.builder()
        .url(url)
        .keyword(entry.getKey())
        .numberOfHits(entry.getValue())
        .statistic(crawlingJob.getStatistic())
        .build();

    this.crawlingJob.getCrawlService().saveCrawl(crawl);
  }

  private List<String> removeUrlsFromTexts(List<String> texts) {
    // check if texts has url, remove if found
    throw new RuntimeException("a");
  }
}
