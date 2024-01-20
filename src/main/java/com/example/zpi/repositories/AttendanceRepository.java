package com.example.zpi.repositories;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.EventEntity;
import com.example.zpi.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttendanceRepository extends CrudRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findByEventId(Long id);

    List<AttendanceEntity> findByUserId(Long id);

    AttendanceEntity findByUserAndEvent(UserEntity user, EventEntity event);
}
