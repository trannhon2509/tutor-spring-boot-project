package com.project.tutor.repository;

import com.project.tutor.model.Tutor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<Tutor , Integer> {
 public List<Tutor> findByApprovedFalse();
 public List<Tutor> findByFullNameContaining(String title , Pageable pageable);
}
