package com.healthcaremanagement.healthcare1.controller;

import com.healthcaremanagement.healthcare1.dto.LoginRequest;
import com.healthcaremanagement.healthcare1.entity.Doctor;
import com.healthcaremanagement.healthcare1.entity.Patient;
import com.healthcaremanagement.healthcare1.repository.DoctorRepository;
import com.healthcaremanagement.healthcare1.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Check in Patient table
        Optional<Patient> patientOpt = patientRepository.findByEmail(request.getEmail());
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            if (patient.getPassword().equals(request.getPassword())) {
                response.put("role", "patient");
                response.put("id", patient.getPatientId());
                response.put("firstName", patient.getFirstName());
                response.put("lastName", patient.getLastName());// Include patientId
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid credentials"));
            }
        }

        // Check in Doctor table
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(request.getEmail());
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            if (doctor.getPassword().equals(request.getPassword())) {
                response.put("role", "doctor");
                response.put("id", doctor.getDoctorId()); // Include doctorId
                response.put("firstName", doctor.getFirstName());
                response.put("lastName", doctor.getLastName());//
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid credentials"));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "User not found"));
    }

}
