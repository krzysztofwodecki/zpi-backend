package com.example.zpi.repositories;

import com.example.zpi.entities.AttendanceEntity;
import org.springframework.data.repository.CrudRepository;

public interface AttendanceRepository extends CrudRepository<AttendanceEntity, Long> {
}
