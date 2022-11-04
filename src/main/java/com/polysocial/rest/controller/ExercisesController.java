package com.polysocial.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polysocial.dto.ExercisesAPI;
import com.polysocial.entity.Exercises;
import com.polysocial.service.ExercisesService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
public class ExercisesController {

    @Autowired
    private ExercisesService exercisesService;

    @PostMapping(value = ExercisesAPI.API_CREATE_EXERCISES)
    public ResponseEntity createExercises(@RequestBody Exercises e) {
        try {
            Exercises exercises = exercisesService.createOne(e);
            return ResponseEntity.ok(exercises);
        } catch(Exception ex) {
            return null;
        }
    }

    @PutMapping(value=ExercisesAPI.API_UPDATE_EXERCISES)
    public ResponseEntity updateExercises(@RequestParam Long id, @RequestBody Exercises entity) {
        try {
            Exercises exercises = exercisesService.updateOne(id, entity);
            return ResponseEntity.ok(exercises);
        } catch(Exception ex) {
            return null;
        }        
    }

    @DeleteMapping(value=ExercisesAPI.API_DELETE_EXERCISES)
    public ResponseEntity deleteExercises(@RequestParam Long id) {
        try {
            Exercises exercises = exercisesService.deleteOne(id, false);
            return ResponseEntity.ok(exercises);
        } catch(Exception ex) {
            return null;
        }        
    }
}
