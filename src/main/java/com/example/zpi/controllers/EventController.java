package com.example.zpi.controllers;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.EventEntity;
import com.example.zpi.entities.UserEntity;
import com.example.zpi.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    final private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventEntity>> getSortedEvents(@RequestParam(required = false) String sortBy) {
        List<EventEntity> sortedEvents = eventService.getSortedEvents(sortBy);
        return ResponseEntity.ok(sortedEvents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventEntity> getEventById(@PathVariable Long id) {
        try {
            EventEntity event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/created/{creatorId}")
    public ResponseEntity<List<EventEntity>> getEventByCreator(@PathVariable Long creatorId) {
        List<EventEntity> events = eventService.getEventsCreatedByUser(creatorId);
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<EventEntity> createEvent(@RequestBody EventEntity eventEntity) {
        try {
            EventEntity createdEvent = eventService.createEvent(eventEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // TODO: add verification to check if user is the owner of event
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO: add verification to check if user is the owner of event
    @PutMapping("/{id}")
    public ResponseEntity<EventEntity> updateEvent(
            @PathVariable Long id,
            @RequestBody EventEntity updatedEvent) {
        try {
            EventEntity savedEvent = eventService.updateEvent(id, updatedEvent);
            return ResponseEntity.ok(savedEvent);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/checkIn")
    public ResponseEntity<AttendanceEntity> checkUserIn(
            @PathVariable Long id,
            @RequestBody UserEntity user) {
        EventEntity eventEntity;
        try {
            eventEntity = eventService.getEventById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        UserEntity userEntity;
        try {
            userEntity = eventService.getUserById(user.getId());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        AttendanceEntity existingAttendance;
        try {
            existingAttendance = eventService.getAttendanceByUserAndEventId(user.getId(), id);
        } catch (EntityNotFoundException e) {
            existingAttendance = null;
        }
        if(existingAttendance != null) {
            return ResponseEntity.ok(existingAttendance);
        }else {
            AttendanceEntity createdAttendance = eventService.createAttendance(userEntity, eventEntity);
            return ResponseEntity.ok(createdAttendance);
        }
    }
}
