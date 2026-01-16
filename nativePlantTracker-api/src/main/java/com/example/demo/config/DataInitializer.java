package com.example.demo.config;

import com.example.demo.entities.Goal;
import com.example.demo.repository.GoalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final GoalRepository goalRepository;

    public DataInitializer(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Running Data Initializer ---");

        // Initialize Data
        //
         // set goal options
        if (goalRepository.count() == 0) {
            System.out.println("Goals table is empty. Seeding initial data...");

            Goal goal1 = new Goal();
            goal1.setName("Attract Pollinators");

            Goal goal11 = new Goal();
            goal11.setName("Butterfly Garden");

            Goal goal2 = new Goal();
            goal2.setName("Support Birds");

            Goal goal3 = new Goal();
            goal3.setName("Early Spring Blooms");

            Goal goal4 = new Goal();
            goal4.setName("Late Fall Color");

            Goal goal5 = new Goal();
            goal5.setName("Low Maintenance / Drought Tolerant");

            Goal goal6 = new Goal();
            goal6.setName("Edible Plants");

            goalRepository.saveAll(List.of(goal1, goal11, goal2, goal3, goal4, goal5, goal6));

            System.out.println("Finished seeding " + goalRepository.count() + " goals.");
        } else {
            System.out.println("Goals table already contains data. Skipping seed process.");
        }


    }
}
