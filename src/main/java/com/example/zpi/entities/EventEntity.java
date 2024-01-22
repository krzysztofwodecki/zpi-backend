package com.example.zpi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class EventEntity {

    protected EventEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long creatorId;
    private String eventName;
    private LocalDateTime eventDateTime;
    private String location;
    private String description;

    public EventEntity(Long creatorId, String name, LocalDateTime dateTime, String location, String description){
        this.creatorId = creatorId;
        this.eventName = name;
        this.eventDateTime = dateTime;
        this.location = location;
        this.description = description;
    }
}
