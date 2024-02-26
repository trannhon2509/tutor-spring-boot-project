package com.project.tutor.mapper;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class Learning implements Serializable {
    @Column(name = "tutor_id")
    private int tutorId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "start_time")
    private LocalDateTime timeStart;

    @Column(name = "end_time")
    private LocalDateTime timeEnd;

    @Column(name = "start_date")
    private LocalDateTime dateStart;

}
