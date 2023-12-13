package com.example.zpi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class AttendanceEntity {

    protected AttendanceEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private EventEntity event;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    public AttendanceEntity(UserEntity user, EventEntity event, LocalDateTime checkInTime, LocalDateTime checkOutTime){
        this.user = user;
        this.event = event;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }
}
