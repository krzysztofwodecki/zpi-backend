package com.example.zpi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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

    public AttendanceEntity(UserEntity user, EventEntity event, LocalDateTime checkInTime){
        this.user = user;
        this.event = event;
        this.checkInTime = checkInTime;
    }
}
