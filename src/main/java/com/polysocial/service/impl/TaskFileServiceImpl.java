package com.polysocial.service.impl;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.polysocial.dto.TaskFileCreateDTO;
import com.polysocial.dto.TaskFileDTO;
import com.polysocial.entity.TaskEx;
import com.polysocial.entity.TaskFile;
import com.polysocial.repository.TaskExRepository;
import com.polysocial.repository.TaskFileRepository;
import com.polysocial.service.FileUploadUtil;
import com.polysocial.service.TaskFileService;

@Service
public class TaskFileServiceImpl implements TaskFileService {

	@Autowired
	private TaskFileRepository taskFileRepository;

	@Autowired
	private TaskExRepository taskExRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Cloudinary cloudinary;

	@Override
	public TaskFileDTO createTaskFile(TaskFile taskFile) {
		TaskFileDTO taskFileDTO = modelMapper.map(taskFileRepository.save(taskFile), TaskFileDTO.class);
		return taskFileDTO;
	}

	@Override
	public void deleteTaskFile(TaskFileDTO taskFileId) {
		taskFileRepository.deleteById(taskFileId.getTaskFileId());
	}

	@Override
	public TaskFile saveFile(TaskFileCreateDTO taskFile) {
		String url = taskFile.getPath();
		TaskEx taskEx = taskExRepository.findByExIdAndUserIdAndGroupId(taskFile.getExId(), taskFile.getUserId(), taskFile.getGroupId());
		String type = url.substring(url.lastIndexOf(".") + 1);
		TaskFile taskFiles = new TaskFile(url, type, taskEx);
		System.out.println(taskFiles.getTask().getTaskId());
		return taskFileRepository.save(taskFiles);
	}

	@Override
	public TaskFile getFileUploadGroup(Long exId, Long userId, Long groupId) {
		Long taskExId = taskExRepository.findByExIdAndUserIdAndGroupId(exId, userId, groupId).getTaskId();
		TaskFile taskFile = taskFileRepository.findByTaskEx(taskExId);
		return taskFile;
	}

	@Override
	public TaskFile updateFile(TaskFileCreateDTO taskFile) {
		String url = taskFile.getPath();
		TaskEx taskEx = taskExRepository.findByExIdAndUserIdAndGroupId(taskFile.getExId(), taskFile.getUserId(), taskFile.getGroupId());
		String type = url.substring(url.lastIndexOf(".") + 1);
		TaskFile taskFiles = new TaskFile(url, type, taskEx);
		Long idTaskFile = taskFileRepository.findByTaskEx(taskEx.getTaskId()).getTaskFileId();
		taskFiles.setTaskFileId(idTaskFile);
		return taskFileRepository.save(taskFiles);
	}
}
