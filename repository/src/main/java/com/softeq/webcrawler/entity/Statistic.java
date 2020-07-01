package com.softeq.webcrawler.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
public class Statistic {

  @Id
  private Long id;

  private Integer totalHits;

  @OneToMany
  private List<Crawl> crawlList;

}
