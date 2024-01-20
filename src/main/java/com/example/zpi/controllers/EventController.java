package com.example.zpi.controllers;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.EventEntity;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<EventEntity> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventEntity> updateEvent(
            @PathVariable Long id,
            @RequestBody EventEntity updatedEvent) {
        try {
            EventEntity savedEvent = eventService.updateEvent(id, updatedEvent);
            return ResponseEntity.ok(savedEvent);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping("/{id}/checkIn")
    public ResponseEntity<AttendanceEntity> checkUserIn(@PathVariable Long id) {
        try {
            AttendanceEntity attendance = eventService.createAttendance(id);
            return ResponseEntity.ok(attendance);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/liked")
    public ResponseEntity<List<EventEntity>> likedEvents() {
        return ResponseEntity.ok(eventService.getLikedEvents());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<EventEntity> likeEvent(@PathVariable Long id) {
        try {
            EventEntity event = eventService.likeEvent(id);
            return ResponseEntity.ok(event);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<EventEntity> unlikeEvent(@PathVariable Long id) {
        try {
            eventService.unlikeEvent(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
