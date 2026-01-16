package com.example.demo.services;

import com.example.demo.entities.Goal;
import com.example.demo.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    final GoalRepository goalRepository;

    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> allGoals(){
        return goalRepository.findAll();
    }
}
