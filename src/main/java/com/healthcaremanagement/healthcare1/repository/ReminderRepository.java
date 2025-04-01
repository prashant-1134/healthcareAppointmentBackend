package com.healthcaremanagement.healthcare1.repository;

import com.healthcaremanagement.healthcare1.entity.Appointment;
import com.healthcaremanagement.healthcare1.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByStatus(Reminder.Status status);
    List<Reminder> findByReminderDateAndStatus(LocalDate reminderDate, Reminder.Status status);
    List<Reminder> findByAppointmentPatientPatientId(Long patientId);

    // ✅ Fetch reminders created after a specific time (to avoid duplicate reminders)
    List<Reminder> findByReminderDateAndStatusAndCreatedAtAfter(LocalDate reminderDate, Reminder.Status status, LocalDateTime createdAfter);

//    // Custom query to delete the reminder associated with an appointment
//    void deleteByReminderAppointment(Appointment appointment);
}
