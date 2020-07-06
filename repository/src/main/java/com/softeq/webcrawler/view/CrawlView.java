package com.softeq.webcrawler.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedStoredProcedureQueries(
    value = {
        @NamedStoredProcedureQuery(name = "CrawlView.crawls"
            , procedureName = "getcrawls",
            resultClasses = {CrawlView.class},
            parameters = {
                @StoredProcedureParameter(name = "statisticId", type = Long.class, mode = ParameterMode.IN)
            }),
        @NamedStoredProcedureQuery(name = "CrawlView.topCrawls"
            , procedureName = "gettopcrawls",
            resultClasses = {CrawlView.class},
            parameters = {
                @StoredProcedureParameter(name = "statisticId", type = Long.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "limitParam", type = Integer.class, mode = ParameterMode.IN)
            })
    }
)
public class CrawlView {

  @Id
  private String urls;

  private String keywords;

  private String hitsPerKeyword;

  private Integer totalHits;

}
