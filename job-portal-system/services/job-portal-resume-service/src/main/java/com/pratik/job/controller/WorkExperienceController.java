package com.pratik.job.controller;

import com.pratik.job.response.ApiResponse;
import com.pratik.job.response.WorkExperienceResponse;
import com.pratik.job.payload.AddWorkExperience;
import com.pratik.job.service.WorkExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/{resumeId}/work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @PostMapping
    public ResponseEntity<WorkExperienceResponse> addWorkExperience(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddWorkExperience req
            ) throws Exception {
        return ResponseEntity.ok(workExperienceService.addWorkExperience(resumeId, candidateId, req));
    }

    @GetMapping
    public ResponseEntity<List<WorkExperienceResponse>> getWorkExperiences(
            @PathVariable Long resumeId)  {
        return ResponseEntity.ok(workExperienceService.getWorkExperiences(resumeId));
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<WorkExperienceResponse> updateWorkExperience(
            @PathVariable Long resumeId,
            @PathVariable Long experienceId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddWorkExperience req) throws Exception {
        return ResponseEntity.ok(
                workExperienceService.updateWorkExperience(resumeId, candidateId, experienceId, req));
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<ApiResponse> deleteWorkExperience(
            @PathVariable Long resumeId,
            @PathVariable Long experienceId,
            @RequestHeader("X-User-Id") Long candidateId) throws Exception {
                workExperienceService.deleteWorkExperience(resumeId,experienceId, candidateId);

                return ResponseEntity.ok(new ApiResponse("Work Experience deleted successfully", true));
    }

}
