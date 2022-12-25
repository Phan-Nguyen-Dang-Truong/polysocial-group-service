package com.polysocial.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polysocial.consts.ExercisesAPI;
import com.polysocial.dto.ExercisesDTO;
import com.polysocial.service.ExercisesService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = "*")
public class ExercisesController {

    @Autowired
    private ExercisesService exercisesService;

    @PostMapping(value = ExercisesAPI.API_CREATE_EXERCISES, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createExercises(@RequestBody(required = false) ExercisesDTO exercise) {
        try {
            exercise.formatEndDate();
            ExercisesDTO exercises = exercisesService.createOne(exercise);
            return ResponseEntity.ok(exercises);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = ExercisesAPI.API_UPDATE_EXERCISES)
    public ResponseEntity updateExercises(@RequestBody ExercisesDTO entity) {
        try {
            entity.formatEndDate();
            ExercisesDTO exercises = exercisesService.updateOne(entity);
            return ResponseEntity.ok(exercises);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = ExercisesAPI.API_DELETE_EXERCISES)
    public ResponseEntity deleteExercises(@RequestParam Long exId) {
        try {
            exercisesService.deleteOne(exId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ExercisesAPI.API_GET_ALL_EXERCISES_END_DATE)
    public ResponseEntity getAllExercisesEndDate(@RequestParam Long groupId) {
        try {
            return ResponseEntity.ok(exercisesService.getAllExercisesEndDate(groupId));
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ExercisesAPI.API_GET_ALL_EXERCISES)
    public ResponseEntity getAllExercises(@RequestParam Long groupId) {
        try {
            return ResponseEntity.ok(exercisesService.getAllExercises(groupId));
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ExercisesAPI.API_GET_ONE_EXERCISES)
    public ResponseEntity getOneExercises(@RequestParam Long exId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(exercisesService.getOneExercises(exId, userId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = ExercisesAPI.API_CHECK_END_DATE)
    public ResponseEntity checkEndDate() {
        try {
            return ResponseEntity.ok(exercisesService.checkEndDate());
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ExercisesAPI.API_SEND_NOTI_DEADLINE)
    public ResponseEntity sendNotiDeadline() {
        try {
            return ResponseEntity.ok(exercisesService.sendNotiDeadline());
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
        }
    }

}
