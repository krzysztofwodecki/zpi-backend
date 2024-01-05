package com.example.zpi.repositories;

import com.example.zpi.entities.RedeemedRewardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RedeemedRewardRepository extends CrudRepository<RedeemedRewardEntity, Long> {
    public List<RedeemedRewardEntity>  getRedeemedRewardsByUserId(Long userId);
}
