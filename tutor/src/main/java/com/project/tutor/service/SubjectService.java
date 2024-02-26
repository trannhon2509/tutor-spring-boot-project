package com.project.tutor.service;

import com.project.tutor.many.dto.SubjectManyDTO;
import com.project.tutor.request.SubjectRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectService {
    public List<SubjectManyDTO> listSubject ();
    public SubjectRequest addSubject (SubjectRequest request);
    public boolean deleteSubject (int subjectId);
    public boolean updateSubject (int subjectId , SubjectRequest request);
    public SubjectManyDTO getSubjectById (int subjectId);
}
