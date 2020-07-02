package com.softeq.webcrawler.service.impl;

import com.softeq.webcrawler.entity.Url;
import com.softeq.webcrawler.repository.UrlRepository;
import com.softeq.webcrawler.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

  public UrlRepository urlRepository;

  @Override
  public Url saveUrl(Url url) {
    return urlRepository.save(url);
  }
}
