package com.example.zpi;

import com.example.zpi.entities.AttendanceEntity;
import com.example.zpi.entities.EventEntity;
import com.example.zpi.entities.UserEntity;
import com.example.zpi.repositories.AttendanceRepository;
import com.example.zpi.repositories.EventRepository;
import com.example.zpi.repositories.UserRepository;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class MainBackendApplication {

  // private static final Logger log = LoggerFactory.getLogger(MainBackendApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(MainBackendApplication.class);
  }


   @Bean
   public CommandLineRunner demo(AttendanceRepository attendanceRepo,
                                 EventRepository eventRepo,
                                 UserRepository userRepo) {
     return (args) -> {
         UserEntity user1 = new UserEntity("user1@exampl.com", "passwd", "ROLE_USER", 1000l);
         user1 = userRepo.save(user1);
         EventEntity event1 = new EventEntity(1L,"Event 1", LocalDateTime.now(), "Place 1");
         event1 = eventRepo.save(event1);
         EventEntity event2 = new EventEntity(2L,"Event 2", LocalDateTime.now(), "Place 2");
         event2 = eventRepo.save(event2);
         AttendanceEntity attendance1 = new AttendanceEntity(user1, event1, LocalDateTime.now());
         attendance1 = attendanceRepo.save(attendance1);
     };
   }
}