package com.micro.news.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse extends RepresentationModel<ArticleResponse> {
    private String headline;
    private String webUrl;
    private String source;
}
