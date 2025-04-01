package com.healthcaremanagement.healthcare1.repository;

import com.healthcaremanagement.healthcare1.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Check if a doctor is available for an appointment
    Optional<Schedule> findByDoctorDoctorIdAndAvailableDateAndStartTime(Long doctorId, LocalDate availableDate, LocalTime startTime);

    // Get all schedules for a specific doctor
    List<Schedule> findByDoctorDoctorId(Long doctorId);

    // Get unbooked schedules for a specific doctor on a specific date
    List<Schedule> findByDoctorDoctorIdAndAvailableDateAndIsBookedFalse(Long doctorId, LocalDate availableDate);

}
