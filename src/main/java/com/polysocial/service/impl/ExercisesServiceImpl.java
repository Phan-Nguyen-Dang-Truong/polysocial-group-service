package com.polysocial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polysocial.dto.ExercisesDTO;
import com.polysocial.entity.Exercises;
import com.polysocial.repository.ExercisesRepo;
import com.polysocial.repository.GroupRepository;
import com.polysocial.service.ExercisesService;

@Service
public class ExercisesServiceImpl implements ExercisesService {

    @Autowired
    private ExercisesRepo exercisesRepo;

    @Autowired
    private GroupRepository groupRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ExercisesDTO createOne(Exercises exercise, Long groupId) {
        exercise.setGroup(groupRepo.findById(groupId).get());
        Exercises exercises = exercisesRepo.save(exercise);
        ExercisesDTO exercisesDTO = modelMapper.map(exercises, ExercisesDTO.class);
        return exercisesDTO ;
    }

    @Override
    public ExercisesDTO updateOne(Long exId, Exercises exercise) {
        exercise.setExId(exId);
        Exercises exercises = exercisesRepo.save(exercise);
        ExercisesDTO exercisesDTO = modelMapper.map(exercises, ExercisesDTO.class);
        return exercisesDTO ;
    }

    @Override
    public ExercisesDTO deleteOne(Long exId) {
        exercisesRepo.updatExercisesByStatuExercises(exId);
        return null;
    }

    @Override
    public List<ExercisesDTO> getAllExercisesEndDate(Long groupId) {
        List<Exercises> exercises =  exercisesRepo.getAllEndDate(groupId);
        List<ExercisesDTO> exercisesDTO = exercises.stream().map(exercise -> modelMapper.map(exercise, ExercisesDTO.class)).collect(Collectors.toList());
        return exercisesDTO;
    }
    
}
