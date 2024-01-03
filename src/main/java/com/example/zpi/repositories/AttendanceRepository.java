package com.example.zpi.repositories;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends CrudRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findByEventId(Long id);

    Optional<AttendanceEntity> findByUserIdAndEventId(Long userId, Long eventId);
}
