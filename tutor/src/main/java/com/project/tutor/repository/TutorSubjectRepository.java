package com.project.tutor.repository;

import com.project.tutor.mapper.TutorSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorSubjectRepository  extends JpaRepository<TutorSubject,Integer> {
    public List<TutorSubject> findBySubjectId(int subjectId);
    public List<TutorSubject> findByTutorId(int tutorId);
    public void deleteAllBySubject_Id(int subjectId);

}
