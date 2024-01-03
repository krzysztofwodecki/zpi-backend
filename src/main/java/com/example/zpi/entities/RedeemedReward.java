package com.example.zpi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RedeemedReward {

    protected RedeemedReward() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RewardEntity reward;

    @ManyToOne
    private UserEntity user;


    public RedeemedReward(RewardEntity reward, UserEntity user){
        this.reward = reward;
        this.user = user;
    }
}
