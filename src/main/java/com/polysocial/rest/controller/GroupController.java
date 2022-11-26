package com.polysocial.rest.controller;

import com.polysocial.consts.GroupAPI;
import com.polysocial.dto.StudentDTO;
import com.polysocial.dto.GroupDTO;
import com.polysocial.dto.MemberDTO;
import com.polysocial.dto.MemberDTO2;
import com.polysocial.dto.MemberGroupDTO;
import com.polysocial.dto.UserDTO;
import com.polysocial.entity.Groups;
import com.polysocial.entity.Members;

import com.polysocial.service.impl.GroupServiceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class GroupController {
	
    @Autowired
	private GroupServiceImpl groupBusiness = new GroupServiceImpl();

	
    @GetMapping(GroupAPI.API_GET_ALL_GROUP)
	@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page> getAll(@RequestParam Integer page, @RequestParam Integer limit){
    	try {
    		Pageable pageable = PageRequest.of(page, limit);
    		Page<Groups> list = groupBusiness.getAll(pageable);
    		return ResponseEntity.ok(list);
    	}catch(Exception e) {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
    	}
    	
    }
    
    @GetMapping(GroupAPI.API_GET_ONE_GROUP)
    public ResponseEntity getOne(@RequestParam Long groupId){
    	try {
    		GroupDTO group = groupBusiness.getOne(groupId);
    		return ResponseEntity.ok(group);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
    	}
    	
    }
    
    @PostMapping(GroupAPI.API_CREATE_GROUP)
    public ResponseEntity createGroup(@RequestBody GroupDTO group) {
    	try {
    		Object o =  groupBusiness.createGroup(group);
    		return ResponseEntity.ok(o);
    	}catch(Exception e) {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
    	}	
    	
    }
    
    @PutMapping(GroupAPI.API_UPDATE_GROUP)
	public ResponseEntity updateGroup(@RequestBody GroupDTO group) {
		try {
			if(group.getGroupId() == null) return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

			Object o = groupBusiness.createGroup(group);
			return ResponseEntity.ok(o);
		}catch(Exception e) {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
		}
	}
    
    @GetMapping(GroupAPI.API_GET_TEACHER)
    public ResponseEntity getTeacherFromGroup(@RequestParam Long groupId) {
    	try{
    		Object object = groupBusiness.getTeacherFromGroup(groupId);
    		return ResponseEntity.ok(object);
    	}catch(Exception e) {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
    	}
    }
    
    @GetMapping(GroupAPI.API_GET_ALL_STUDENT)
    public ResponseEntity getStudentByIdClass(@RequestParam Long groupId){
    	try {
    		List<UserDTO> list = groupBusiness.getMemberInGroup(groupId);
    		return ResponseEntity.ok(list);
    	}catch(Exception e) {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
    	}
    	
    }
    
    @GetMapping(GroupAPI.API_GET_STUDENT)
    public ResponseEntity getUserInGroup(@RequestParam String email, @RequestParam Long groupId ) {
    	try {
    		UserDTO user =  groupBusiness.getOneMemberInGroup(email, groupId);
    		return ResponseEntity.ok(user);
    	}catch(Exception e) {
			e.printStackTrace();
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
    	}
    }
    
    @PostMapping(GroupAPI.API_CREATE_STUDENT)
    public ResponseEntity createStudentGroup(@RequestBody StudentDTO user) {
    	try {
    		MemberDTO member = groupBusiness.saveMember(user);
    		return ResponseEntity.ok(member);
    	}catch(Exception e) {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

    	}
    }
    
    @GetMapping(GroupAPI.API_FIND_GROUP)
    public ResponseEntity findGroup(@RequestParam(required = false, name="keywork")	 String keyword){
    	try {
    		List<GroupDTO> list = groupBusiness.findByKeywork(keyword);
    		return ResponseEntity.ok(list);
    	}catch(Exception e) {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

    	}
    }
    
	@DeleteMapping(value = GroupAPI.API_DELETE_GROUP)	
	public ResponseEntity deleteGroup(@RequestParam Long groupId) {
		try {
			GroupDTO groups = groupBusiness.deleteGroup(groupId);
			return ResponseEntity.ok(groups);
		}catch(Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}
    
    @DeleteMapping(GroupAPI.API_REMOVE_STUDENT)
    public ResponseEntity removeStudentToClass(@RequestParam Long groupId, @RequestParam Long userId) {
    	try {
        	groupBusiness.deleteMemberToGroup(groupId, userId);
        	return ResponseEntity.ok("OK");
    	}catch(Exception e) {
			e.printStackTrace();
    		return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

    	}
    }	
    
	@RequestMapping(value = GroupAPI.API_CREATE_GROUP_EXCEL, method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity uploadFile(@RequestParam MultipartFile file, @RequestParam Long groupId, @RequestParam Long teacherId) 
			throws IOException {
		try {
			List<MemberDTO> list = groupBusiness.createExcel(file, groupId, teacherId);
			return ResponseEntity.ok(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping(GroupAPI.API_GET_ALL_GROUP_STUDENT)
	public ResponseEntity getAllGroupStudent(@RequestParam Long userId) {
		try {
			List<MemberGroupDTO> list = groupBusiness.getAllGroupByStudent(userId);
			return ResponseEntity.ok(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}
	
	@GetMapping(GroupAPI.API_GET_ALL_GROUP_TEACHER)
	public ResponseEntity getAllGroupTeacher(@RequestParam Long userId) {
		try {
			List<MemberGroupDTO> list = groupBusiness.getAllGroupByTeacher(userId);
			return ResponseEntity.ok(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping(GroupAPI.API_GET_ALL_GROUP_FALSE)
	public ResponseEntity getAllGroupFalse(@RequestParam Integer page, @RequestParam Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			Page<Groups> list = groupBusiness.getAllGroupFalse(pageable);
			return ResponseEntity.ok(list);
		}catch(Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}
	
	@PostMapping(GroupAPI.API_MEMBER_JOIN_GROUP)
	public ResponseEntity memberJoinGroup(@RequestParam Long groupId, @RequestParam Long userId) {
		try {
			MemberDTO memberDTO = groupBusiness.memberJoinGroup(groupId, userId);
			return ResponseEntity.ok(memberDTO);
		}catch(Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping(GroupAPI.API_MEMBER_JOIN_GROUP_FALSE)
	public ResponseEntity getAllMemberGroupFalse(@RequestParam Long groupId) {
		try {
			List<MemberDTO2> list = groupBusiness.getAllMemberJoinGroupFalse(groupId);
			return ResponseEntity.ok(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping(GroupAPI.API_CONFIRM_MEMBER_GROUP)
	public ResponseEntity confirmMemberGroup(@RequestParam Long groupId, @RequestParam Long userId) {
		try {
			UserDTO userDTO = groupBusiness.confirmOneMemberGroup(groupId, userId);
			return ResponseEntity.ok(userDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping(GroupAPI.API_CONFIRM_ALL_MEMBER_GROUP)
	public ResponseEntity confirmAllMemberGroup(@RequestParam Long groupId) {
		try {
			List<Members> list = groupBusiness.confirmAllMemberGroup(groupId);
			return ResponseEntity.ok(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}

	@DeleteMapping(GroupAPI.API_LEAVE_GROUP)
	public ResponseEntity leaveGroup(@RequestParam Long groupId, @RequestParam Long userId) {
		try {
			groupBusiness.memberLeaveGroup(groupId, userId);
			return ResponseEntity.ok("OK");
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);

		}
	}
}
