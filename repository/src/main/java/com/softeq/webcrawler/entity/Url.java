package com.softeq.webcrawler.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Url {

  @Id
  private Long id;

  private String url;
}
