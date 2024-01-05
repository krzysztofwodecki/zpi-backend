package com.example.zpi.controllers;

import com.example.zpi.entities.RedeemedRewardEntity;
import com.example.zpi.entities.RewardEntity;
import com.example.zpi.service.EventService;
import com.example.zpi.service.RewardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardsController {
    final private RewardService rewardService;
    final private EventService eventService;

    public RewardsController(RewardService rewardService, EventService eventService) {
        this.rewardService = rewardService;
        this.eventService = eventService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RewardEntity>> getAvailableRewards() {
        List<RewardEntity> rewards = rewardService.getAllRewards();
        return ResponseEntity.ok(rewards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardEntity> getAvailableRewardById(@PathVariable Long id) {
        try{
            RewardEntity reward = rewardService.getRewardById(id);
            return ResponseEntity.ok(reward);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/redeemed")
    public ResponseEntity<List<RedeemedRewardEntity>> getRedeemedRewards(@RequestParam(required = true) Long userId) {
        try{
            eventService.getUserById(userId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        List<RedeemedRewardEntity> rewards = rewardService.getRedeemedRewards(userId);
        return ResponseEntity.ok(rewards);
    }
}