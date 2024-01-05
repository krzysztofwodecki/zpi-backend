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

    public List<RedeemedRewardEntity> getRedeemedRewards(Long userId){
        return redeemedRewardRepository.getRedeemedRewardsByUserId(userId);
    }

    public RewardEntity getRewardById(Long id){
        return rewardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reward not found with id: " + id));
    }

    public RedeemedRewardEntity redeemRewardForUser(RewardEntity reward, UserEntity user){
        //if(user.getPoints() < reward.getValue())
        //    throw new IllegalArgumentException();

        user.setPoints(user.getPoints() - reward.getValue());
        userRepository.save(user);
        RedeemedRewardEntity redeemedRewardEntity = new RedeemedRewardEntity(reward, user);
        redeemedRewardEntity = redeemedRewardRepository.save(redeemedRewardEntity);
        return redeemedRewardEntity;
    }
}
