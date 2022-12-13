package com.polysocial.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudinary.Cloudinary;
import com.polysocial.dto.TaskDetailDTO;
import com.polysocial.dto.TaskFileCreateDTO;
import com.polysocial.dto.TaskFileDTO;
import com.polysocial.entity.Exercises;
import com.polysocial.entity.TaskEx;
import com.polysocial.entity.TaskFile;
import com.polysocial.entity.Users;
import com.polysocial.repository.ExercisesRepository;
import com.polysocial.repository.TaskExRepository;
import com.polysocial.repository.TaskFileRepository;
import com.polysocial.repository.UserRepository;
import com.polysocial.service.TaskFileService;

@Service
public class TaskFileServiceImpl implements TaskFileService {

	@Autowired
	private TaskFileRepository taskFileRepository;

	@Autowired
	private TaskExRepository taskExRepository;

	@Autowired
	private ExercisesRepository exercisesRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Cloudinary cloudinary;

	@Autowired
	private UserRepository userRepo;

	@Override
	public TaskFileDTO createTaskFile(TaskFile taskFile) {
		TaskFileDTO taskFileDTO = modelMapper.map(taskFileRepository.save(taskFile), TaskFileDTO.class);
		return taskFileDTO;
	}

	@Override
	public void deleteTaskFile(Long taskFileId) {
		taskFileRepository.deleteById(taskFileId);
	}

	@Override
	public TaskFile saveFile(TaskFileCreateDTO taskFile) {
		String url = taskFile.getPath();
		TaskEx taskEx = taskExRepository.findByExIdAndUserIdAndGroupId(taskFile.getExId(), taskFile.getUserId(), taskFile.getGroupId());
		TaskFile task_file = taskFileRepository.findByTaskEx(taskEx.getTaskId()) ;
		if(task_file != null )  return null;
		String type = url.substring(url.lastIndexOf(".") + 1);
		TaskFile taskFiles = new TaskFile(url, type, taskEx);
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
		System.out.println("123");
		String url = taskFile.getPath();
		System.out.println(taskFile.getExId() +"-"+ taskFile.getUserId() +"-"+ taskFile.getGroupId());
		TaskEx taskEx = taskExRepository.findByExIdAndUserIdAndGroupId(taskFile.getExId(), taskFile.getUserId(), taskFile.getGroupId());
		String type = url.substring(url.lastIndexOf(".") + 1);
		TaskFile taskFiles = new TaskFile(url, type, taskEx);
		Long idTaskFile = taskFileRepository.findByTaskEx(taskEx.getTaskId()).getTaskFileId();
		System.out.println(idTaskFile);
		taskFiles.setTaskFileId(idTaskFile);
		return taskFileRepository.save(taskFiles);
	}

	@Override
	public List<TaskDetailDTO> getAllTaskFile(Long exId, Long groupId) {
		List<TaskEx> list = taskExRepository.findByExIdAndGroupId(exId, groupId);
		List<TaskDetailDTO> listTaskDetailDTO = new ArrayList<>();
		for (TaskEx taskEx : list) {
			Users user = userRepo.findById(taskEx.getMember().getUserId()).get();
			Exercises exercise = exercisesRepository.findById(exId).get();
			TaskDetailDTO taskDetailDTO = new TaskDetailDTO();
			taskDetailDTO.setTaskId(taskEx.getTaskId());
			taskDetailDTO.setUserId(user.getUserId());
			taskDetailDTO.setGroupId(taskEx.getMember().getGroupId());
			taskDetailDTO.setExId(taskEx.getExercise().getExId());
			taskDetailDTO.setAvatar(user.getAvatar());
			taskDetailDTO.setFullName(user.getFullName());
			taskDetailDTO.setContent(exercise.getContent());
			taskDetailDTO.setEndDate(exercise.getEndDate());
			taskDetailDTO.setCreatedDate(taskEx.getCreatedDate());
			taskDetailDTO.setMark(taskEx.getMark());
			try{
				String url = taskFileRepository.findByTaskEx(taskEx.getTaskId()).getUrl();
				taskDetailDTO.setUrl(url);
			}catch(Exception e){
				continue;
			}
			listTaskDetailDTO.add(taskDetailDTO);
		}
		return listTaskDetailDTO;
	}
}
