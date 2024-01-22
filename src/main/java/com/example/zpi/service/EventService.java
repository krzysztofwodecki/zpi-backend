package com.example.zpi.service;

import com.example.zpi.entities.*;
import com.example.zpi.repositories.AttendanceRepository;
import com.example.zpi.repositories.EventRepository;
import com.example.zpi.repositories.LikedEventRepository;
import com.example.zpi.repositories.UserRepository;
import com.example.zpi.security.UserInfoDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class EventService {

    private final EventRepository eventRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final LikedEventRepository likedEventRepository;

    @Autowired
    public EventService(EventRepository eventRepository, AttendanceRepository attendanceRepository, UserRepository userRepository, LikedEventRepository likedEventRepository) {
        this.eventRepository = eventRepository;
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.likedEventRepository = likedEventRepository;
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
    public void deleteEvent(Long id) throws IllegalAccessException {
        EventEntity existingEvent = getEventById(id);
        if (!isCurrentUserEventOwner(existingEvent)) {
            throw new IllegalAccessException();
        }

        List<AttendanceEntity> attendanceEntities = attendanceRepository.findByEventId(id);
        attendanceRepository.deleteAll(attendanceEntities);
        List<LikedEventEntity> likedEvents = likedEventRepository.findAllByEvent_Id(id);
        likedEventRepository.deleteAll(likedEvents);
        eventRepository.delete(existingEvent);
    }

    @Transactional
    public EventEntity updateEvent(Long eventId, EventEntity updatedEvent) throws IllegalAccessException {
        EventEntity existingEvent = getEventById(eventId);
        if (!isCurrentUserEventOwner(existingEvent)) {
            throw new IllegalAccessException();
        }

        if (updatedEvent.getEventName() != null)
            existingEvent.setEventName(updatedEvent.getEventName());
        if (updatedEvent.getEventDateTime() != null)
            existingEvent.setEventDateTime(updatedEvent.getEventDateTime());
        if (updatedEvent.getLocation() != null)
            existingEvent.setLocation(updatedEvent.getLocation());
        if (updatedEvent.getDescription() != null)
            existingEvent.setDescription(updatedEvent.getDescription());
        return existingEvent;
    }

    boolean isValid(EventEntity event) {
        return event != null &&
                event.getCreatorId() != null &&
                event.getEventDateTime() != null &&
                event.getEventName() != null &&
                event.getLocation() != null;
    }

    boolean isCurrentUserEventOwner(EventEntity event) {
        Long currentUserId = getUserId();
        return currentUserId.equals(event.getCreatorId());
    }

    public AttendanceEntity createAttendance(Long eventId) {
        EventEntity event = getEventById(eventId);
        UserEntity user = userRepository.findById(getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        AttendanceEntity existing = attendanceRepository.findByUserAndEvent(user, event);
        if(existing != null){
            return existing;
        }
        AttendanceEntity entity = new AttendanceEntity(user, event, java.time.LocalDateTime.now());
        return attendanceRepository.save(entity);
    }

    public List<EventEntity> getAttendancesByUserId(Long userId) {
        return attendanceRepository.findByUserId(userId)
                .stream()
                .map(AttendanceEntity::getEvent)
                .sorted(Comparator.comparing(EventEntity::getEventDateTime).reversed())
                .collect(Collectors.toList());
    }

    public EventEntity likeEvent(Long eventId) {
        EventEntity event = getEventById(eventId);
        UserEntity user = userRepository.findById(getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (likedEventRepository.existsByEvent_IdAndUser_Id(eventId, getUserId())) {
            return event;
        }
        LikedEventEntity liked = new LikedEventEntity(event, user);
        return likedEventRepository.save(liked).getEvent();
    }

    public void unlikeEvent(Long eventId) throws IllegalArgumentException {
        if (!likedEventRepository.existsByEvent_IdAndUser_Id(eventId, getUserId())) {
            throw new IllegalArgumentException();
        }
        LikedEventEntity liked = likedEventRepository.findByEvent_IdAndUser_Id(eventId, getUserId());
        likedEventRepository.delete(liked);
    }

    public List<EventEntity> getLikedEvents() {
        return likedEventRepository.findAllByUser_Id(getUserId())
                .stream()
                .map(LikedEventEntity::getEvent)
                .collect(Collectors.toList());
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserInfoDetails) authentication.getPrincipal()).getId();
    }


}
