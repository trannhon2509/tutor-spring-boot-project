package com.project.tutor.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.tutor.model.Tutor;
import com.project.tutor.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id" , unique = true , nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.MERGE)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

}
