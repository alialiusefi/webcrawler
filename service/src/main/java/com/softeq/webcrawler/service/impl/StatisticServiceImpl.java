package com.softeq.webcrawler.service.impl;

import com.softeq.webcrawler.repository.StatisticRepository;
import com.softeq.webcrawler.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

  private final StatisticRepository statisticRepository;


}
