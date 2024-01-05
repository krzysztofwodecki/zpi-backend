package com.example.zpi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RedeemedRewardEntity {

    protected RedeemedRewardEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RewardEntity reward;

    @ManyToOne
    private UserEntity user;


    public RedeemedRewardEntity(RewardEntity reward, UserEntity user){
        this.reward = reward;
        this.user = user;
    }
}
