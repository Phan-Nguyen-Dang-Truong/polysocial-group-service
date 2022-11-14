package com.polysocial.rest.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.polysocial.consts.TaskAPI;
import com.polysocial.dto.TaskFileCreateDTO;
import com.polysocial.dto.TaskFileDTO;
import com.polysocial.service.TaskFileService;

@RestController
@CrossOrigin(origins = "*")
public class TaskController {


	@Autowired
	private TaskFileService taskFileService;


	@PostMapping(value = TaskAPI.API_TASK_FILE_CREATE, consumes = { "application/json" })
	public ResponseEntity createFile(@RequestBody TaskFileCreateDTO taskFile) throws IOException {
		try{
			return ResponseEntity.ok(taskFileService.saveFile(taskFile));
		}catch(Exception e){
			e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = TaskAPI.API_GET_FILE_UPLOAD_GROUP+"/{groupId}/{userId}/{exId}")
	public ResponseEntity getFileUploadGroup(@PathVariable Long groupId, @PathVariable Long userId, @PathVariable Long exId) {
		try{
			return ResponseEntity.ok(taskFileService.getFileUploadGroup(exId, userId, groupId));
		}catch(Exception e){
			e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = TaskAPI.API_TASK_EX_UPDATE, consumes = { "application/json" })
	public ResponseEntity updateFile(@RequestBody TaskFileCreateDTO taskFile) throws IOException {
		try{
			return ResponseEntity.ok(taskFileService.updateFile(taskFile));
		}catch(Exception e){
			e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
		}
	}


	@DeleteMapping(value = TaskAPI.API_DELETE_FILE_UPLOAD)
	public ResponseEntity deleteTaskFile(@RequestBody TaskFileDTO taskFile) {
		try{
			taskFileService.deleteTaskFile(taskFile);
			return ResponseEntity.ok().build();
		}catch(Exception e){
			e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
