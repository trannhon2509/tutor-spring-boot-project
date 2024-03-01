package com.project.tutor.repository;

import com.project.tutor.mapper.TutorSubjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorSubjectUserRepository extends JpaRepository<TutorSubjectUser,Integer> {
    public List<TutorSubjectUser> findBySubjectId(int subjectId);
    public List<TutorSubjectUser> findByTutorId(int tutorId);
    public void deleteAllBySubject_Id(int subjectId);

    public void deleteAllByTutor_Id(int tutorId);


}
