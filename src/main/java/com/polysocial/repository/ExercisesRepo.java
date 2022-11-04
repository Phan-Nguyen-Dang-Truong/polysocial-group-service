package com.polysocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polysocial.entity.Exercises;


@Repository
public interface ExercisesRepo extends JpaRepository<Exercises, Long> {

    // Exercises updatExercisesByStatuExercises(Long exId, Boolean status);

    // Exercises updatExercisesByExercises(Long exId, Exercises exercise);
}
