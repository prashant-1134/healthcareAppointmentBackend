package com.healthcaremanagement.healthcare1.repository;

import com.healthcaremanagement.healthcare1.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.patient.patientId = :patientId AND " +
            "(a.appointmentDate > CURRENT_DATE OR " +
            "(a.appointmentDate = CURRENT_DATE AND a.appointmentTime > CURRENT_TIME))")
    List<Appointment> findUpcomingAppointmentsByPatientId(@Param("patientId") Long patientId);

    // New method: fetch past appointments (appointments that have already happened)
    @Query("SELECT a FROM Appointment a WHERE a.patient.patientId = :patientId AND " +
            "(a.appointmentDate < CURRENT_DATE OR " +
            "(a.appointmentDate = CURRENT_DATE AND a.appointmentTime <= CURRENT_TIME))")
    List<Appointment> findPastAppointmentsByPatientId(@Param("patientId") Long patientId);

    // New method: Fetch all Scheduled appointments that have already passed
    @Query("SELECT a FROM Appointment a WHERE a.status = 'Scheduled' AND " +
            "((a.appointmentDate < CURRENT_DATE) OR " +
            "(a.appointmentDate = CURRENT_DATE AND a.appointmentTime < CURRENT_TIME))")
    List<Appointment> findPastScheduledAppointments();



    @Query("SELECT a FROM Appointment a WHERE a.doctor.doctorId = :doctorId AND " +
            "(a.appointmentDate > CURRENT_DATE OR " +
            "(a.appointmentDate = CURRENT_DATE AND a.appointmentTime > CURRENT_TIME))")
    List<Appointment> findByDoctorIdAndAppointmentDateAfter(@Param("doctorId") Long doctorId);



}
