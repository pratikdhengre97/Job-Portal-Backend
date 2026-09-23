package com.pratik.job.job_portal_user_service.repository;

import com.pratik.job.job_portal_user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);



//    User findById(Long id);
}
