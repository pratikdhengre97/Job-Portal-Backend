package com.pratik.job.controller;

import com.pratik.job.response.ApiResponse;
import com.pratik.job.dto.ApplicationResponse;
import com.pratik.job.payload.CompanyApplicationFilterRequest;
import com.pratik.job.payload.CreateApplicationRequest;
import com.pratik.job.payload.UpdateApplicationStatusRequest;
import com.pratik.job.payload.WithdrawApplicationRequest;
import com.pratik.job.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestHeader("X-User-Id") Long candidateId,
                                                                 @RequestBody @Valid CreateApplicationRequest createApplicationRequest) throws Exception {
        return ResponseEntity.ok(
                applicationService.createApplication(candidateId, createApplicationRequest)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            @RequestHeader("X-User-Id") Long candidateId) {
        return ResponseEntity.ok(applicationService.getMyApplications(candidateId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationForJob(
            @PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId));
    }

    @GetMapping("/company")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForCompany(
            @RequestHeader("X-User-Id") Long userId,
            @ModelAttribute CompanyApplicationFilterRequest filter) {
        return ResponseEntity.ok(applicationService.getApplicationsForCompany(
                userId, filter));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid UpdateApplicationStatusRequest req) throws Exception {
        return ResponseEntity.ok(applicationService.updateStatus(id, employerId, req.getStatus())
        );
    }

    @PatchMapping("{id}/withdraw")
    public ResponseEntity<ApplicationResponse> withdraw(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody WithdrawApplicationRequest req) throws Exception {

        return ResponseEntity.ok(applicationService.withdraw(id, candidateId, req));
    }

    @PatchMapping("/{id}/star")
    public ResponseEntity<ApplicationResponse> toggleStar(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId) throws Exception {
        return ResponseEntity.ok(applicationService.toggleStar(id, employerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteApplication(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId) {
        return ResponseEntity.ok(new ApiResponse("Application deleted successfully", true));
    }

}
