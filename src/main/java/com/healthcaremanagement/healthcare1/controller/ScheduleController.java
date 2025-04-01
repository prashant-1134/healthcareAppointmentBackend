package com.healthcaremanagement.healthcare1.controller;

import com.healthcaremanagement.healthcare1.entity.Schedule;
import com.healthcaremanagement.healthcare1.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    // Get all schedules
    @GetMapping("/getAll")
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Get schedules by doctor ID
    @GetMapping("/doctor/{doctorId}")
    public List<Schedule> getSchedulesByDoctor(@PathVariable Long doctorId) {
        return scheduleRepository.findByDoctorDoctorId(doctorId);
    }

    // Get unbooked schedules by doctor ID and a specific date
    @GetMapping("/doctor/{doctorId}/unbooked/{appointmentDate}")
    public List<Schedule> getUnbookedSchedulesByDate(
            @PathVariable Long doctorId,
            @PathVariable String appointmentDate) {

        // Convert appointmentDate to LocalDate (YYYY-MM-DD format)
        LocalDate date = LocalDate.parse(appointmentDate);

        // Fetch unbooked schedules for the doctor on the specified date
        return scheduleRepository.findByDoctorDoctorIdAndAvailableDateAndIsBookedFalse(doctorId, date);
    }


    // Create a new schedule
    @PostMapping("/create")
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // Update a schedule (mark as booked)
    @PutMapping("/{scheduleId}/book")
    public Schedule bookSchedule(@PathVariable Long scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setBooked(true);
            return scheduleRepository.save(schedule);
        } else {
            throw new RuntimeException("Schedule not found");
        }
    }

    @GetMapping("/doctor/{doctorId}/available-times/{appointmentDate}")
    public List<LocalTime> getAvailableTimes(
            @PathVariable Long doctorId,
            @PathVariable String appointmentDate) {

        // Convert appointmentDate string to LocalDate
        LocalDate date = LocalDate.parse(appointmentDate);

        // Fetch unbooked schedules for the doctor on the given date
        List<Schedule> unbookedSchedules = scheduleRepository.findByDoctorDoctorIdAndAvailableDateAndIsBookedFalse(doctorId, date);

        // Extract available time slots (start times of unbooked schedules)
        return unbookedSchedules.stream()
                .map(Schedule::getStartTime)
                .toList();
    }


    // Delete a schedule
    @DeleteMapping("/delete/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}

