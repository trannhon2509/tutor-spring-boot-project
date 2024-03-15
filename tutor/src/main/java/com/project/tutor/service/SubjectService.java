package com.project.tutor.service;

import com.project.tutor.dto.SubjectDTO;
import com.project.tutor.many.dto.SubjectManyDTO;
import com.project.tutor.request.SubjectRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectService {
    public List<SubjectManyDTO> listSubject (int page , int record);
    public SubjectRequest addSubject (SubjectRequest request);
    public boolean deleteSubject (int subjectId);
    public boolean updateSubject (int subjectId , SubjectRequest request);
    public SubjectManyDTO getSubjectById (int subjectId);

    public List<SubjectManyDTO> findAllSubjectByName (String subjectName , int page , int record);
}
