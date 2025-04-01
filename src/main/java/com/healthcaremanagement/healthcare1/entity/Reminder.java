package com.healthcaremanagement.healthcare1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "reminders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reminder_id")
    private Long reminderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(name = "reminder_date", nullable = false)
    private LocalDate reminderDate;  // Use LocalDate instead of Date

    @Column(name = "reminder_time", nullable = false)
    private LocalTime reminderTime;  // Use LocalTime instead of Date

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.Pending;  // Default value in Java instead of column definition

    // Getter and Setter
    @Setter
    @Getter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Status {
        Pending, Sent
    }
}
