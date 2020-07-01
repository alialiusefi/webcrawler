package com.softeq.webcrawler.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
public class Keyword {

  @Id
  private Long id;

  private String keyword;
}
