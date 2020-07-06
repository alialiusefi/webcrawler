package com.softeq.webcrawler.repository.impl;

import com.softeq.webcrawler.repository.CrawlViewRepository;
import com.softeq.webcrawler.view.CrawlView;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CrawlViewRepositoryImpl implements CrawlViewRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<CrawlView> getTopCrawlsByStatisticIdSortByTotalHitsDesc(Long statisticId, Integer limitParam) {
    StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("CrawlView.topCrawls");
    query.setParameter("statisticId", statisticId);
    query.setParameter("limitParam", limitParam);
    List result = query.getResultList();
    return (List<CrawlView>) result;
  }

  @Override
  public List<CrawlView> getCrawlsByStatisticId(Long statisticId) {
    StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("CrawlView.crawls");
    query.setParameter("statisticId", statisticId);
    List result = query.getResultList();
    return (List<CrawlView>) result;
  }
}
