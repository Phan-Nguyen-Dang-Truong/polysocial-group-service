package com.polysocial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openxmlformats.schemas.drawingml.x2006.main.CTLineStyleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.polysocial.dto.GroupDTO;
import com.polysocial.dto.MemberDTO2;
import com.polysocial.dto.MemberGroupDTO;
import com.polysocial.dto.StudentDTO;
import com.polysocial.dto.UserDTO;
import com.polysocial.entity.Groups;
import com.polysocial.entity.Members;
import com.polysocial.repository.GroupRepository;
import com.polysocial.repository.MemberRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GroupTest {

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupRepository groupRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Test
	@Order(1)
	public void testGetAll() {
		Pageable pageable = PageRequest.of(0, 5); 
		Page<Groups> list = groupService.getAll(pageable);
		assertThat(list).size().isGreaterThan(0);
	}
	
	@Test
	@Order(2)
	public void testGetOne() {
		GroupDTO group = groupService.getOne(2L);
		assertEquals("Lập trinh web", group.getName());
	}
	
	@Test
	@Order(3)
	public void testCreate() {
		GroupDTO group = new GroupDTO();
		group.setName("Lop java test 22");
		group.setTotalMember(10L);
		group.setDescription("Test");
		group.setClassName("IT12345");
		group.setAvatar("anh.jpg");
		assertNotNull(groupRepo.findById(groupService.createGroup(group).getGroupId()).get());	
	}
	
	@Test
	@Order(4)
	public void testUpdateGroup() {
		GroupDTO group = new GroupDTO();
		group.setName("Lop test 2");
		group.setTotalMember(20L);
		group.setDescription("Test udpate");
		group.setGroupId(14L);
		groupService.updateGroup(group);
		assertNotEquals("Lop test", groupRepo.findById(14L).get().getClassName());
	}
	
	@Test
	@Order(5)
	public void testDeleteMemberToGroup() {
		Long groupId = 2L;
		Long userId = 5L;
		groupService.deleteMemberToGroup(groupId, userId);
		assertThat(groupRepo.existsById(2L)).isFalse();
	}
	
	@Test
	@Order(6)
	public void testDeleteGroup() {
		Long groupId = 7L;
		groupService.deleteGroup(groupId);
		assertEquals(false, groupRepo.findById(groupId).get().getStatus());
	}

	
	@Test
	@Order(7)
	public void testGetTeacherFromGroup() {
		Long groupId = 2L;
		UserDTO user = groupService.getTeacherFromGroup(groupId);
		assertNotNull(user.getUserId());
	} 
	
	@Test
	@Order(8)
	public void testGetMemberInGroup() {
		 Long groupId = 2L;
		 List<UserDTO> list = groupService.getMemberInGroup(groupId);
		 assertThat(list).size().isGreaterThan(0);
	}
	
	@Test
	public void testSaveMember() {
		StudentDTO user = new StudentDTO();
		user.setGroupId(2L);
		user.setUserId(7L);
		Long memberId = groupService.saveMember(user).getUserId();
		assertNotNull(memberRepo.findById(memberId));
		
	}
	
	@Test
	@Order(9)
	public void testGetAllGroupByStudent() {
		List<MemberGroupDTO> list = groupService.getAllGroupByStudent(2L);
		assertThat(list).size().isGreaterThan(0);
	}
	
	@Test
	@Order(10)
	public void testGetAllGroupByTeacher() {
		List<MemberGroupDTO> list = groupService.getAllGroupByTeacher(1L);
		assertThat(list).size().isGreaterThan(0);
	}
	
	@Test
	@Order(11)
	public void testGetAllMemberJoinGroupFalse() {
		List<MemberDTO2> list = groupService.getAllMemberJoinGroupFalse(2L);
		assertEquals(list.get(0).getConfirm(), false);
	}
	
	@Test
	@Order(12)
	public void testConfirmOneMemberGroup() {
		Long memberId = groupService.confirmOneMemberGroup(2L, 5L).getUserId();
		assertEquals(memberRepo.findById(memberId).get().getConfirm(), true);
	}
	
	@Test
	@Order(13)
	public void testConfirmAllMemberGroup() {
		List<Members> list = groupService.confirmAllMemberGroup(2L);
		assertEquals(list.get(0).getConfirm(), true);
	}
	
	@Test
	@Order(14)
	public void testMemberLeaveGroup() {
		groupService.memberLeaveGroup(2L,2L);
		assertNull(memberRepo.getOneMemberInGroup(2L, 2L));
	}
	
	@Test
	@Order(15)
	public void testGetAllGroupByUser() {
		List<MemberGroupDTO> list = groupService.getAllGroupByUser(1L);
		assertThat(list).size().isGreaterThan(0);
	}

}
