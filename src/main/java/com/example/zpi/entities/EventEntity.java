package com.example.zpi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class EventEntity {

    protected EventEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;
    private LocalDateTime eventDateTime;
    private String location;

    public EventEntity(String name, LocalDateTime dateTime, String location){
        this.eventName = name;
        this.eventDateTime = dateTime;
        this.location = location;
    }
}
