package com.project.tutor.implementservice;


import com.project.tutor.dto.SubjectDTO;
import com.project.tutor.dto.TeachingDTO;
import com.project.tutor.many.dto.TutorManyDTO;
import com.project.tutor.mapper.TutorSubject;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    TutorSubjecRepository tutorSubjecRepository;

    @Autowired
    FileService fileService;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<TutorManyDTO> getAllTutor() {
        List<Tutor> listTutors = tutorRepository.findAll();
        List<TutorManyDTO> listTutorDTO = new ArrayList<>();

        for (Tutor tutor : listTutors) {
            TutorManyDTO tutorManyDTO = new TutorManyDTO();
            tutorManyDTO.setId(tutor.getId());
            tutorManyDTO.setCityTech(tutor.getCityTeach());
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
            List<Teaching> listTeachings = tutor.getListTeachings();

            List<SubjectDTO> listSubjectDTOs = new ArrayList<>();
            List<TeachingDTO> listTeachingDTO = new ArrayList<>();

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
            for (Teaching teaching : listTeachings){
                TeachingDTO teachingDTO   = new TeachingDTO();
                teachingDTO.setTeachingId(teaching.getId());
                teachingDTO.setTeachingName(teaching.getTeachingName());
                teachingDTO.setSchedule(teaching.getSchedule());
                listTeachingDTO.add(teachingDTO);
            }
            tutorManyDTO.setListSubjectDTO(listSubjectDTOs);
            tutorManyDTO.setListTeachingDTO(listTeachingDTO);
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
            // In lỗi hoặc log lỗi để kiểm tra
            e.printStackTrace();
            throw new RuntimeException("Update tutor fail: " + e.getMessage());
        }
        return false;
    }

    @Override
    public TutorManyDTO getTutorById(int tutorId) {
        Optional<Tutor> checkTutorExistOrNot = tutorRepository.findById(tutorId);
        if (checkTutorExistOrNot.isPresent()) {
            Tutor tutor = checkTutorExistOrNot.get();
            List<TutorSubject> listTutorSubject = tutorSubjecRepository.findByTutorId(tutorId);

            TutorManyDTO tutorManyDTO = new TutorManyDTO();
            tutorManyDTO.setId(tutor.getId());
            tutorManyDTO.setCityTech(tutor.getCityTeach());
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
