package com.polysocial.service;

import java.util.List;

import com.polysocial.dto.ExercisesDTO;
import com.polysocial.dto.ExercisesDetailDTO;
import com.polysocial.entity.Exercises;

public interface ExercisesService {
    
    ExercisesDTO createOne(ExercisesDTO exercise);

    ExercisesDTO updateOne(ExercisesDTO exercise);

    void deleteOne(Long exId);

    List<ExercisesDTO> getAllExercisesEndDate(Long groupId);

    List<ExercisesDTO> getAllExercises(Long groupId);

    ExercisesDetailDTO getOneExercises(Long exId, Long userId);

}
