package com.polysocial.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polysocial.entity.Exercises;
import com.polysocial.repository.ExercisesRepo;
import com.polysocial.service.ExercisesService;

@Service
public class ExercisesServiceImpl implements ExercisesService {

    @Autowired
    private ExercisesRepo exercisesRepo;

    @Override
    public Exercises createOne(Exercises exercise) {
        Exercises exercises = exercisesRepo.save(exercise);
        return exercises;
    }

    @Override
    public Exercises updateOne(Long exId, Exercises exercise) {
        // Exercises exercises = exercisesRepo.updatExercisesByExercises(exId, exercise);
        // return exercises;
        return null;
    }

    @Override
    public Exercises deleteOne(Long exId, Boolean status) {
        // Exercises exercises = exercisesRepo.updatExercisesByStatuExercises(exId, status);
        // return exercises;
        return null;
    }
    
}
