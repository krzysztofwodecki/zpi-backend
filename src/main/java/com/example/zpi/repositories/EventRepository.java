package com.example.zpi.repositories;

import com.example.zpi.entities.EventEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, Long> {
    List<EventEntity> findAll(Sort sort);

    List<EventEntity> findByCreatorId(Long creatorId);
}
