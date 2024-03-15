package com.project.tutor.implementservice;

import com.project.tutor.access.PagingSearchAndSorting;
import com.project.tutor.dto.SubjectDTO;
import com.project.tutor.dto.TutorDTO;
import com.project.tutor.many.dto.SubjectManyDTO;
import com.project.tutor.mapper.TutorSubject;
import com.project.tutor.model.Subject;
import com.project.tutor.model.Tutor;
import com.project.tutor.repository.SubjectRepository;
import com.project.tutor.repository.TutorRepository;
import com.project.tutor.repository.TutorSubjecRepository;
import com.project.tutor.request.SubjectRequest;
import com.project.tutor.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubjectImplementService implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TutorSubjecRepository tutorSubjecRepository;

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    PagingSearchAndSorting pagingSearchAndSorting;


    @Override
    public List<SubjectManyDTO> listSubject(int page, int record) {

        List<Subject> listSubject = subjectRepository.findAll
                (pagingSearchAndSorting.pageablePageSizeAndRecordOrSearchOrSort(page, record)).get().toList();

        List<SubjectManyDTO> subjectDTOList = new ArrayList<>();
        for (Subject subject : listSubject) {
            SubjectManyDTO subjectDTO = SubjectManyDTO.builder()
                    .id(subject.getId())
                    .subjectName(subject.getSubjectName())
                    .description(subject.getDescription())
                    .totalMoneyMonthTeaching(subject.getTotalMoneyMonthTeaching())
                    .numberTeachOfWeek(subject.getNumberTeachOfWeek())
                    .oneHourTeaching(subject.getOneHourTeaching())
                    .build();


            List<TutorSubject> listTutorSubjectList = subject.getListTutorSubject();

            List<TutorDTO> tutorDTOList = new ArrayList<>();

            for (TutorSubject tutorSubject : listTutorSubjectList) {
                TutorDTO tutorDTO = TutorDTO.builder()
                        .id(tutorSubject.getTutor().getId())
                        .cityTech(tutorSubject.getTutor().getCityTeach())
                        .fullName(tutorSubject.getTutor().getFullName())
                        .gender(tutorSubject.getTutor().getGender())
                        .dateOfBirth(tutorSubject.getTutor().getDateOfBirth())
                        .address(tutorSubject.getTutor().getAddress())
                        .phoneNumber(tutorSubject.getTutor().getPhoneNumber())
                        .email(tutorSubject.getTutor().getEmail())
                        .voice(tutorSubject.getTutor().getVoice())
                        .major(tutorSubject.getTutor().getMajor())
                        .ecademicLevel(tutorSubject.getTutor().getEcademicLevel())
                        .description(tutorSubject.getTutor().getDescription())
                        .citizenIdentificationCard(tutorSubject.getTutor().getCitizenIdentificationCard())
                        .issued(tutorSubject.getTutor().getIssued())
                        .citizenIdentificationCardFront(tutorSubject.getTutor().getCitizenIdentificationCardFront())
                        .citizenIdentificationCardFrontBackside(tutorSubject.getTutor().getCitizenIdentificationCardFrontBackside())
                        .cardPhoto(tutorSubject.getTutor().getCardPhoto())
                        .schoolTeachOrStudent(tutorSubject.getTutor().getSchoolTeachOrStudent())
                        .numberTeachOfWeak(tutorSubject.getTutor().getNumberTeachOfWeek())
                        .salaryRequest(tutorSubject.getTutor().getSalaryRequest())
                        .createAt(tutorSubject.getTutor().getCreateAt())
                        .approved(tutorSubject.getTutor().isApproved())
                        .build();
                tutorDTOList.add(tutorDTO);
            }
            subjectDTO.setListTutorDTO(tutorDTOList);
            subjectDTOList.add(subjectDTO);
        }
        return subjectDTOList;
    }

    @Override
    public List<SubjectManyDTO> findAllSubjectByName(String subjectName, int page, int record) {

        List<Subject> listSubject = subjectRepository.findBySubjectNameContaining(subjectName, pagingSearchAndSorting.pageablePageSizeAndRecordOrSearchOrSort(page, record));

        List<SubjectManyDTO> subjectDTOList = new ArrayList<>();

        for (Subject subject : listSubject) {
            SubjectManyDTO subjectDTO = SubjectManyDTO.builder()
                    .id(subject.getId())
                    .subjectName(subject.getSubjectName())
                    .description(subject.getDescription())
                    .totalMoneyMonthTeaching(subject.getTotalMoneyMonthTeaching())
                    .numberTeachOfWeek(subject.getNumberTeachOfWeek())
                    .oneHourTeaching(subject.getOneHourTeaching())
                    .build();

            List<TutorSubject> listTutorSubjectList = subject.getListTutorSubject();

            List<TutorDTO> tutorDTOList = new ArrayList<>();

            for (TutorSubject tutorSubject : listTutorSubjectList) {
                TutorDTO tutorDTO = TutorDTO.builder()
                        .id(tutorSubject.getTutor().getId())
                        .cityTech(tutorSubject.getTutor().getCityTeach())
                        .fullName(tutorSubject.getTutor().getFullName())
                        .gender(tutorSubject.getTutor().getGender())
                        .dateOfBirth(tutorSubject.getTutor().getDateOfBirth())
                        .address(tutorSubject.getTutor().getAddress())
                        .phoneNumber(tutorSubject.getTutor().getPhoneNumber())
                        .email(tutorSubject.getTutor().getEmail())
                        .voice(tutorSubject.getTutor().getVoice())
                        .major(tutorSubject.getTutor().getMajor())
                        .ecademicLevel(tutorSubject.getTutor().getEcademicLevel())
                        .description(tutorSubject.getTutor().getDescription())
                        .citizenIdentificationCard(tutorSubject.getTutor().getCitizenIdentificationCard())
                        .issued(tutorSubject.getTutor().getIssued())
                        .citizenIdentificationCardFront(tutorSubject.getTutor().getCitizenIdentificationCardFront())
                        .citizenIdentificationCardFrontBackside(tutorSubject.getTutor().getCitizenIdentificationCardFrontBackside())
                        .cardPhoto(tutorSubject.getTutor().getCardPhoto())
                        .schoolTeachOrStudent(tutorSubject.getTutor().getSchoolTeachOrStudent())
                        .numberTeachOfWeak(tutorSubject.getTutor().getNumberTeachOfWeek())
                        .salaryRequest(tutorSubject.getTutor().getSalaryRequest())
                        .createAt(tutorSubject.getTutor().getCreateAt())
                        .approved(tutorSubject.getTutor().isApproved())
                        .build();

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

            List<TutorSubject> listTutorSubject = new ArrayList<>();
            for (Tutor tutor : listTutors) {
                Tutor existingTutor = tutorRepository.findById(tutor.getId()).get();
                if (existingTutor != null) {
                    TutorSubject tutorSubject = new TutorSubject();
                    tutorSubject.setSubject(saveSubject);
                    tutorSubject.setTutor(existingTutor);
                    listTutorSubject.add(tutorSubject);
                }
            }
            tutorSubjecRepository.saveAll(listTutorSubject);
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

            tutorSubjecRepository.deleteAllBySubject_Id(subjectId);

            subject = subjectRepository.save(subject);

            List<TutorSubject> listTutorSubject = new ArrayList<>();
            if (request.getListTutors() != null) {
                for (Tutor tutor : request.getListTutors()) {
                    Tutor existingTutor = tutorRepository.findById(tutor.getId()).get();
                    TutorSubject tutorSubject = new TutorSubject();
                    tutorSubject.setSubject(subject);
                    tutorSubject.setTutor(existingTutor);
                    listTutorSubject.add(tutorSubject);
                }
                tutorSubjecRepository.saveAll(listTutorSubject);
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
            List<TutorSubject> tutorSubjects = tutorSubjecRepository.findBySubjectId(subjectId);

            SubjectManyDTO subjectManyDTO = SubjectManyDTO.builder()
                    .id(subject.getId())
                    .subjectName(subject.getSubjectName())
                    .description(subject.getDescription())
                    .totalMoneyMonthTeaching(subject.getTotalMoneyMonthTeaching())
                    .numberTeachOfWeek(subject.getNumberTeachOfWeek())
                    .oneHourTeaching(subject.getOneHourTeaching())
                    .build();

            List<TutorDTO> listTutorDTO = tutorSubjects.stream().map(
                    TubjectDTO -> {
                        return TutorDTO.builder()
                                .id(TubjectDTO.getTutor().getId())
                                .cityTech(TubjectDTO.getTutor().getCityTeach())
                                .fullName(TubjectDTO.getTutor().getFullName())
                                .gender(TubjectDTO.getTutor().getGender())
                                .dateOfBirth(TubjectDTO.getTutor().getDateOfBirth())
                                .address(TubjectDTO.getTutor().getAddress())
                                .phoneNumber(TubjectDTO.getTutor().getPhoneNumber())
                                .email(TubjectDTO.getTutor().getEmail())
                                .voice(TubjectDTO.getTutor().getVoice())
                                .major(TubjectDTO.getTutor().getMajor())
                                .ecademicLevel(TubjectDTO.getTutor().getEcademicLevel())
                                .description(TubjectDTO.getTutor().getDescription())
                                .citizenIdentificationCard(TubjectDTO.getTutor().getCitizenIdentificationCard())
                                .issued(TubjectDTO.getTutor().getIssued())
                                .citizenIdentificationCardFront(TubjectDTO.getTutor().getCitizenIdentificationCardFront())
                                .citizenIdentificationCardFrontBackside(TubjectDTO.getTutor().getCitizenIdentificationCardFrontBackside())
                                .cardPhoto(TubjectDTO.getTutor().getCardPhoto())
                                .schoolTeachOrStudent(TubjectDTO.getTutor().getSchoolTeachOrStudent())
                                .numberTeachOfWeak(TubjectDTO.getTutor().getNumberTeachOfWeek())
                                .salaryRequest(TubjectDTO.getTutor().getSalaryRequest())
                                .createAt(TubjectDTO.getTutor().getCreateAt())
                                .approved(TubjectDTO.getTutor().isApproved())
                                .build();
                    }).collect(Collectors.toList());
            subjectManyDTO.setListTutorDTO(listTutorDTO);
            return subjectManyDTO;
        } else {
            throw new RuntimeException("Cannot found subject with id : " + subjectId);
        }
    }
}
