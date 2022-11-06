package com.polysocial.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polysocial.consts.ExercisesAPI;
import com.polysocial.dto.ExercisesDTO;
import com.polysocial.entity.Exercises;
import com.polysocial.service.ExercisesService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ExercisesController {

    @Autowired
    private ExercisesService exercisesService;

    @PostMapping(value = ExercisesAPI.API_CREATE_EXERCISES)
    public ResponseEntity createExercises(@RequestBody Exercises e, @RequestParam Long groupId) {
        try {
            ExercisesDTO exercises = exercisesService.createOne(e, groupId);
            return ResponseEntity.ok(exercises);
        } catch(Exception ex) {
            return null;
        }
    }

    @PutMapping(value=ExercisesAPI.API_UPDATE_EXERCISES)
    public ResponseEntity updateExercises(@RequestParam Long exId, @RequestBody Exercises entity) {
        try {
            ExercisesDTO exercises = exercisesService.updateOne(exId, entity);
            return ResponseEntity.ok(exercises);
        } catch(Exception ex) {
            return null;
        }        
    }

    @DeleteMapping(value=ExercisesAPI.API_DELETE_EXERCISES)
    public ResponseEntity deleteExercises(@RequestParam Long exId) {
        try {
            ExercisesDTO exercises = exercisesService.deleteOne(exId);
            return ResponseEntity.ok(exercises);
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }        
    }

    @GetMapping(value=ExercisesAPI.API_GET_ALL_EXERCISES_END_DATE)
    public ResponseEntity getAllExercisesEndDate(@RequestParam Long groupId) {
        try {
            return ResponseEntity.ok(exercisesService.getAllExercisesEndDate(groupId));
        } catch(Exception ex) {
            return null;
        }        
    }

}
