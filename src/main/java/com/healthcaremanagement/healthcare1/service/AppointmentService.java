package com.healthcaremanagement.healthcare1.service;

import com.healthcaremanagement.healthcare1.entity.Appointment;
import com.healthcaremanagement.healthcare1.entity.Reminder;
import com.healthcaremanagement.healthcare1.entity.Schedule;
import com.healthcaremanagement.healthcare1.repository.AppointmentRepository;
import com.healthcaremanagement.healthcare1.repository.ReminderRepository;
import com.healthcaremanagement.healthcare1.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    public Appointment bookAppointment(Appointment appointment) {
        Long doctorId = appointment.getDoctor().getDoctorId();
        LocalDate appointmentDate = appointment.getAppointmentDate();
        LocalTime appointmentTime = appointment.getAppointmentTime();

        // Check if doctor has an available schedule
        Optional<Schedule> optionalSchedule = scheduleRepository.findByDoctorDoctorIdAndAvailableDateAndStartTime(
                doctorId, appointmentDate, appointmentTime
        );

        if (optionalSchedule.isEmpty()) {
            throw new RuntimeException("Doctor is not available at this time!");
        }

        Schedule schedule = optionalSchedule.get();

        // Check if doctor is already booked
        if (schedule.isBooked()) {
            throw new RuntimeException("Doctor is already booked at this time!");
        }

        // Mark the schedule as booked
        schedule.setBooked(true);
        scheduleRepository.save(schedule);

        // Save the appointment
        appointment.setStatus(Appointment.Status.Scheduled);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // 🔔 Create Reminder (One Day Before Appointment)
        LocalDate reminderDate = appointmentDate.minusDays(1);
        LocalTime reminderTime = appointmentTime;

        Reminder reminder = new Reminder();
        reminder.setAppointment(savedAppointment);
        reminder.setReminderDate(reminderDate);
        reminder.setReminderTime(reminderTime);
        reminder.setStatus(Reminder.Status.Pending);
        reminderRepository.save(reminder);

        return savedAppointment;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // New method: Get upcoming appointments by patient ID
    public List<Appointment> getUpcomingAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findUpcomingAppointmentsByPatientId(patientId);
    }

    // New method: Get past appointments for a patient
    public List<Appointment> getPastAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findPastAppointmentsByPatientId(patientId);
    }

    // Scheduled method to update past appointments status to Completed at 13:10 every day
    @Scheduled(cron = "0 30 13 * * ?")
    public void updatePastAppointmentsStatus() {
        List<Appointment> pastAppointments = appointmentRepository.findPastScheduledAppointments();
        for (Appointment appt : pastAppointments) {
            appt.setStatus(Appointment.Status.Completed);
            appointmentRepository.save(appt);
            System.out.println("Updated appointment " + appt.getAppointmentId() + " to Completed.");
        }
    }

//    @Transactional
//    public void deleteAppointment(Long appointmentId) {
//        // Find the appointment to delete
//        Appointment appointment = appointmentRepository.findById(appointmentId)
//                .orElseThrow(() -> new RuntimeException("Appointment not found"));
//
//        // If the appointment is scheduled, update the doctor's schedule
//        if (appointment.getStatus() == Appointment.Status.Scheduled) {
//            Schedule schedule = scheduleRepository.findByDoctorDoctorIdAndAvailableDateAndStartTime(
//                    appointment.getDoctor().getDoctorId(),
//                    appointment.getAppointmentDate(),
//                    appointment.getAppointmentTime()
//            ).orElseThrow(() -> new RuntimeException("Schedule not found for this appointment"));
//
//            // Mark the schedule as available again (set isBooked to false)
//            schedule.setBooked(false);
//            scheduleRepository.save(schedule);
//        }
//
//        // Delete the reminder associated with this appointment
//        reminderRepository.deleteByReminderAppointment(appointment);
//
//        // Delete the appointment
//        appointmentRepository.delete(appointment);
//    }

    public List<Appointment> getUpcomingAppointmentsByDoctorId(Long doctorId) {
        // Assuming you have a repository for Appointment, and it has the necessary method to fetch appointments by doctorId and future dates.
        return appointmentRepository.findByDoctorIdAndAppointmentDateAfter(doctorId);
    }



}
