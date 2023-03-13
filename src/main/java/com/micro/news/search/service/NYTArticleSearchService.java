package com.micro.news.search.service;

import com.micro.news.search.dto.ArticleResponse;
import com.micro.news.search.dto.nyt.Doc;
import com.micro.news.search.dto.nyt.NYTArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NYTArticleSearchService {

    @Value("{nyt.api.key:}")
    private String nytApiKey;

    public static final String NYT_ARTICLE_SEARCH_API = "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=5hA2P1lnV97d3SsIzKoruHaQ5n1l5uqA&q=%s&page=%s&sort=newest";
    public static final int NYT_ARTICLES_API_DEFAULT_PAGE_SIZE = 10;
    public static final String SOURCE_NEW_YORK_TIMES = "New York Times";
    private final RestClient restClient;

    public List<ArticleResponse> findArticles(String query, Integer offset, Integer limit) {
        List<Doc> nytArticles = new ArrayList<>();
        int pageNumber = offset / NYT_ARTICLES_API_DEFAULT_PAGE_SIZE;
        NYTArticleResponse response = restClient.get(String.format(NYT_ARTICLE_SEARCH_API, query, pageNumber), NYTArticleResponse.class, Map.of());
        int firstPageArticles = offset % NYT_ARTICLES_API_DEFAULT_PAGE_SIZE;
        nytArticles.addAll(response.getResponse().getDocs().stream().skip(firstPageArticles).collect(Collectors.toList()));
        for(int i = 1; i < (limit - firstPageArticles) / NYT_ARTICLES_API_DEFAULT_PAGE_SIZE; i++){
            restClient.get(String.format(NYT_ARTICLE_SEARCH_API, query, pageNumber + i), NYTArticleResponse.class, Map.of());
            nytArticles.addAll(response.getResponse().getDocs());
        }

        if(nytArticles.size() < limit){
            restClient.get(String.format(NYT_ARTICLE_SEARCH_API, query, (offset + limit) / 10), NYTArticleResponse.class, Map.of());
            nytArticles.addAll(response.getResponse().getDocs().stream().limit(limit - nytArticles.size()).collect(Collectors.toList()));
        }
        return nytArticles.stream().map(d -> new ArticleResponse(d.getHeadline().getMain(), d.getWebUrl(), SOURCE_NEW_YORK_TIMES)).collect(Collectors.toList());
    }

    public long countArticles(String query) {
        return restClient.get(String.format(NYT_ARTICLE_SEARCH_API, query, 0), NYTArticleResponse.class, Map.of())
                .getResponse().getMeta().getHits();
    }
}
