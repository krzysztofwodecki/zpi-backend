package com.example.zpi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class EventEntity {

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
