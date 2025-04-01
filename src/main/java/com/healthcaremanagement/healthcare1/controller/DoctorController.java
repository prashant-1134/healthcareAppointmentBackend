package com.healthcaremanagement.healthcare1.controller;


import com.healthcaremanagement.healthcare1.entity.Doctor;
import com.healthcaremanagement.healthcare1.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    // Get all doctors
    @GetMapping("/getAllDoctors")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Add a new doctor
    @PostMapping("/create")
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}

