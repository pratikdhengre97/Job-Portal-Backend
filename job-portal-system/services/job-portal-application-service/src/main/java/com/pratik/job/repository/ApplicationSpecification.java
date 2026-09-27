package com.pratik.job.repository;

import com.pratik.job.domain.AiShortlistStatus;
import com.pratik.job.domain.ApplicationStatus;
import com.pratik.job.model.Application;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ApplicationSpecification {

    public static Specification<Application> forCompanyWithFilters(
            Long companyId,
            Long jobId,
            ApplicationStatus status,
            boolean isStarred,
            AiShortlistStatus aiShortlistStatus,
            Integer minAiScore
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("companyId"), companyId));
            if(jobId != null) predicates.add(cb.equal(root.get("jobId"), jobId));
            if(status != null)  predicates.add(cb.equal(root.get("status"), status));
            if(isStarred)  predicates.add(cb.equal(root.get("isStarred"), isStarred));
            if(aiShortlistStatus != null) predicates.add(cb.equal(
                    root.get("aiShortlistStatus"), aiShortlistStatus));
            if(minAiScore != null) predicates.add(cb.greaterThanOrEqualTo(
                    root.get("aiScore"), minAiScore
            ));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
