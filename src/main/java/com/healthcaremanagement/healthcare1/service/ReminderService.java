package com.healthcaremanagement.healthcare1.service;

import com.healthcaremanagement.healthcare1.entity.Reminder;
import com.healthcaremanagement.healthcare1.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private NotificationService notificationService;

    private LocalDateTime lastExecutionTime = LocalDateTime.now().minusDays(1); // Stores last reminder execution

    // ✅ Scheduled task: Runs daily at 9 AM
    @Scheduled(cron = "0 0 9 * * ?") // Runs every day at 9:00 AM
    @Transactional
    public void sendReminders() {
        LocalDate today = LocalDate.now();
        // Get pending reminders for today, ignore the 'createdAt' constraint
        List<Reminder> reminders = reminderRepository.findByReminderDateAndStatus(
                today, Reminder.Status.Pending);

        for (Reminder reminder : reminders) {
            String message = buildReminderMessage(reminder);

            // Send SMS notification
            notificationService.sendSms(reminder.getAppointment().getPatient().getPhoneNumber(), message);

            // Update reminder status to Sent
            reminder.setStatus(Reminder.Status.Sent);
            reminderRepository.save(reminder);
        }

        // Update last execution time after processing
        lastExecutionTime = LocalDateTime.now();
    }

    // ✅ Create a new reminder
    @Transactional
    public Reminder createReminder(Reminder reminder) {
        Reminder savedReminder = reminderRepository.save(reminder);

        // If the reminder is for today, send an immediate notification
        if (reminder.getReminderDate().equals(LocalDate.now())) {
            String message = buildReminderMessage(reminder);
            notificationService.sendSms(reminder.getAppointment().getPatient().getPhoneNumber(), message);

            // Mark as Sent
            reminder.setStatus(Reminder.Status.Sent);
            reminderRepository.save(reminder);

            // Optional: Call sendReminders to process immediately (if needed)
            sendReminders();  // This will trigger the scheduled reminder process immediately
        }

        return savedReminder;
    }

    // ✅ Helper method to construct the reminder message
    private String buildReminderMessage(Reminder reminder) {
        return "📅 Reminder: " + reminder.getAppointment().getPatient().getFirstName() + " " +
                reminder.getAppointment().getPatient().getLastName() +
                ", your appointment with Dr. " +
                reminder.getAppointment().getDoctor().getFirstName() + " " +
                reminder.getAppointment().getDoctor().getLastName() +
                " is scheduled for tomorrow at " + reminder.getReminderTime() +
                ". Please be on time. 🏥";
    }

    // ✅ Mark a reminder as Sent
    @Transactional
    public Reminder markReminderAsSent(Long reminderId) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));
        reminder.setStatus(Reminder.Status.Sent);
        return reminderRepository.save(reminder);
    }

    // ✅ Get all reminders
    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll();
    }

    // ✅ Get pending reminders
    public List<Reminder> getPendingReminders() {
        return reminderRepository.findByStatus(Reminder.Status.Pending);
    }

    // ✅ Get reminders by patient ID
    public List<Reminder> getRemindersByPatientId(Long patientId) {
        return reminderRepository.findByAppointmentPatientPatientId(patientId);
    }
}
