package com.project.tutor.service;

import com.project.tutor.dto.TeachingDTO;
import com.project.tutor.request.TeachingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeachingService {
    public List<TeachingDTO> getAllListTeaching ();
    public boolean addTeaching (TeachingRequest request);

    public boolean deleteTeaching (int teachingId);

    public boolean updateTeaching (int teachingId , TeachingRequest request);

    public TeachingDTO getTeachingById (int teachingId);
}
