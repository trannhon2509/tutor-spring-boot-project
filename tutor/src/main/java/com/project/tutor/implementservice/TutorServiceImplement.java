package com.project.tutor.implementservice;


import com.project.tutor.dto.SubjectDTO;
import com.project.tutor.many.dto.TutorManyDTO;
import com.project.tutor.mapper.TutorSubject;
import com.project.tutor.model.Subject;
import com.project.tutor.model.Tutor;
import com.project.tutor.repository.SubjectRepository;
import com.project.tutor.repository.TutorRepository;
import com.project.tutor.repository.TutorSubjectRepository;
import com.project.tutor.request.TutorRequest;
import com.project.tutor.service.FileService;
import com.project.tutor.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TutorServiceImplement implements TutorService {
    @Autowired
    TutorRepository tutorRepository;


    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TutorSubjectRepository tutorSubjectRepository;

    @Autowired
    FileService fileSevice;

    @Override
    public List<TutorManyDTO> getAllTutor() {
        List<Tutor> listTutors = tutorRepository.findAll();
        List<TutorManyDTO> listTutorDTO = new ArrayList<>();


        for (Tutor tutor : listTutors) {
            TutorManyDTO tutorManyDTO = new TutorManyDTO();
            tutorManyDTO.setId(tutor.getId());
            tutorManyDTO.setCityTech(tutor.getCityTech());
            tutorManyDTO.setFullName(tutor.getFullName());
            tutorManyDTO.setGender(tutor.getGender());
            tutorManyDTO.setDateOfBirth(tutor.getDateOfBirth());
            tutorManyDTO.setAddress(tutor.getAddress());
            tutorManyDTO.setPhoneNumber(tutor.getPhoneNumber());
            tutorManyDTO.setEmail(tutor.getEmail());
            tutorManyDTO.setVoice(tutor.getVoice());
            tutorManyDTO.setMajor(tutor.getMajor());
            tutorManyDTO.setEcademicLevel(tutor.getEcademicLevel());
            tutorManyDTO.setDescription(tutor.getDescription());
            tutorManyDTO.setCitizenIdentificationCard(tutor.getCitizenIdentificationCard());
            tutorManyDTO.setIssued(tutor.getIssued());
            tutorManyDTO.setCitizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront());
            tutorManyDTO.setCitizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside());
            tutorManyDTO.setCardPhoto(tutor.getCardPhoto());
            tutorManyDTO.setSchoolTeachOrStudent(tutor.getSchoolTeachOrStudent());
            tutorManyDTO.setNumberTeachOfWeak(tutor.getNumberTeachOfWeek());
            tutorManyDTO.setSalaryRequest(tutor.getSalaryRequest());
            tutorManyDTO.setCreateAt(tutor.getCreateAt());
            List<TutorSubject> listTutorSubject = tutor.getListTutorSubject();
            List<SubjectDTO> listSubjectDTOs = new ArrayList<>();

            for (TutorSubject tutorSubject : listTutorSubject) {
                SubjectDTO subjectDTO = new SubjectDTO();
                subjectDTO.setId(tutorSubject.getSubject().getId());
                subjectDTO.setSubjectName(tutorSubject.getSubject().getSubjectName());
                subjectDTO.setDescription(tutorSubject.getSubject().getDescription());
                subjectDTO.setTotalMoneyMonthTeaching(tutorSubject.getSubject().getTotalMoneyMonthTeaching());
                subjectDTO.setNumberTeachOfWeek(tutorSubject.getSubject().getNumberTeachOfWeek());
                subjectDTO.setOneHourTeaching(tutorSubject.getSubject().getOneHourTeaching());

                listSubjectDTOs.add(subjectDTO);
            }
            tutorManyDTO.setListSubjectDTO(listSubjectDTOs);
            listTutorDTO.add(tutorManyDTO);
        }

        return listTutorDTO;
    }


    @Override
    public TutorRequest addTutor(MultipartFile file, TutorRequest request) {
        try {
            boolean isSuccess = fileSevice.saveFile(file);

            if (isSuccess) {
                Tutor tutor = new Tutor();
                String cityTeach = request.getCityTech();
                String fullName = request.getFullName();
                String gender = request.getGender();
                String dateOfBirth = request.getDateOfBirth();
                String address = request.getAddress();
                String phoneNumber = request.getPhoneNumber();
                String email = request.getEmail();
                String voice = request.getVoice();
                String major = request.getMajor();
                String ecademicLevel = request.getEcademicLevel();
                String description = request.getDescription();
                String citizenIdentificationCard = file.getOriginalFilename();
                String issued = request.getIssued();
                String citizenIdentificationCardFront = file.getOriginalFilename();
                String citizenIdentificationCardFrontBackside = file.getOriginalFilename();
                String cardPhoto = file.getOriginalFilename();
                String schoolOfTeach = request.getSchoolTeachOrStudent();
                int numberTeachOfWeek = request.getNumberTeachOfWeak();
                double salaryRequest = request.getSalaryRequest();
                LocalDateTime createAt = request.getCreateAt();
                List<Subject> listSubject = request.getListSubjects();

                Tutor newTutor = new Tutor();
                newTutor.setCityTech(cityTeach);
                newTutor.setFullName(fullName);
                newTutor.setGender(gender);
                newTutor.setDateOfBirth(dateOfBirth);
                newTutor.setAddress(address);
                newTutor.setPhoneNumber(phoneNumber);
                newTutor.setEmail(email);
                newTutor.setVoice(voice);
                newTutor.setMajor(major);
                newTutor.setEcademicLevel(ecademicLevel);
                newTutor.setDescription(description);
                newTutor.setCitizenIdentificationCard(citizenIdentificationCard);
                newTutor.setIssued(issued);
                newTutor.setCitizenIdentificationCardFront(citizenIdentificationCardFront);
                newTutor.setCitizenIdentificationCardFrontBackside(citizenIdentificationCardFrontBackside);
                newTutor.setCardPhoto(cardPhoto);
                newTutor.setSchoolTeachOrStudent(schoolOfTeach);
                newTutor.setSalaryRequest(salaryRequest);

                Tutor saveTutor = tutorRepository.save(newTutor);

                List<TutorSubject> listTutorSubject = new ArrayList<>();
                for (Subject subject : listSubject) {
                    Subject existingSubject = subjectRepository.findById(subject.getId()).get();
                    TutorSubject tutorSubject = new TutorSubject();
                    tutorSubject.setTutor(saveTutor);
                    tutorSubject.setSubject(existingSubject);
                    listTutorSubject.add(tutorSubject);
                }
                tutorSubjectRepository.saveAll(listTutorSubject);
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot add tutor !");
        }
        return null;
    }

    @Override
    public boolean deleteTutorById(int tutorId) {
        Optional<Tutor> checkTutorExistOrNot = tutorRepository.findById(tutorId);
        if (checkTutorExistOrNot.isPresent()) {
            Tutor tutor = checkTutorExistOrNot.get();
            tutorRepository.delete(tutor);
            return true;
        } else {
            throw new RuntimeException("Cannot find tutor with id : " + tutorId);
        }
    }

    @Override
    public boolean updateTutor(int tutorId, TutorRequest request) {


        return false;
    }

    @Override
    public TutorManyDTO getTutorById(int tutorId) {
        Optional<Tutor> checkTutorExistOrNot = tutorRepository.findById(tutorId);
        if (checkTutorExistOrNot.isPresent()) {
            Tutor tutor = checkTutorExistOrNot.get();
            List<TutorSubject> listTutorSubject = tutorSubjectRepository.findByTutorId(tutorId);

            TutorManyDTO tutorManyDTO = new TutorManyDTO();
            tutorManyDTO.setId(tutor.getId());
            tutorManyDTO.setCityTech(tutor.getCityTech());
            tutorManyDTO.setFullName(tutor.getFullName());
            tutorManyDTO.setGender(tutor.getGender());
            tutorManyDTO.setDateOfBirth(tutor.getDateOfBirth());
            tutorManyDTO.setAddress(tutor.getAddress());
            tutorManyDTO.setPhoneNumber(tutor.getPhoneNumber());
            tutorManyDTO.setEmail(tutor.getEmail());
            tutorManyDTO.setVoice(tutor.getVoice());
            tutorManyDTO.setMajor(tutor.getMajor());
            tutorManyDTO.setEcademicLevel(tutor.getEcademicLevel());
            tutorManyDTO.setDescription(tutor.getDescription());
            tutorManyDTO.setCitizenIdentificationCard(tutor.getCitizenIdentificationCard());
            tutorManyDTO.setIssued(tutor.getIssued());
            tutorManyDTO.setCitizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront());
            tutorManyDTO.setCitizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside());
            tutorManyDTO.setCardPhoto(tutor.getCardPhoto());
            tutorManyDTO.setSchoolTeachOrStudent(tutor.getSchoolTeachOrStudent());
            tutorManyDTO.setNumberTeachOfWeak(tutor.getNumberTeachOfWeek());
            tutorManyDTO.setSalaryRequest(tutor.getSalaryRequest());
            tutorManyDTO.setCreateAt(tutor.getCreateAt());

            List<SubjectDTO> listSubjectDT0 = listTutorSubject.stream().map(
                    TubjectDTO -> {
                        SubjectDTO subjectDTO = new SubjectDTO();
                        subjectDTO.setId(TubjectDTO.getId());
                        subjectDTO.setSubjectName(TubjectDTO.getSubject().getSubjectName());
                        subjectDTO.setDescription(TubjectDTO.getSubject().getDescription());
                        subjectDTO.setTotalMoneyMonthTeaching(TubjectDTO.getSubject().getTotalMoneyMonthTeaching());
                        subjectDTO.setNumberTeachOfWeek(TubjectDTO.getSubject().getNumberTeachOfWeek());
                        subjectDTO.setOneHourTeaching(TubjectDTO.getSubject().getOneHourTeaching());
                        return subjectDTO;
                    }).collect(Collectors.toList());
            tutorManyDTO.setListSubjectDTO(listSubjectDT0);
            return tutorManyDTO;
        }
        return null;
    }
}
