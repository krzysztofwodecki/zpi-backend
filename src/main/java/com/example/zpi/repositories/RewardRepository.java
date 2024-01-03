package com.example.zpi.repositories;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.RewardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RewardRepository extends CrudRepository<RewardEntity, Long> {
}
