package com.polysocial.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.polysocial.dto.ExercisesDTO;
import com.polysocial.entity.Exercises;


@Repository
public interface ExercisesRepository extends JpaRepository<Exercises, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Exercises e SET e.status = 0 WHERE e.exId = ?1")
    void updatExercisesByStatuExercises(Long exId);

    @Query("SELECT e FROM Exercises e WHERE e.status = 0 and e.group.groupId = ?1")
    List<Exercises> getAllEndDate(Long groupId);
}
