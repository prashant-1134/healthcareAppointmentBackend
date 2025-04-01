package com.healthcaremanagement.healthcare1.controller;

import com.healthcaremanagement.healthcare1.entity.Reminder;
import com.healthcaremanagement.healthcare1.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    // ✅ Get all reminders
    @GetMapping("/getAllReminders")
    public ResponseEntity<List<Reminder>> getAllReminders() {
        return ResponseEntity.ok(reminderService.getAllReminders());
    }

    // ✅ Get pending reminders
    @GetMapping("/getPending")
    public ResponseEntity<List<Reminder>> getPendingReminders() {
        return ResponseEntity.ok(reminderService.getPendingReminders());
    }

    // ✅ Create a new reminder
    @PostMapping("/create")
    public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {
        Reminder savedReminder = reminderService.createReminder(reminder);
        return ResponseEntity.ok(savedReminder);
    }

    // ✅ Mark a reminder as sent
    @PutMapping("/markSent/{reminderId}")
    public ResponseEntity<Reminder> markReminderSent(@PathVariable Long reminderId) {
        Reminder updatedReminder = reminderService.markReminderAsSent(reminderId);
        return ResponseEntity.ok(updatedReminder);
    }

    // ✅ Get reminders by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Reminder>> getRemindersByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(reminderService.getRemindersByPatientId(patientId));
    }
}
