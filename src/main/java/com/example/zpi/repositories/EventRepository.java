package com.example.zpi.repositories;

import com.example.zpi.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventEntity, Long> {
}
