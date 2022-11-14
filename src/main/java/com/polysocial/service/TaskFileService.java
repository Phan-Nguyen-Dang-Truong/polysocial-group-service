package com.polysocial.service;

import org.springframework.web.multipart.MultipartFile;

import com.polysocial.dto.TaskFileCreateDTO;
import com.polysocial.dto.TaskFileDTO;
import com.polysocial.entity.TaskFile;

public interface TaskFileService {
    
    TaskFileDTO createTaskFile(TaskFile taskFile);

    void deleteTaskFile(TaskFileDTO taskFileId);

    TaskFile saveFile(TaskFileCreateDTO taskFile);

    TaskFile updateFile(TaskFileCreateDTO taskFile);

    TaskFile getFileUploadGroup(Long exId, Long userId, Long groupId);

}
