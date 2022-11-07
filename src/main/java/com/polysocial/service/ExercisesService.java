package com.polysocial.service;

import java.util.List;

import com.polysocial.dto.ExercisesDTO;
import com.polysocial.entity.Exercises;

public interface ExercisesService {
    
    ExercisesDTO createOne(Exercises exercise, Long groupId);

    ExercisesDTO updateOne(Long exId, Exercises exercise);

    ExercisesDTO deleteOne(Long exId);

    List<ExercisesDTO> getAllExercisesEndDate(Long groupId);

}
