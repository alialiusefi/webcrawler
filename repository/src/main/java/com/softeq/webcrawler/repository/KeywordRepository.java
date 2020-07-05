package com.softeq.webcrawler.repository;

import com.softeq.webcrawler.entity.Keyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

  @Query(value = "select distinct k from Keyword k "
      + "join k.crawlList c "
      + "join c.statistic f where f.id = :id")
  List<Keyword> getAllKeywordsByStatisticId(@Param("id") Long staticsticId);

}
