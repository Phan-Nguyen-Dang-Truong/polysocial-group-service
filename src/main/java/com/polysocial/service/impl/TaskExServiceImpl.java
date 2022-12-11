package com.polysocial.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.polysocial.dto.TaskExDTO;
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

  
    
}
