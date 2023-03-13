package com.micro.news.search.service;

import com.micro.news.search.dto.ArticleResponse;
import com.micro.news.search.dto.guardian.GuardianArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuardianArticleSearchService {

    private static final String SEARCH_API = "https://content.guardianapis.com/search?q=%s&page=%s&api-key=test&order-by=newest&page-size=%s";
    public static final String SOURCE_THE_GUARDIAN = "The Guardian";

    private final RestClient restClient;

    public List<ArticleResponse> findArticles(String query, int pageNumber, int pageSize) {
        GuardianArticleResponse response = restClient.get(String.format(SEARCH_API, query, pageNumber, pageSize), GuardianArticleResponse.class, Map.of());
        return response.getResponse().getResults().stream().map(r -> new ArticleResponse(r.getWebTitle(), r.getWebUrl(), SOURCE_THE_GUARDIAN)).collect(Collectors.toList());
    }

    public long countArticles(String query) {
        return restClient.get(String.format(SEARCH_API, query, 1, 1), GuardianArticleResponse.class, Map.of()).getResponse().getTotal();
    }
}
