package com.example.zpi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class AttendanceEntity {

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
