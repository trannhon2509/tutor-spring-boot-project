package com.project.tutor.repository;

import com.project.tutor.model.Teaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachingRepository extends JpaRepository<Teaching , Integer> {
    public boolean existsByTeachingName(String teachingName);
}
