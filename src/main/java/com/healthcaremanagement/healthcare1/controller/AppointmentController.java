package com.healthcaremanagement.healthcare1.controller;

import com.healthcaremanagement.healthcare1.entity.Appointment;
import com.healthcaremanagement.healthcare1.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Get all appointments
    @GetMapping("/getAllAppointments")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Create a new appointment with validation
    @PostMapping("/create")
    public Appointment addAppointment(@RequestBody Appointment appointment) {
        return appointmentService.bookAppointment(appointment);
    }

    // New endpoint: Get upcoming appointments by patient ID
    @GetMapping("/patient/upcoming/{patientId}")
    public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByPatientId(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getUpcomingAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }


    // New endpoint: Get past appointments by patient ID
    @GetMapping("/patient/past/{patientId}")
    public ResponseEntity<List<Appointment>> getPastAppointmentsByPatientId(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getPastAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }


//    // New endpoint: Delete an appointment by ID
//    @DeleteMapping("/delete/{appointmentId}")
//    public ResponseEntity<String> deleteAppointment(@PathVariable Long appointmentId) {
//        try {
//            appointmentService.deleteAppointment(appointmentId);
//            return ResponseEntity.ok("Appointment cancelled successfully.");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(400).body("Error: " + e.getMessage());
//        }
//    }

    // New endpoint: Get all upcoming appointments by doctor ID
    @GetMapping("/doctor/upcoming/{doctorId}")
    public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByDoctorId(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getUpcomingAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }


}
