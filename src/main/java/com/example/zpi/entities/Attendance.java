package com.example.zpi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    // Getters and setters (and other constructors if needed)

    // Constructors, getters, setters, and other methods
}
