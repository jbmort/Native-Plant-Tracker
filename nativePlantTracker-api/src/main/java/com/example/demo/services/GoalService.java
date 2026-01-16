package com.example.demo.services;

import com.example.demo.entities.Goal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoalService {

    List<Goal> allGoals();
}
