package com.example.zpi.controllers;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.EventEntity;
import com.example.zpi.entities.UserEntity;
import com.example.zpi.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    final private EventService eventService;

    public AttendanceController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/history")
    public ResponseEntity<List<EventEntity>> getAttendanceHistory(@RequestParam(required = true) Long userId) {
        return ResponseEntity.ok(eventService.getAttendancesByUserId(userId));
    }

}
