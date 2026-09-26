package com.pratik.job.repository;

import com.pratik.job.model.JobSkill;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {

    List<JobSkill> findByActiveTrue();

    boolean existsByName(String name);
    boolean existsBySlug(String slug);
}
