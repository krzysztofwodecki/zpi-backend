package com.example.zpi.service;

import com.example.zpi.entities.*;
import com.example.zpi.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<RewardEntity> getRedeemedRewards(Long userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return redeemedRewardRepository.findAllByUser(user)
                .stream()
                .map(RedeemedRewardEntity::getReward)
                .collect(Collectors.toList());
    }

    public RewardEntity getRewardById(Long id){
        return rewardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reward not found with id: " + id));
    }

    @Transactional
    public RedeemedRewardEntity redeemRewardForUser(Long rewardId, Long userId){
        RewardEntity reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new EntityNotFoundException("Reward not found with id: " + rewardId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if(user.getPoints() < reward.getValue()) {
            throw new IllegalArgumentException();
        }

        user.setPoints(user.getPoints() - reward.getValue());
        RedeemedRewardEntity redeemedRewardEntity = new RedeemedRewardEntity(reward, user);
        redeemedRewardEntity = redeemedRewardRepository.save(redeemedRewardEntity);
        return redeemedRewardEntity;
    }
}
