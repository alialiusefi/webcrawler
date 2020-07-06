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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class Crawler implements Runnable {

  private static final String PUNCTUATION_REGEX = "\\p{Punct}";
  private static final String URL_REGEX = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
  private static final String DIRECTORY_REGEX = "^(.+)\\/([^/]+)$";
  private final Url url;
  private final List<Keyword> keywords;
  private final CrawlingJob crawlingJob;
  private final WebClient webclient;
  private boolean isDone;

  public Crawler(Url url, List<Keyword> keywords, CrawlingJob crawlingJob) {
    this.url = url;
    this.keywords = keywords;
    this.crawlingJob = crawlingJob;
    this.webclient = new WebClient(BrowserVersion.BEST_SUPPORTED);
    this.isDone = false;
  }

  @Override
  public void run() {
    try {
      HtmlPage page = webclient.getPage(url.getName());
      crawlingJob.getTotalVisitedPages().incrementAndGet();

      Document document = Jsoup.parse(page.asXml());

      List<String> texts = extractTextFromDocument(document);

      List<String> urls = getUrlsFromDocuments(document);

      urls.forEach(urlStr -> {
        Url currUrl = Url.builder().name(urlStr).build();
        crawlingJob.submitNewUrl(currUrl);
      });

      HashMap<Keyword, Integer> hits = searchForKeyWordsInText(texts);

      Set<Entry<Keyword, Integer>> set = hits.entrySet();
      set.forEach(this::saveCrawlResults);
      isDone = true;
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    } finally {
      isDone = true;
    }

  }

  private HashMap<Keyword, Integer> searchForKeyWordsInText(List<String> texts) {
    HashMap<Keyword, Integer> hits = new HashMap<>();

    keywords.forEach(keyword -> hits.put(keyword, 0));

    texts.forEach(text -> {
      String lowerCaseText = text.toLowerCase();
      String withoutPunctuation = lowerCaseText.replaceAll(PUNCTUATION_REGEX, "");

      keywords.forEach(
          keyword -> {
            if (keyword.getName().equals(withoutPunctuation)) {
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
    Element rootElement = elements.get(0);
    String text;
    if (rootElement.hasText()) {
      text = rootElement.text();
      String[] tokens = text.split(" ");
      textsExtracted.addAll(Arrays.asList(tokens));
    }
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

  private List<String> getUrlsFromDocuments(Document document) {
    List<String> listOfUrls = new ArrayList<>();
    Elements elements = document.getAllElements();
    elements.forEach(element -> {
      if (element.hasAttr("href")) {
        String urlHref = element.attr("href");
        if (urlHref.matches(URL_REGEX)) {
          listOfUrls.add(urlHref);
        }
        if (urlHref.matches(DIRECTORY_REGEX)) {
          String correctUrl = this.url + urlHref;
          if (correctUrl.matches(URL_REGEX)) {
            listOfUrls.add(urlHref);
          }
        }
      }
    });
    return listOfUrls;
  }

  public boolean isDone() {
    return isDone;
  }
}
