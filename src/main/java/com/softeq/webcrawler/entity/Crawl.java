package com.softeq.webcrawler.entity;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Crawl {

  @Id
  private Long id;

  @ManyToOne
  private Url url;

  @ManyToOne
  private Keyword keyword;

  private Integer numberOfHits;

  private LocalDateTime crawlTimestamp;

}
