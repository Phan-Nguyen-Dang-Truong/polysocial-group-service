package com.polysocial.rest.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.polysocial.consts.TaskAPI;
import com.polysocial.service.TaskFileService;

@RestController
@CrossOrigin(origins = "*")
public class TaskController {


	@Autowired
	private TaskFileService taskFileService;


	@PostMapping(value = TaskAPI.API_TASK_FILE_CREATE)
	public ResponseEntity createFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam Long exId, @RequestParam Long userId, @RequestParam Long groupId) throws IOException {
		try{
			return ResponseEntity.ok(taskFileService.saveFile(file, exId, userId, groupId));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping(value = TaskAPI.API_GET_FILE_UPLOAD_GROUP)
	public ResponseEntity getFileUploadGroup(@RequestParam Long exId, @RequestParam Long userId, @RequestParam Long groupId) {
		try{
			return ResponseEntity.ok(taskFileService.getFileUploadGroup(exId, userId, groupId));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@PutMapping(value = TaskAPI.API_TASK_EX_UPDATE)
	public ResponseEntity updateTaskFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam Long exId, @RequestParam Long userId, @RequestParam Long groupId) {
		try{
			return ResponseEntity.ok(taskFileService.updateFile(file, exId, userId, groupId));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@DeleteMapping(value = TaskAPI.API_DELETE_FILE_UPLOAD)
	public ResponseEntity deleteTaskFile(@RequestParam Long taskFileId) {
		try{
			taskFileService.deleteTaskFile(taskFileId);
			return ResponseEntity.ok().build();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
