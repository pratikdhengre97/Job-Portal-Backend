package com.pratik.job.controller;

import com.pratik.job.payload.JobAlertSuggestRequest;
import com.pratik.job.payload.JobAlertSuggestResponse;
import com.pratik.job.payload.SearchEnhanceRequest;
import com.pratik.job.payload.SearchEnhanceResponse;
import com.pratik.job.service.SearchAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiSearchController {
    private final SearchAiService searchAiService;

    @PostMapping("/enhance")
    public ResponseEntity<SearchEnhanceResponse> enhanceSearch(
            @Valid @RequestBody SearchEnhanceRequest request
            ) throws Exception {
        return ResponseEntity.ok(searchAiService.enhanceSearch(request));
    }

    @PostMapping("/alert-suggestion")
    public ResponseEntity<JobAlertSuggestResponse> suggestAlertCriteria(
            @Valid @RequestBody JobAlertSuggestRequest request
    ) throws Exception {
        return ResponseEntity.ok(searchAiService.suggestJobAlertCriteria(request));
    }
}
