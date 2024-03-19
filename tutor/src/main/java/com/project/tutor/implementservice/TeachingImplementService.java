package com.project.tutor.implementservice;

import com.project.tutor.dto.TeachingDTO;
import com.project.tutor.mapstruct.MapStructGlobal;
import com.project.tutor.model.Teaching;
import com.project.tutor.model.Tutor;
import com.project.tutor.repository.TeachingRepository;
import com.project.tutor.request.TeachingRequest;
import com.project.tutor.service.TeachingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TeachingImplementService implements TeachingService {

    TeachingRepository teachingRepository;


    MapStructGlobal mapStructGlobal;

    @Override
    public List<TeachingDTO> getAllListTeaching() {
        List<Teaching> listTeaching = teachingRepository.findAll();
        List<TeachingDTO> listTeachingDTO = new ArrayList<>();
        for (Teaching teaching : listTeaching) {

            TeachingDTO teachingDTO = mapStructGlobal.toTeaching(teaching);

            listTeachingDTO.add(teachingDTO);
        }
        return listTeachingDTO;
    }

    @Override
    public boolean addTeaching(TeachingRequest request) {
        try {

           if(teachingRepository.existsByTeachingName(request.getTeachingName()))
               throw  new RuntimeException("Teaching already exist!");

            Teaching newTeaching = mapStructGlobal.toTeachingRequest(request);

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

                return TeachingDTO.builder()
                        .teachingId(teaching.getId())
                        .teachingName(teaching.getTeachingName())
                        .schedule(teaching.getSchedule())
                        .build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Cann't found  teaching detail with id  : " + teachingId);
        }
        return null;
    }
}
