package com.project.tutor.implementservice;

import com.project.tutor.dto.TeachingDTO;
import com.project.tutor.model.Teaching;
import com.project.tutor.model.Tutor;
import com.project.tutor.repository.TeachingRepository;
import com.project.tutor.request.TeachingRequest;
import com.project.tutor.service.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachingImplementService implements TeachingService {

    @Autowired
    TeachingRepository teachingRepository;
    @Override
    public List<TeachingDTO> getAllListTeaching() {
        List<Teaching> listTeaching = teachingRepository.findAll();
        List<TeachingDTO> listTeachingDTO = new ArrayList<>();

        for (Teaching teaching : listTeaching){
            TeachingDTO teachingDTO = new TeachingDTO();
            teachingDTO.setTeachingId(teaching.getId());
            teachingDTO.setTeachingName(teaching.getTeachingName());
            teachingDTO.setSchedule(teaching.getSchedule());

            listTeachingDTO.add(teachingDTO);
        }
        return listTeachingDTO;
    }

    @Override
    public TeachingRequest addTeaching(TeachingRequest request) {
        return null;
    }

    @Override
    public boolean deleteTeaching(int teachingId) {
        return false;
    }

    @Override
    public boolean updateTeaching(int teachingId, TeachingRequest request) {
        return false;
    }

    @Override
    public TeachingDTO getTeachingById(int teachingId) {
        return null;
    }
}
