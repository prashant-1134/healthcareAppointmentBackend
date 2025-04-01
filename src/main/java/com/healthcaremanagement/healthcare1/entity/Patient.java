package com.healthcaremanagement.healthcare1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;

    @Column(name = "password", length = 255, nullable = false)
    private String password; // ✅ New Password Field (Will store hashed passwords)

    @Column(name = "created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    // Gender Enum
    public enum Gender {
        Male, Female, Other
    }
}
