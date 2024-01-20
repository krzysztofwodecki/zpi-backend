package com.example.zpi.repositories;

import com.example.zpi.entities.RedeemedRewardEntity;
import com.example.zpi.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RedeemedRewardRepository extends CrudRepository<RedeemedRewardEntity, Long> {
    List<RedeemedRewardEntity> findAllByUser(UserEntity user);
}
