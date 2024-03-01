package com.project.tutor.implementservice;

import com.project.tutor.dto.TutorDTO;
import com.project.tutor.many.dto.SubjectManyDTO;
import com.project.tutor.mapper.TutorSubjectUser;
import com.project.tutor.model.Subject;
import com.project.tutor.model.Tutor;
import com.project.tutor.repository.SubjectRepository;
import com.project.tutor.repository.TutorRepository;
import com.project.tutor.repository.TutorSubjectUserRepository;
import com.project.tutor.request.SubjectRequest;
import com.project.tutor.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubjectImplementService implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TutorSubjectUserRepository tutorSubjectUserRepository;

    @Autowired
    TutorRepository tutorRepository;


    @Override
    public List<SubjectManyDTO> listSubject() {
        List<Subject> listSubject = subjectRepository.findAll();
        List<SubjectManyDTO> subjectDTOList = new ArrayList<>();
        for (Subject subject : listSubject) {
            SubjectManyDTO subjectDTO = new SubjectManyDTO();
            subjectDTO.setId(subject.getId());
            subjectDTO.setSubjectName(subject.getSubjectName());
            subjectDTO.setDescription(subject.getDescription());
            subjectDTO.setTotalMoneyMonthTeaching(subject.getTotalMoneyMonthTeaching());
            subjectDTO.setNumberTeachOfWeek(subject.getNumberTeachOfWeek());
            subjectDTO.setOneHourTeaching(subject.getOneHourTeaching());

            List<TutorSubjectUser> listTutorSubjectUserList = subject.getListTutorSubjectUser();

            List<TutorDTO> tutorDTOList = new ArrayList<>();

            for (TutorSubjectUser tutorSubjectUser : listTutorSubjectUserList) {
                TutorDTO tutorDTO = new TutorDTO();
                tutorDTO.setId(tutorSubjectUser.getTutor().getId());
                tutorDTO.setCityTech(tutorSubjectUser.getTutor().getCityTeach());
                tutorDTO.setFullName(tutorSubjectUser.getTutor().getFullName());
                tutorDTO.setGender(tutorSubjectUser.getTutor().getGender());
                tutorDTO.setDateOfBirth(tutorSubjectUser.getTutor().getDateOfBirth());
                tutorDTO.setAddress(tutorSubjectUser.getTutor().getAddress());
                tutorDTO.setPhoneNumber(tutorSubjectUser.getTutor().getPhoneNumber());
                tutorDTO.setEmail(tutorSubjectUser.getTutor().getEmail());
                tutorDTO.setVoice(tutorSubjectUser.getTutor().getVoice());
                tutorDTO.setMajor(tutorSubjectUser.getTutor().getMajor());
                tutorDTO.setEcademicLevel(tutorSubjectUser.getTutor().getEcademicLevel());
                tutorDTO.setDescription(tutorSubjectUser.getTutor().getDescription());
                tutorDTO.setCitizenIdentificationCard(tutorSubjectUser.getTutor().getCitizenIdentificationCard());
                tutorDTO.setIssued(tutorSubjectUser.getTutor().getIssued());
                tutorDTO.setCitizenIdentificationCardFront(tutorSubjectUser.getTutor().getCitizenIdentificationCardFront());
                tutorDTO.setCitizenIdentificationCardFrontBackside(tutorSubjectUser.getTutor().getCitizenIdentificationCardFrontBackside());
                tutorDTO.setCardPhoto(tutorSubjectUser.getTutor().getCardPhoto());
                tutorDTO.setSchoolTeachOrStudent(tutorSubjectUser.getTutor().getSchoolTeachOrStudent());
                tutorDTO.setNumberTeachOfWeak(tutorSubjectUser.getTutor().getNumberTeachOfWeek());
                tutorDTO.setSalaryRequest(tutorSubjectUser.getTutor().getSalaryRequest());
                tutorDTO.setCreateAt(tutorSubjectUser.getTutor().getCreateAt());

                tutorDTOList.add(tutorDTO);
            }
            subjectDTO.setListTutorDTO(tutorDTOList);
            subjectDTOList.add(subjectDTO);
        }
        return subjectDTOList;
    }

    @Override
    public SubjectRequest addSubject(SubjectRequest request) {
        try {
            String subjectName = request.getSubjectName();
            String description = request.getDescription();
            double totalMoneyMonthTeaching = request.getTotalMoneyMonthTeaching();
            int numerTeachOfWeek = request.getNumberTeachOfWeek();
            double oneHourTeaching = request.getOneHourTeaching();
            List<Tutor> listTutors = request.getListTutors();

            Subject newSubject = new Subject();
            newSubject.setSubjectName(subjectName);
            newSubject.setDescription(description);
            newSubject.setTotalMoneyMonthTeaching(totalMoneyMonthTeaching);
            newSubject.setNumberTeachOfWeek(numerTeachOfWeek);
            newSubject.setOneHourTeaching(oneHourTeaching);

            Subject saveSubject = subjectRepository.save(newSubject);

            List<TutorSubjectUser> listTutorSubjectUser = new ArrayList<>();
            for (Tutor tutor : listTutors) {
                Tutor existingTutor = tutorRepository.findById(tutor.getId()).get();
                if (existingTutor != null) {
                    TutorSubjectUser tutorSubjectUser = new TutorSubjectUser();
                    tutorSubjectUser.setSubject(saveSubject);
                    tutorSubjectUser.setTutor(existingTutor);
                    listTutorSubjectUser.add(tutorSubjectUser);
                }
            }
            tutorSubjectUserRepository.saveAll(listTutorSubjectUser);
        } catch (Exception e) {
            throw new RuntimeException("Cann't add subject!!");
        }
        return null;
    }

    @Override
    public boolean deleteSubject(int subjectId) {
        Optional<Subject> checkSubjectExistOrNot = subjectRepository.findById(subjectId);
        if (checkSubjectExistOrNot.isPresent()) {
            Subject subject = checkSubjectExistOrNot.get();
            subjectRepository.delete(subject);
            return true;
        } else {
            throw new RuntimeException("Cannot delete subject with id : " + subjectId);
        }

    }

    @Override
    public boolean updateSubject(int subjectId, SubjectRequest request) {
        Optional<Subject> checkSubjectExistOrNot = subjectRepository.findById(subjectId);
        if (checkSubjectExistOrNot.isPresent()) {
            Subject subject = checkSubjectExistOrNot.get();
            subject.setSubjectName(request.getSubjectName());
            subject.setDescription(request.getDescription());
            subject.setTotalMoneyMonthTeaching(request.getTotalMoneyMonthTeaching());
            subject.setNumberTeachOfWeek(request.getNumberTeachOfWeek());
            subject.setOneHourTeaching(request.getOneHourTeaching());

            tutorSubjectUserRepository.deleteAllBySubject_Id(subjectId);

            subject = subjectRepository.save(subject);

            List<TutorSubjectUser> listTutorSubjectUser = new ArrayList<>();
            if (request.getListTutors() != null) {
                for (Tutor tutor : request.getListTutors()) {
                    Tutor existingTutor = tutorRepository.findById(tutor.getId()).get();
                    TutorSubjectUser tutorSubjectUser = new TutorSubjectUser();
                    tutorSubjectUser.setSubject(subject);
                    tutorSubjectUser.setTutor(existingTutor);
                    listTutorSubjectUser.add(tutorSubjectUser);
                }
                tutorSubjectUserRepository.saveAll(listTutorSubjectUser);
            }
            return true;
        }
        return false;
    }

    @Override
    public SubjectManyDTO getSubjectById(int subjectId) {
        Optional<Subject> checkSubjectExistOrNot = subjectRepository.findById(subjectId);
        if (checkSubjectExistOrNot.isPresent()) {
            Subject subject = checkSubjectExistOrNot.get();
            List<TutorSubjectUser> tutorSubjectUsers = tutorSubjectUserRepository.findBySubjectId(subjectId);

            SubjectManyDTO SubjectManyDTO = new SubjectManyDTO();
            SubjectManyDTO.setId(subject.getId());
            SubjectManyDTO.setSubjectName(subject.getSubjectName());
            SubjectManyDTO.setDescription(subject.getDescription());
            SubjectManyDTO.setTotalMoneyMonthTeaching(subject.getTotalMoneyMonthTeaching());
            SubjectManyDTO.setNumberTeachOfWeek(subject.getNumberTeachOfWeek());
            SubjectManyDTO.setOneHourTeaching(subject.getOneHourTeaching());

            List<TutorDTO> listTutorDTO = tutorSubjectUsers.stream().map(
                    TubjectDTO -> {
                        TutorDTO tutorDTO = new TutorDTO();
                        tutorDTO.setId(TubjectDTO.getTutor().getId());
                        tutorDTO.setCityTech(TubjectDTO.getTutor().getCityTeach());
                        tutorDTO.setFullName(TubjectDTO.getTutor().getFullName());
                        tutorDTO.setGender(TubjectDTO.getTutor().getGender());
                        tutorDTO.setDateOfBirth(TubjectDTO.getTutor().getDateOfBirth());
                        tutorDTO.setAddress(TubjectDTO.getTutor().getAddress());
                        tutorDTO.setPhoneNumber(TubjectDTO.getTutor().getPhoneNumber());
                        tutorDTO.setEmail(TubjectDTO.getTutor().getEmail());
                        tutorDTO.setVoice(TubjectDTO.getTutor().getVoice());
                        tutorDTO.setMajor(TubjectDTO.getTutor().getMajor());
                        tutorDTO.setEcademicLevel(TubjectDTO.getTutor().getEcademicLevel());
                        tutorDTO.setDescription(TubjectDTO.getTutor().getDescription());
                        tutorDTO.setCitizenIdentificationCard(TubjectDTO.getTutor().getCitizenIdentificationCard());
                        tutorDTO.setIssued(TubjectDTO.getTutor().getIssued());
                        tutorDTO.setCitizenIdentificationCardFront(TubjectDTO.getTutor().getCitizenIdentificationCardFront());
                        tutorDTO.setCitizenIdentificationCardFrontBackside(TubjectDTO.getTutor().getCitizenIdentificationCardFrontBackside());
                        tutorDTO.setCardPhoto(TubjectDTO.getTutor().getCardPhoto());
                        tutorDTO.setSchoolTeachOrStudent(TubjectDTO.getTutor().getSchoolTeachOrStudent());
                        tutorDTO.setNumberTeachOfWeak(TubjectDTO.getTutor().getNumberTeachOfWeek());
                        tutorDTO.setSalaryRequest(TubjectDTO.getTutor().getSalaryRequest());
                        tutorDTO.setCreateAt(TubjectDTO.getTutor().getCreateAt());
                        return tutorDTO;
                    }).collect(Collectors.toList());
            SubjectManyDTO.setListTutorDTO(listTutorDTO);
            return SubjectManyDTO;
        } else {
            throw new RuntimeException("Cannot found subject with id : " + subjectId);
        }
    }
}
