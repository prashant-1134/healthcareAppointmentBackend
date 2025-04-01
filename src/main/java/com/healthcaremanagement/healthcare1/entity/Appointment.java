package com.healthcaremanagement.healthcare1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;  // ✅ Change Date → LocalTime

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('Scheduled', 'Completed', 'Cancelled') DEFAULT 'Scheduled'")
    private Status status;

    @Column(name = "created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdAt;  // ✅ Change Date → LocalDate

    public enum Status {
        Scheduled, Completed, Cancelled
    }
}
