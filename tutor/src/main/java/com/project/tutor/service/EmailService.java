package com.project.tutor.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendEmail (String from , String to , String subject , String text);
    public String createActiveCode ();
    public void sendEmailActive (String email , String activeCode);

    public void sendEmailResetPassword(String email);
}
