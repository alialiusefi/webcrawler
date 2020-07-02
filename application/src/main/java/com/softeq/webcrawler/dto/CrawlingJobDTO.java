package com.softeq.webcrawler.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingJobDTO {

  //@NotEmpty
  private List<String> keywords;

  //@NotEmpty
  private String seedUrl;

}
