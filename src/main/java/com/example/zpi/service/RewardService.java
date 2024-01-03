package com.example.zpi.service;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.EventEntity;
import com.example.zpi.entities.RewardEntity;
import com.example.zpi.entities.UserEntity;
import com.example.zpi.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class RewardService {
    private final UserRepository userRepository;
    private final RewardRepository rewardRepository;
    private final RedeemedRewardRepository redeemedRewardRepository;

    @Autowired
    public RewardService(UserRepository userRepository, RewardRepository rewardRepository, RedeemedRewardRepository redeemedRewardRepository) {
        this.userRepository = userRepository;
        this.rewardRepository = rewardRepository;
        this.redeemedRewardRepository = redeemedRewardRepository;
    }

    public List<RewardEntity> getAllRewards(){
        List<RewardEntity> rewards = new ArrayList<RewardEntity>();
        rewardRepository.findAll().forEach(rewards::add);
        return rewards;
    }
}
