package com.project.tutor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.tutor.model.Tutor;
import com.project.tutor.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "feedback")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id" , unique = true,nullable = false)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private double rating;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_id")
    @JsonIgnoreProperties("listFeedbacks")
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("listFeedbacks")
    private User user;
}
