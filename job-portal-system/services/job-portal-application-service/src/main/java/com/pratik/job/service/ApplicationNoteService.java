package com.pratik.job.service;

import com.pratik.job.dto.ApplicationNoteResponse;
import com.pratik.job.dto.ApplicationResponse;
import com.pratik.job.model.ApplicationNote;
import com.pratik.job.payload.AddApplicationNoteRequest;

import java.util.List;

public interface ApplicationNoteService {

    ApplicationNoteResponse addNote(
            Long applicationId, Long employerId, AddApplicationNoteRequest req) throws Exception;

    List<ApplicationNoteResponse> getNotesByApplication(
            Long applicationId, Long employerId
    );

    void deleteNote(Long applicationId, Long noteId, Long employerId) throws Exception;

}
