package com.polysocial.service;

import com.polysocial.entity.Exercises;

public interface ExercisesService {
    
    Exercises createOne(Exercises exercise);

    Exercises updateOne(Long exId, Exercises exercise);

    Exercises deleteOne(Long exId, Boolean status);
}
