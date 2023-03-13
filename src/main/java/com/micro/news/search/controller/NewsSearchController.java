package com.micro.news.search.controller;

import com.micro.news.search.dto.ArticleResponse;
import com.micro.news.search.service.NewsSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("v1/newssearch")
@RequiredArgsConstructor
public class NewsSearchController {

    private final NewsSearchService newsSearchService;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ArticleResponse>>> searchTopic(@RequestParam String query, Pageable pageRequest) {
        Link link = linkTo(methodOn(this.getClass()).searchTopic(query, pageRequest)).withSelfRel();
        Page page = new PageImpl(newsSearchService.findArticles(query, pageRequest), pageRequest, newsSearchService.getTotalArticles(query));
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(page, link));
    }
}
