package com.project.tutor.repository;

import com.project.tutor.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    public List<Subject> findBySubjectNameContaining(String subjectName , Pageable pageable);
}
