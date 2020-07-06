package com.softeq.webcrawler.repository;

import com.softeq.webcrawler.entity.Keyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

  @Query(value = "select distinct k.id,k.name from keyword as k "
      + "inner join crawl on k.id = crawl.keyword_id "
      + "inner join statistic on crawl.statistic_id = statistic.id where statistic.id = :id", nativeQuery = true)
  List<Keyword> getAllKeywordsByStatisticId(@Param("id") Long statisticId);
}