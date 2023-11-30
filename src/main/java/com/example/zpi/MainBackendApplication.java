package com.example.zpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainBackendApplication {

  private static final Logger log = LoggerFactory.getLogger(MainBackendApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(MainBackendApplication.class);
  }

  // Bean that fills db with sample data
  // @Bean
  // public CommandLineRunner demo(CustomerRepository repository) {
  //   return (args) -> {
  //     // save sample customers
  //     repository.save(new Customer("Jack", "Bauer"));
  //     repository.save(new Customer("Chloe", "O'Brian"));
  //     repository.save(new Customer("Kim", "Bauer"));
  //     repository.save(new Customer("David", "Palmer"));
  //     repository.save(new Customer("Michelle", "Dessler"));
  //   };
  //   log.info("...")
  // }

}