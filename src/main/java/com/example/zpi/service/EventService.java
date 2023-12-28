package com.example.zpi.service;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.EventEntity;
import com.example.zpi.repositories.AttendanceRepository;
import com.example.zpi.repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class EventService {

    private final EventRepository eventRepository;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public EventService(EventRepository eventRepository, AttendanceRepository attendanceRepository) {
        this.eventRepository = eventRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public List<EventEntity> getSortedEvents(String sortBy) {
        if (!"time".equalsIgnoreCase(sortBy) && !"name".equalsIgnoreCase(sortBy)) {
            return (List<EventEntity>) eventRepository.findAll();
        }
        Sort sort;
        if ("time".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Order.asc("eventDateTime"));
        } else {
            sort = Sort.by(Sort.Order.asc("eventName"));
        }
        return eventRepository.findAll(sort);
    }

    public List<EventEntity> getEventsCreatedByUser(Long userId) {
        return eventRepository.findByCreatorId(userId);
    }

    public EventEntity createEvent(EventEntity eventEntity) {
        if (isValid(eventEntity))
            return eventRepository.save(eventEntity);
        throw new IllegalArgumentException();
    }

    public EventEntity getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
    }

    @Transactional
    public void deleteEvent(Long id) {
        EventEntity existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));

        List<AttendanceEntity> attendanceEntities = attendanceRepository.findByEventId(id);
        attendanceRepository.deleteAll(attendanceEntities);
        eventRepository.delete(existingEvent);
    }

    @Transactional
    public EventEntity updateEvent(Long eventId, EventEntity updatedEvent) {
        EventEntity existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        if (updatedEvent.getEventName() != null)
            existingEvent.setEventName(updatedEvent.getEventName());
        if (updatedEvent.getEventDateTime() != null)
            existingEvent.setEventDateTime(updatedEvent.getEventDateTime());
        if (updatedEvent.getLocation() != null)
            existingEvent.setLocation(updatedEvent.getLocation());
        return existingEvent;
    }

    boolean isValid(EventEntity event) {
        return event != null &&
                event.getCreatorId() != null &&
                event.getEventDateTime() != null &&
                event.getEventName() != null &&
                event.getLocation() != null;
    }
}
