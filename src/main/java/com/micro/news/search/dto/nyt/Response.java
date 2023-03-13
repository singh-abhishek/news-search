package com.micro.news.search.dto.nyt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private List<Doc> docs;
    private Meta meta;
}
