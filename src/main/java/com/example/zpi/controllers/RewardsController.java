package com.example.zpi.controllers;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.RewardEntity;
import com.example.zpi.service.EventService;
import com.example.zpi.service.RewardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardsController {
    final private RewardService rewardService;

    public RewardsController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RewardEntity>> getAvailableRewards() {
        List<RewardEntity> rewards = rewardService.getAllRewards();
        return ResponseEntity.ok(rewards);
    }
}
