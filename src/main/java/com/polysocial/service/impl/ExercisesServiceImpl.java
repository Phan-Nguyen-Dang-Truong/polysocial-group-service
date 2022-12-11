package com.polysocial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polysocial.dto.ExercisesDTO;
import com.polysocial.dto.TaskExDTO;
import com.polysocial.entity.Exercises;
import com.polysocial.entity.Members;
import com.polysocial.entity.TaskEx;
import com.polysocial.repository.ExercisesRepository;
import com.polysocial.repository.GroupRepository;
import com.polysocial.repository.MemberRepository;
import com.polysocial.repository.TaskExRepository;
import com.polysocial.service.ExercisesService;

@Service
public class ExercisesServiceImpl implements ExercisesService {

    @Autowired
    private ExercisesRepository exercisesRepo;

    @Autowired
    private GroupRepository groupRepo;

    @Autowired
    private TaskExRepository taskExRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberRepository memberRepo;

    @Override
    public ExercisesDTO createOne(ExercisesDTO ex) {
        Exercises exercise = modelMapper.map(ex, Exercises.class);
        exercise.setGroup(groupRepo.findById(ex.getGroupId()).get());
        List<Members> listMember = memberRepo.getMemberInGroup(exercise.getGroup().getGroupId());
        Exercises exercises = exercisesRepo.save(exercise);
        for(int i = 0; i< listMember.size();i++){
            TaskExDTO taskExDTO = new TaskExDTO(exercise.getExId(), listMember.get(i).getUserId(), exercise.getGroup().getGroupId());
            Members member = new Members(listMember.get(i).getUserId(), exercise.getGroup().getGroupId(), false, true);
            TaskEx taskEx = modelMapper.map(taskExDTO, TaskEx.class);
            taskEx.setMember(member);
            taskEx.setExercise(exercises);
            taskExRepo.save(taskEx);
        }
        ExercisesDTO exercisesDTO = modelMapper.map(exercises, ExercisesDTO.class);

        return exercisesDTO ;
    }

    @Override
    public ExercisesDTO updateOne (ExercisesDTO exercise) {
        Exercises exercises = modelMapper.map(exercise, Exercises.class);
        exercises.setExId(exercise.getExId());
        ExercisesDTO exercisesDTO = modelMapper.map(exercisesRepo.save(exercises), ExercisesDTO.class);
        return exercisesDTO ;
    }

    @Override
    public ExercisesDTO deleteOne(Long exId) {
        exercisesRepo.updatExercisesByStatuExercises(exId);
        return exercisesRepo.findById(exId).map(exercises -> {
            ExercisesDTO exercisesDTO = modelMapper.map(exercises, ExercisesDTO.class);
            return exercisesDTO;
        }).orElse(null);
    }

    @Override
    public List<ExercisesDTO> getAllExercisesEndDate(Long groupId) {
        List<Exercises> exercises =  exercisesRepo.getAllEndDate(groupId);
        List<ExercisesDTO> exercisesDTO = exercises.stream().map(exercise -> modelMapper.map(exercise, ExercisesDTO.class)).collect(Collectors.toList());
        return exercisesDTO;
    }

    @Override
    public List<ExercisesDTO> getAllExercises(Long groupId) {
        List<Exercises> exercises =  exercisesRepo.getAllExercises(groupId);
        List<ExercisesDTO> exercisesDTO = exercises.stream().map(exercise -> modelMapper.map(exercise, ExercisesDTO.class)).collect(Collectors.toList());
        return exercisesDTO;
    }
    
}
