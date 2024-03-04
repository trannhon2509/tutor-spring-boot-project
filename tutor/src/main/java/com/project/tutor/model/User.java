package com.project.tutor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.tutor.mapper.Booking;
import com.project.tutor.mapper.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private String passwordRepeat;

    @Transient
    private String passWordRepeatNew;

    @Column(name = "email")
    private String email;

    @Column(name = "fisrt_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name ="active_code")
    private String activeCode;

    @Column(name = "create_at")
    @UpdateTimestamp
    private LocalDateTime creatAt;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserRole> listUserRoles = new ArrayList<>();


    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    private List<FeedBack> listFeedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Booking> listBooking = new ArrayList<>();


}
