package com.project.tutor.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookingService {
    public boolean addBooking (List<Map<String, Integer>> listTutorMaps);

}
