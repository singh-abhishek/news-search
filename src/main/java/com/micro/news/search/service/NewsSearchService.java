package com.micro.news.search.service;

import com.micro.news.search.dto.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class NewsSearchService {

    private final NYTArticleSearchService nytArticleSearchService;
    private final GuardianArticleSearchService guardianArticleSearchService;

    public List<ArticleResponse> findArticles(String query, Pageable pageRequest) {
        Integer nytArticlesOffset = (pageRequest.getPageSize() / 2) * pageRequest.getPageNumber();

        List<ArticleResponse> nytArticles = nytArticleSearchService.findArticles(query, nytArticlesOffset, pageRequest.getPageSize() / 2);

        List<ArticleResponse> guardianArticles = guardianArticleSearchService.findArticles(query, pageRequest.getPageNumber() + 1, pageRequest.getPageSize() / 2);
        return Stream.concat(nytArticles.stream(), guardianArticles.stream()).collect(Collectors.toList());
    }

    public long getTotalArticles(String query) {
        return nytArticleSearchService.countArticles(query) + guardianArticleSearchService.countArticles(query);
    }
}
