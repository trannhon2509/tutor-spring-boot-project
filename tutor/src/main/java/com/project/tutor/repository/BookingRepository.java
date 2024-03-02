package com.project.tutor.repository;

import com.project.tutor.mapper.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface BookingRepository extends JpaRepository<Booking , Integer> {

}
