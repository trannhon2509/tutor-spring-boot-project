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
import java.util.Optional;

@Service
public class TeachingImplementService implements TeachingService {

    @Autowired
    TeachingRepository teachingRepository;

    @Override
    public List<TeachingDTO> getAllListTeaching() {
        List<Teaching> listTeaching = teachingRepository.findAll();
        List<TeachingDTO> listTeachingDTO = new ArrayList<>();

        for (Teaching teaching : listTeaching) {
            TeachingDTO teachingDTO = new TeachingDTO();
            teachingDTO.setTeachingId(teaching.getId());
            teachingDTO.setTeachingName(teaching.getTeachingName());
            teachingDTO.setSchedule(teaching.getSchedule());

            listTeachingDTO.add(teachingDTO);
        }
        return listTeachingDTO;
    }

    @Override
    public boolean addTeaching(TeachingRequest request) {
        try {
            Tutor tutor = new Tutor();

            String teachingName = request.getTeachingName();
            int schedule = request.getSchedule();

            tutor.setId(request.getTutorId());

            Teaching checkTeachingNameExist = teachingRepository.findTeachingByTeachingName(teachingName);

            if (checkTeachingNameExist != null) {
                throw new RuntimeException("Teaching name already exist!");
            }

            Teaching newTeaching = new Teaching();

            newTeaching.setTeachingName(teachingName);
            newTeaching.setSchedule(schedule);
            newTeaching.setTutor(tutor);

            teachingRepository.save(newTeaching);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Add teaching fail!");
        }
    }

    @Override
    public boolean deleteTeaching(int teachingId) {
        Optional<Teaching> checkTeachingExistOrnot = teachingRepository.findById(teachingId);
        try {
            if (checkTeachingExistOrnot.isPresent()) {
                Teaching teaching = checkTeachingExistOrnot.get();
                teachingRepository.delete(teaching);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Cann't found teaching with id  : " + teachingId);
        }
        return false;
    }

    @Override
    public boolean updateTeaching(int teachingId, TeachingRequest request) {
        Optional<Teaching> checkTeachingExistOrnot = teachingRepository.findById(teachingId);
        try {
            if (checkTeachingExistOrnot.isPresent()) {
                Teaching teaching = checkTeachingExistOrnot.get();

                teaching.setTeachingName(request.getTeachingName());
                teaching.setSchedule(request.getSchedule());

                teachingRepository.save(teaching);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Cann't found and update teaching with id  : " + teachingId);
        }
        return false;
    }

    @Override
    public TeachingDTO getTeachingById(int teachingId) {
        Optional<Teaching> checkTeachingExistOrnot = teachingRepository.findById(teachingId);
        try {
            if (checkTeachingExistOrnot.isPresent()) {
                Teaching teaching = checkTeachingExistOrnot.get();

                TeachingDTO teachingDTO = new TeachingDTO();
                teachingDTO.setTeachingName(teaching.getTeachingName());
                teaching.setSchedule(teaching.getSchedule());
                return teachingDTO;
            }
        } catch (Exception e) {
            throw new RuntimeException("Cann't found  teaching detail with id  : " + teachingId);
        }
        return null;
    }
}
