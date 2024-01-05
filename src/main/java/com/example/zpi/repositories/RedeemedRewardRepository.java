package com.example.zpi.repositories;

import com.example.zpi.entities.RedeemedRewardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RedeemedRewardRepository extends CrudRepository<RedeemedRewardRepository, Long> {
    public List<RedeemedRewardEntity>  getRedeemedRewardsByUserId(Long userId);
}
