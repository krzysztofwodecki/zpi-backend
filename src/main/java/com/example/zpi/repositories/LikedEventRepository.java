package com.example.zpi.repositories;

import com.example.zpi.entities.LikedEventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikedEventRepository extends CrudRepository<LikedEventEntity, Long> {

    LikedEventEntity findByEvent_IdAndUser_Id(Long eventId, Long userId);

    List<LikedEventEntity> findAllByUser_Id(Long userId);

    List<LikedEventEntity> findAllByEvent_Id(Long eventId);

    boolean existsByEvent_IdAndUser_Id(Long eventId, Long userId);
}
