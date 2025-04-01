package com.healthcaremanagement.healthcare1.controller;

import com.healthcaremanagement.healthcare1.entity.Patient;
import com.healthcaremanagement.healthcare1.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/getAllpatients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @PostMapping("/Create")
    public Patient addPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }
}
