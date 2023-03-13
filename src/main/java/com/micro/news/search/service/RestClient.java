package com.micro.news.search.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RestClient {

    private static RestTemplate restTemplate = new RestTemplate();

    public <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables){
        return restTemplate.getForEntity(url, responseType, uriVariables).getBody();
    }
}
