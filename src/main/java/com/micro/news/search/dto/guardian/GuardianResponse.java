package com.micro.news.search.dto.guardian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuardianResponse {
    private List<Result> results;
    private Long total;
}
