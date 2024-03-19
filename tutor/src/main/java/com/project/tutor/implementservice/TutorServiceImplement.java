package com.project.tutor.implementservice;


import com.project.tutor.access.PagingSearchAndSorting;
import com.project.tutor.dto.FeedBackDTO;
import com.project.tutor.dto.SubjectDTO;
import com.project.tutor.dto.TeachingDTO;
import com.project.tutor.many.dto.TutorManyDTO;
import com.project.tutor.mapper.TutorSubject;
import com.project.tutor.model.FeedBack;
import com.project.tutor.model.Subject;
import com.project.tutor.model.Teaching;
import com.project.tutor.model.Tutor;
import com.project.tutor.repository.SubjectRepository;
import com.project.tutor.repository.TutorRepository;
import com.project.tutor.repository.TutorSubjecRepository;
import com.project.tutor.repository.UserRepository;
import com.project.tutor.request.TutorRequest;
import com.project.tutor.service.FileService;
import com.project.tutor.service.TutorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TutorServiceImplement implements TutorService {
     
    TutorRepository tutorRepository;
    PagingSearchAndSorting pagingSearchAndSorting;

    SubjectRepository subjectRepository;
    TutorSubjecRepository tutorSubjecRepository;
    FileService fileService;

    UserRepository userRepository;

    @Override
    public List<TutorManyDTO> getListTutorApprovedFalse() {
        List<Tutor> listTutors = tutorRepository.findByApprovedFalse();
        List<TutorManyDTO> listTutorDTO = new ArrayList<>();

        for (Tutor tutor : listTutors) {
            TutorManyDTO tutorManyDTO = TutorManyDTO.builder()
                    .id(tutor.getId())
                    .cityTech(tutor.getCityTeach())
                    .fullName(tutor.getFullName())
                    .gender(tutor.getGender())
                    .dateOfBirth(tutor.getDateOfBirth())
                    .address(tutor.getAddress())
                    .phoneNumber(tutor.getPhoneNumber())
                    .email(tutor.getEmail())
                    .voice(tutor.getVoice())
                    .major(tutor.getMajor())
                    .ecademicLevel(tutor.getEcademicLevel())
                    .description(tutor.getDescription())
                    .citizenIdentificationCard(tutor.getCitizenIdentificationCard())
                    .issued(tutor.getIssued())
                    .citizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront())
                    .citizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside())
                    .cardPhoto(tutor.getCardPhoto())
                    .schoolTeachOrStudent(tutor.getSchoolTeachOrStudent())
                    .numberTeachOfWeak(tutor.getNumberTeachOfWeek())
                    .salaryRequest(tutor.getSalaryRequest())
                    .createAt(tutor.getCreateAt())
                    .approved(tutor.isApproved())
                    .build();

            listTutorDTO.add(tutorManyDTO);
        }
        return listTutorDTO;
    }

    @Override
    public boolean approveTutor(int tutorId) {

        Optional<Tutor> checkTutorExistOrNot = tutorRepository.findById(tutorId);
        if (checkTutorExistOrNot.isPresent()) {
            Tutor tutor = checkTutorExistOrNot.get();

            if (tutor.isApproved()) {
                throw new BadCredentialsException("Tutor is approved");
            } else {
                tutor.setApproved(true);
                tutorRepository.save(tutor);
            }
            return true;
        }

        return false;
    }

    @Override
    public List<TutorManyDTO> getAllTutor(int page, int record) {
        List<Tutor> listTutors = tutorRepository.findAll(pagingSearchAndSorting.pageablePageSizeAndRecordOrSearchOrSort(page, record)).getContent();
        List<TutorManyDTO> listTutorDTO = new ArrayList<>();

        for (Tutor tutor : listTutors) {
            TutorManyDTO tutorManyDTO = TutorManyDTO.builder()
                    .id(tutor.getId())
                    .cityTech(tutor.getCityTeach())
                    .fullName(tutor.getFullName())
                    .gender(tutor.getGender())
                    .dateOfBirth(tutor.getDateOfBirth())
                    .address(tutor.getAddress())
                    .phoneNumber(tutor.getPhoneNumber())
                    .email(tutor.getEmail())
                    .voice(tutor.getVoice())
                    .major(tutor.getMajor())
                    .ecademicLevel(tutor.getEcademicLevel())
                    .description(tutor.getDescription())
                    .citizenIdentificationCard(tutor.getCitizenIdentificationCard())
                    .issued(tutor.getIssued())
                    .citizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront())
                    .citizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside())
                    .cardPhoto(tutor.getCardPhoto())
                    .schoolTeachOrStudent(tutor.getSchoolTeachOrStudent())
                    .numberTeachOfWeak(tutor.getNumberTeachOfWeek())
                    .salaryRequest(tutor.getSalaryRequest())
                    .createAt(tutor.getCreateAt())
                    .approved(tutor.isApproved())
                    .build();

            List<TutorSubject> listTutorSubject = tutor.getListTutorSubject();
            List<Teaching> listTeachings = tutor.getListTeachings();
            List<FeedBack> listFeedback = tutor.getListFeedbacks();

            List<SubjectDTO> listSubjectDTOs = new ArrayList<>();
            List<TeachingDTO> listTeachingDTO = new ArrayList<>();
            List<FeedBackDTO> listFeedbackDTO = new ArrayList<>();

            for (TutorSubject tutorSubject : listTutorSubject) {
                SubjectDTO subjectDTO = SubjectDTO.builder()

                        .id(tutorSubject.getSubject().getId())
                        .subjectName(tutorSubject.getSubject().getSubjectName())
                        .description(tutorSubject.getSubject().getDescription())
                        .totalMoneyMonthTeaching(tutorSubject.getSubject().getTotalMoneyMonthTeaching())
                        .numberTeachOfWeek(tutorSubject.getSubject().getNumberTeachOfWeek())
                        .oneHourTeaching(tutorSubject.getSubject().getOneHourTeaching())
                        .build();

                listSubjectDTOs.add(subjectDTO);

            }
            for (Teaching teaching : listTeachings) {
                TeachingDTO teachingDTO = TeachingDTO.builder()
                        .teachingId(teaching.getId())
                        .teachingName(teaching.getTeachingName())
                        .schedule(teaching.getSchedule())
                        .build();
                listTeachingDTO.add(teachingDTO);
            }

            for (FeedBack feedback : listFeedback) {
                FeedBackDTO feedBackDTO = FeedBackDTO.builder()
                        .feedbackId(feedback.getId())
                        .content(feedback.getContent())
                        .rating(feedback.getRating())
                        .createAt(feedback.getCreateAt())
                        .build();

                listFeedbackDTO.add(feedBackDTO);
            }
            tutorManyDTO.setListSubjectDTO(listSubjectDTOs);
            tutorManyDTO.setListTeachingDTO(listTeachingDTO);
            tutorManyDTO.setListFeedbackDTO(listFeedbackDTO);

            listTutorDTO.add(tutorManyDTO);
        }

        return listTutorDTO;
    }

    @Override
    public List<TutorManyDTO> getAllTutorSearchAndPagingAndSort(String title, int page, int record) {
        List<Tutor> listTutors = tutorRepository.findByFullNameContaining(title, pagingSearchAndSorting.pageablePageSizeAndRecordOrSearchOrSort(page, record));
        List<TutorManyDTO> listTutorDTO = new ArrayList<>();

        for (Tutor tutor : listTutors) {
            TutorManyDTO tutorManyDTO = TutorManyDTO.builder()
                    .id(tutor.getId())
                    .cityTech(tutor.getCityTeach())
                    .fullName(tutor.getFullName())
                    .gender(tutor.getGender())
                    .dateOfBirth(tutor.getDateOfBirth())
                    .address(tutor.getAddress())
                    .phoneNumber(tutor.getPhoneNumber())
                    .email(tutor.getEmail())
                    .voice(tutor.getVoice())
                    .major(tutor.getMajor())
                    .ecademicLevel(tutor.getEcademicLevel())
                    .description(tutor.getDescription())
                    .citizenIdentificationCard(tutor.getCitizenIdentificationCard())
                    .issued(tutor.getIssued())
                    .citizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront())
                    .citizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside())
                    .cardPhoto(tutor.getCardPhoto())
                    .schoolTeachOrStudent(tutor.getSchoolTeachOrStudent())
                    .numberTeachOfWeak(tutor.getNumberTeachOfWeek())
                    .salaryRequest(tutor.getSalaryRequest())
                    .createAt(tutor.getCreateAt())
                    .approved(tutor.isApproved())
                    .build();

            List<TutorSubject> listTutorSubject = tutor.getListTutorSubject();
            List<Teaching> listTeachings = tutor.getListTeachings();
            List<FeedBack> listFeedback = tutor.getListFeedbacks();

            List<SubjectDTO> listSubjectDTOs = new ArrayList<>();
            List<TeachingDTO> listTeachingDTO = new ArrayList<>();
            List<FeedBackDTO> listFeedbackDTO = new ArrayList<>();

            for (TutorSubject tutorSubject : listTutorSubject) {
                SubjectDTO subjectDTO = SubjectDTO.builder()
                        .id(tutorSubject.getSubject().getId())
                        .subjectName(tutorSubject.getSubject().getSubjectName())
                        .description(tutorSubject.getSubject().getDescription())
                        .totalMoneyMonthTeaching(tutorSubject.getSubject().getTotalMoneyMonthTeaching())
                        .numberTeachOfWeek(tutorSubject.getSubject().getNumberTeachOfWeek())
                        .oneHourTeaching(tutorSubject.getSubject().getOneHourTeaching())
                        .build();

                listSubjectDTOs.add(subjectDTO);
            }
            for (Teaching teaching : listTeachings) {
                TeachingDTO teachingDTO = TeachingDTO.builder()
                        .teachingId(teaching.getId())
                        .teachingName(teaching.getTeachingName())
                        .schedule(teaching.getSchedule())
                        .build();
                listTeachingDTO.add(teachingDTO);
            }

            for (FeedBack feedback : listFeedback) {
                FeedBackDTO feedBackDTO = FeedBackDTO.builder()
                        .feedbackId(feedback.getId())
                        .content(feedback.getContent())
                        .rating(feedback.getRating())
                        .createAt(feedback.getCreateAt())
                        .build();

                listFeedbackDTO.add(feedBackDTO);
            }
            tutorManyDTO.setListSubjectDTO(listSubjectDTOs);
            tutorManyDTO.setListTeachingDTO(listTeachingDTO);
            tutorManyDTO.setListFeedbackDTO(listFeedbackDTO);

            listTutorDTO.add(tutorManyDTO);
        }

        return listTutorDTO;
    }

    @Override
    public TutorRequest addTutor(MultipartFile[] files, TutorRequest request) {
        try {
            boolean isSuccess = fileService.uploadMultiFile(files);

            if (isSuccess) {
                String cityTeach = request.getCityTeach();
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
                String issued = request.getIssued();
                String schoolOfTeach = request.getSchoolTeachOrStudent();
                int numberTeachOfWeek = request.getNumberTeachOfWeek();
                double salaryRequest = request.getSalaryRequest();
                LocalDateTime createAt = request.getCreateAt();
                if (createAt == null) {
                    createAt = LocalDateTime.now();
                }
                List<Subject> listSubject = request.getListSubjects();

                Tutor newTutor = new Tutor();
                newTutor.setCityTeach(cityTeach);
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
                newTutor.setIssued(issued);
                newTutor.setSchoolTeachOrStudent(schoolOfTeach);
                newTutor.setNumberTeachOfWeek(numberTeachOfWeek);
                newTutor.setSalaryRequest(salaryRequest);
                newTutor.setCreateAt(createAt);
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    if (!file.isEmpty()) {
                        switch (i) {
                            case 0:
                                newTutor.setCitizenIdentificationCard(file.getOriginalFilename());
                                break;
                            case 1:
                                newTutor.setCitizenIdentificationCardFront(file.getOriginalFilename());
                                break;
                            case 2:
                                newTutor.setCitizenIdentificationCardFrontBackside(file.getOriginalFilename());
                                break;
                            case 3:
                                newTutor.setCardPhoto(file.getOriginalFilename());
                                break;
                            default:
                                break;
                        }
                    }
                }
                Tutor saveTutor = tutorRepository.save(newTutor);

                List<TutorSubject> listTutorSubject = new ArrayList<>();
                for (Subject subject : listSubject) {

                    Subject existingSubject = subjectRepository.findById(subject.getId()).get();

                    TutorSubject tutorSubject = new TutorSubject();
                    tutorSubject.setTutor(saveTutor);
                    tutorSubject.setSubject(existingSubject);
                    listTutorSubject.add(tutorSubject);
                }
                tutorSubjecRepository.saveAll(listTutorSubject);
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
    @Transactional
    public boolean updateTutor(MultipartFile[] files, int tutorId, TutorRequest request) {
        Optional<Tutor> checkTutorExistOrNot = tutorRepository.findById(tutorId);
        try {
            if (checkTutorExistOrNot.isPresent()) {
                Tutor tutor = checkTutorExistOrNot.get();
                tutor.setCityTeach(request.getCityTeach());
                tutor.setFullName(request.getFullName());
                tutor.setGender(request.getGender());
                tutor.setDateOfBirth(request.getDateOfBirth());
                tutor.setAddress(request.getAddress());
                tutor.setPhoneNumber(request.getPhoneNumber());
                tutor.setEmail(request.getEmail());
                tutor.setVoice(request.getVoice());
                tutor.setMajor(request.getMajor());
                tutor.setEcademicLevel(request.getEcademicLevel());
                tutor.setDescription(request.getDescription());
                tutor.setIssued(request.getIssued());
                tutor.setSchoolTeachOrStudent(request.getSchoolTeachOrStudent());
                tutor.setNumberTeachOfWeek(request.getNumberTeachOfWeek());
                tutor.setSalaryRequest(request.getSalaryRequest());

                // Thực hiện lưu tutor chỉ khi có ID
                if (tutor.getId() > 0) {
                    for (int i = 0; i < files.length; i++) {
                        MultipartFile file = files[i];
                        if (!file.isEmpty()) {
                            switch (i) {
                                case 0:
                                    tutor.setCitizenIdentificationCard(file.getOriginalFilename());
                                    break;
                                case 1:
                                    tutor.setCitizenIdentificationCardFront(file.getOriginalFilename());
                                    break;
                                case 2:
                                    tutor.setCitizenIdentificationCardFrontBackside(file.getOriginalFilename());
                                    break;
                                case 3:
                                    tutor.setCardPhoto(file.getOriginalFilename());
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    tutorSubjecRepository.deleteAllByTutor_Id(tutorId);

                    tutor = tutorRepository.save(tutor);

                    List<TutorSubject> listTutorSubjects = new ArrayList<>();
                    if (request.getListSubjects() != null) {
                        for (Subject subject : request.getListSubjects()) {
                            Subject existingSubject = subjectRepository.findById(subject.getId()).orElse(null);
                            if (existingSubject != null) {
                                TutorSubject tutorSubject = new TutorSubject();
                                tutorSubject.setTutor(tutor);
                                tutorSubject.setSubject(existingSubject);
                                listTutorSubjects.add(tutorSubject);
                            }
                        }
                        tutorSubjecRepository.saveAll(listTutorSubjects);
                    }

                    return true;
                } else {
                    throw new RuntimeException("Tutor ID is null. Cannot update tutor.");
                }
            }
        } catch (Exception e) {

            throw new RuntimeException("Update tutor fail: " + e.getMessage());
        }
        return false;
    }

    @Override
    public TutorManyDTO getTutorById(int tutorId) {
        Tutor tutor = tutorRepository.findById(tutorId).orElseThrow(() -> new RuntimeException("Tutor not found"));
        if (tutor != null) {
            List<TutorSubject> listTutorSubject = tutorSubjecRepository.findByTutorId(tutorId);
            TutorManyDTO tutorManyDTO = TutorManyDTO.builder()
                    .id(tutor.getId())
                    .cityTech(tutor.getCityTeach())
                    .fullName(tutor.getFullName())
                    .gender(tutor.getGender())
                    .dateOfBirth(tutor.getDateOfBirth())
                    .address(tutor.getAddress())
                    .phoneNumber(tutor.getPhoneNumber())
                    .email(tutor.getEmail())
                    .voice(tutor.getVoice())
                    .major(tutor.getMajor())
                    .ecademicLevel(tutor.getEcademicLevel())
                    .description(tutor.getDescription())
                    .citizenIdentificationCard(tutor.getCitizenIdentificationCard())
                    .issued(tutor.getIssued())
                    .citizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront())
                    .citizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside())
                    .cardPhoto(tutor.getCardPhoto())
                    .schoolTeachOrStudent(tutor.getSchoolTeachOrStudent())
                    .numberTeachOfWeak(tutor.getNumberTeachOfWeek())
                    .salaryRequest(tutor.getSalaryRequest())
                    .createAt(tutor.getCreateAt())
                    .approved(tutor.isApproved())
                    .build();

            List<SubjectDTO> listSubjectDT0 = listTutorSubject.stream().map(
                    TubjectDTO -> {
                        return SubjectDTO.builder()
                                .id(TubjectDTO.getId())
                                .subjectName(TubjectDTO.getSubject().getSubjectName())
                                .description(TubjectDTO.getSubject().getDescription())
                                .totalMoneyMonthTeaching(TubjectDTO.getSubject().getTotalMoneyMonthTeaching())
                                .numberTeachOfWeek(TubjectDTO.getSubject().getNumberTeachOfWeek())
                                .oneHourTeaching(TubjectDTO.getSubject().getOneHourTeaching())
                                .build();
                    }).collect(Collectors.toList());

            List<TeachingDTO> listTeachingDTO = tutor.getListTeachings().stream().map(
                    teaching -> {
                        return TeachingDTO.builder()
                                .teachingId(teaching.getId())
                                .teachingName(teaching.getTeachingName())
                                .schedule(teaching.getSchedule())
                                .build();
                    }).collect(Collectors.toList());

            List<FeedBackDTO> listFeedbackDTO = tutor.getListFeedbacks().stream().map(
                    feedback -> {
                        return FeedBackDTO.builder()
                                        .feedbackId(feedback.getId())
                                        .content(feedback.getContent())
                                        .rating(feedback.getRating())
                                        .createAt(feedback.getCreateAt())
                                        .build();
                    }).collect(Collectors.toList());

            tutorManyDTO.setListSubjectDTO(listSubjectDT0);
            tutorManyDTO.setListTeachingDTO(listTeachingDTO);
            tutorManyDTO.setListFeedbackDTO(listFeedbackDTO);
            return tutorManyDTO;
        }
        return null;
    }
}
