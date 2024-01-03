package com.example.zpi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class RewardEntity {

    protected RewardEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Long value;

    public RewardEntity(String name, String description, Long value){
        this.name = name;
        this.description = description;
        this.value = value;
    }
}
