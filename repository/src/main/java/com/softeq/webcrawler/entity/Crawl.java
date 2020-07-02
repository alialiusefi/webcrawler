package com.softeq.webcrawler.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Builder
public class Crawl {

  @Id
  private Long id;

  @ManyToOne
  private Url url;

  @ManyToOne
  private Keyword keyword;

  private Integer numberOfHits;

  @ManyToOne
  private Statistic statistic;
}
