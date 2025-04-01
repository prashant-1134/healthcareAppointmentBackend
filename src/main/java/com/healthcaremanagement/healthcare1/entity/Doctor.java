package com.healthcaremanagement.healthcare1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "specialty", length = 100, nullable = false)
    private String specialty;

    @Column(name = "password", length = 255, nullable = false)
    private String password; // ✅ New Password Field (Will store hashed passwords)

    @Column(name = "created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;
}
