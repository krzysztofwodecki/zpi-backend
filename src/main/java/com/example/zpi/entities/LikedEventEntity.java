package com.example.zpi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LikedEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EventEntity event;

    @ManyToOne
    private UserEntity user;

    protected LikedEventEntity() {}
    public LikedEventEntity(EventEntity event, UserEntity user){
        this.event = event;
        this.user = user;
    }
}
