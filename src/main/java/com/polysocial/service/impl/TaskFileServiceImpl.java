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
	public void deleteTaskFile(Long taskFileId) {
		taskFileRepository.deleteById(taskFileId);
	}

	@Override
	public TaskFile saveFile(MultipartFile file, Long exId, Long userId, Long groupId) {
		try {
			String url = this.saveFile(file);
			TaskEx taskEx = taskExRepository.findByExIdAndUserIdAndGroupId(exId, userId, groupId);
			String type = url.substring(url.lastIndexOf(".") + 1);
			TaskFile taskFile = new TaskFile(url, type, taskEx);
			return taskFileRepository.save(taskFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String saveFile(MultipartFile file) throws IOException {
		String url = "";
		File folder = new File("Files");
		try {
			folder.mkdir();
			saveLocal(file);
			url = upLoadServer(file);
			deleteFile();
		} catch (Exception e) {
			folder.delete();
			e.printStackTrace();
			return url;
		}
		return url;
	}

	public void saveLocal(MultipartFile fi) throws IOException {
		FileUploadUtil.saveFile("polysocial.zip", fi);
	}

	public String upLoadServer(MultipartFile fi) {
		int firtsIndex = 0;
		int lastIndex = 0;
		String url = "";
		try {
			String json = "" + this.cloudinary.uploader().upload("./Files/" + "polysocial.zip",
					ObjectUtils.asMap("resource_type", "auto"));
			firtsIndex = json.indexOf("url=");
			lastIndex = json.indexOf("created_at");
			url = json.substring(firtsIndex + 4, lastIndex - 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}

	void deleteFile() {
		Path filePath = Paths.get("Files");
		if (filePath.toFile().exists()) {
			File[] files = filePath.toFile().listFiles();
			for (File file : files) {
				file.delete();
			}
		}
	}

	@Override
	public TaskFile getFileUploadGroup(Long exId, Long userId, Long groupId) {
		Long taskExId = taskExRepository.findByExIdAndUserIdAndGroupId(exId, userId, groupId).getTaskId();
		System.out.println(taskExId);
		TaskFile taskFile = taskFileRepository.findByTaskEx(taskExId);
		return taskFile;
	}

	@Override
	public TaskFile updateFile(MultipartFile file, Long exId, Long userId, Long groupId) {
		try {
			String url = this.saveFile(file);
			TaskEx taskEx = taskExRepository.findByExIdAndUserIdAndGroupId(exId, userId, groupId);
			String type = url.substring(url.lastIndexOf(".") + 1);
			TaskFile taskFile = new TaskFile(url, type, taskEx);
			Long idTaskFile = taskFileRepository.findByTaskEx(taskEx.getTaskId()).getTaskFileId();
			taskFile.setTaskFileId(idTaskFile);
			return taskFileRepository.save(taskFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
