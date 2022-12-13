package com.polysocial.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.polysocial.dto.TaskExDTO;
import com.polysocial.dto.TaskExDetailDTO;
import com.polysocial.entity.FileSave;
import com.polysocial.entity.TaskEx;
import com.polysocial.repository.TaskExRepository;
import com.polysocial.service.TaskExService;

@Service
public class TaskExServiceImpl implements TaskExService {

    @Autowired
    private TaskExRepository taskExRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public TaskExDTO createTaskEx(TaskEx taskEx) {
        TaskExDTO taskExDTO = modelMapper.map(taskExRepository.save(taskEx), TaskExDTO.class);
        return taskExDTO;
    }

    @Override
    public TaskExDTO updateTaskEx(Long taskId, TaskEx taskExDTO) {
        taskExDTO.setTaskId(taskId);
        TaskExDTO taskEx = modelMapper.map(taskExRepository.save(taskExDTO), TaskExDTO.class);
        return taskEx;
    }

    @Override
    public void deleteTaskEx(Long taskId) {
        
    }

    @Override
    public TaskExDTO createMark(Float mark, Long exId, Long userId, Long groupId) {
        if(mark > 10 || mark <0) mark = 0F;
        taskExRepository.updateMark(mark, exId, userId, groupId);
        TaskEx task = taskExRepository.findByExIdAndUserIdAndGroupId(exId, userId, groupId);
        TaskExDTO taskExDTO = modelMapper.map(task, TaskExDTO.class);
        return taskExDTO;
    }

    @Override
    public List<TaskExDetailDTO> getAllTaskExByUserId(Long userId) {
        List<TaskEx> taskExs = taskExRepository.findByUserId(userId);
        List<TaskExDetailDTO> taskExDTOs = new ArrayList<>();
        for(int i = 0 ; i < taskExs.size(); i++) {
            TaskExDetailDTO taskExDTO = new TaskExDetailDTO(taskExs.get(i).getTaskId(), taskExs.get(i).getExercise().getExId(), userId,taskExs.get(i).getExercise().getGroup().getGroupId(), taskExs.get(0).getExercise().getContent(), taskExs.get(0).getExercise().getEndDate());
            taskExDTOs.add(taskExDTO);
        }
        return taskExDTOs;
    }

    @Override
    public List<TaskExDTO> getAllTaskExByEx(Long exId) {
        List<TaskEx> taskExs = taskExRepository.findByExerciseExId(exId);
        List<TaskExDTO> taskExDTOs = new ArrayList<>();
        for(int i = 0 ; i < taskExs.size(); i++) {
            TaskExDTO taskExDTO = modelMapper.map(taskExs.get(i), TaskExDTO.class);
            taskExDTOs.add(taskExDTO);
        }
        return taskExDTOs;
    }

  
    
}
