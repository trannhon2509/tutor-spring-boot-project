package com.project.tutor.repository;

import com.project.tutor.mapper.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingRepository extends JpaRepository<Booking , Integer> {
     public  List<Booking> findByUserId(int userId);

}
