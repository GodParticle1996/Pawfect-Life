package com.example.pawfectlife;

import com.example.pawfectlife.model.User;
import com.example.pawfectlife.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.pawfectlife.repository")
@EntityScan("com.example.pawfectlife.model")
@ComponentScan("com.example.pawfectlife")
public class PawfectLifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PawfectLifeApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdmin(UserService userService) {
        return args -> {
            String adminEmail = "admin@pawfectlife.com";
            String adminPassword = "admin123";
            
            if (userService.findByEmail(adminEmail) == null) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(adminPassword);
                admin.setRole(User.Role.ADMIN);
                
                userService.registerUser(admin);
                System.out.println("\n=========================================");
                System.out.println("Admin account has been created.:");
                System.out.println("Email: " + adminEmail);
                System.out.println("Password: " + adminPassword);
                System.out.println("=========================================\n");
            }
        };
    }
}