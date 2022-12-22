package com.polysocial.service.impl;

import com.polysocial.dto.StudentDTO;
import com.polysocial.dto.ContactDTO;
import com.polysocial.dto.GroupDTO;
import com.polysocial.dto.MemberDTO;
import com.polysocial.dto.MemberDTO2;
import com.polysocial.dto.MemberGroupDTO;
import com.polysocial.dto.UserDTO;
import com.polysocial.entity.Book;
import com.polysocial.entity.Contacts;
import com.polysocial.entity.Groups;
import com.polysocial.entity.Members;
import com.polysocial.entity.Messages;
import com.polysocial.entity.RoomChats;
import com.polysocial.entity.Users;
import com.polysocial.entity.ViewedStatus;
import com.polysocial.repository.ContactRepository;
import com.polysocial.repository.GroupRepository;
import com.polysocial.repository.MemberRepository;
import com.polysocial.repository.MessageRepo;
import com.polysocial.repository.RoomChatRepository;
import com.polysocial.repository.UserRepository;
import com.polysocial.repository.ViewedStatusRepo;
import com.polysocial.service.ExcelService;
import com.polysocial.service.FileUploadUtil;
import com.polysocial.service.GroupService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GroupRepository groupRepo;
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoomChatRepository roomChatRepo;
	@Autowired
	private ContactRepository contactRepo;
	@Autowired
	private MessageRepo messageRepo;
	@Autowired
	private ViewedStatusRepo viewedStatusRepo;

	@Override
	public Page<Groups> getAll(Pageable page) {
		return groupRepo.getAll(page);
	}

	@Override
	public GroupDTO getOne(Long id) {
		Groups group = groupRepo.getGroup(id);
		GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
		Long roomId = roomChatRepo.getRoomByGroupId(id).get(0).getRoomId();
		groupDTO.setRoomId(roomId);
		List<Contacts> listContact = contactRepo.getContactByRoomId(roomId);
		List<ContactDTO> listContactDTO = listContact.stream().map(contact -> {
			Users user = userRepo.findById(contact.getUser().getUserId()).get();
			ContactDTO contactDTO = modelMapper.map(user, ContactDTO.class);
			return contactDTO;
		}).collect(Collectors.toList());
		groupDTO.setListContact(listContactDTO);
		return groupDTO;
	}

	@Override
	public void addMemberToGroup(Users user, Groups group) {
		Members member = new Members(user.getUserId(), group.getGroupId(), false, true);
		memberRepo.save(member);
		group.setTotalMember(memberRepo.countMemberInGroup(group.getGroupId()));
		groupRepo.save(group);

	}

	@Override
	public void deleteMemberToGroup(Long groupdId, Long userId) {
		memberRepo.removeUserToGroup(groupdId, userId);
	}

	@Override
	public UserDTO getTeacherFromGroup(Long groupId) {
		Members member = memberRepo.getTeacherFromGroup(groupId);
		Users user = userRepo.findById(member.getUserId()).get();
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	@Override
	public Integer getUserId(String email) {
		return userRepo.getIdUserByEmail(email);
	}

	@Override
	public GroupDTO createGroup(GroupDTO group) {
		Groups groupEntity = modelMapper.map(group, Groups.class);
		groupEntity.setTotalMember(0L);
		Groups groups = groupRepo.save(groupEntity);
		GroupDTO groupDTO = modelMapper.map(groups, GroupDTO.class);
		groupDTO.setGroupId(groups.getGroupId());
		RoomChats room = new RoomChats();
		room.setLastMessage("Có thành viên vừa tham gia nhóm");
		// encodedString
		String encodedString = Base64.getEncoder().encodeToString(room.getLastMessage().getBytes());
		room.setLastMessage(encodedString);

		room.setGroup(groups);
		RoomChats roomCreate = roomChatRepo.save(room);

		Contacts contact = new Contacts();
		contact.setRoom(roomCreate);
		contact.setUser(userRepo.findById(group.getAdminId()).get());
		contact.setIsAdmin(true);
		Contacts contactCreated = contactRepo.save(contact);

		Messages message = new Messages();
		message.setContact(contactCreated);
		message.setStatus(true);
		String encodedStringContent = Base64.getEncoder().encodeToString(
				(userRepo.findById(group.getAdminId()).get().getFullName() + " đã tham gia nhóm").getBytes());
		message.setContent(encodedStringContent);
		messageRepo.save(message);

		ViewedStatus viewedStatus = new ViewedStatus();
		viewedStatus.setContactId(contactCreated.getContactId());
		viewedStatusRepo.save(viewedStatus);

		Members member = new Members(group.getAdminId(), groupDTO.getGroupId(), true, true);
		memberRepo.save(member);
		return groupDTO;
	}

	@Override
	public List<UserDTO> getMemberInGroup(Long id) {
		List<Members> listMember = memberRepo.getMemberInGroup(id);
		List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
		for (int i = 0; i < listMember.size(); i++) {
			Users user = userRepo.findById(listMember.get(i).getUserId()).get();
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			listUserDTO.add(userDTO);
		}
		return listUserDTO;
	}

	@Override
	public GroupDTO updateGroup(GroupDTO group) {
		Groups groupEntity = modelMapper.map(group, Groups.class);
		Groups groups = groupRepo.save(groupEntity);
		GroupDTO groupDTO = modelMapper.map(groups, GroupDTO.class);
		return groupDTO;
	}

	@Override
	public List<MemberDTO> createExcel(MultipartFile multipartFile, Long groupId, Long userId) throws IOException {
		try {
			ExcelService excel = new ExcelService();
			HashMap<Integer, Users> map = new HashMap();
			FileUploadUtil.saveFile("abc.xlsx", multipartFile);
			String excelFilePath = "./Files/abc.xlsx";
			Long user_id = (long) 1;
			;
			List<Book> books = excel.readExcel(excelFilePath);
			for (int i = 0; i <= books.size() - 2; i++) {
				user_id = Long.parseLong(userRepo.getIdUserByEmail(books.get(i).getEmail()) + "");
				map.put(i, userRepo.findById(user_id).get());
			}
			Groups group = modelMapper.map(groupRepo.findById(groupId), Groups.class);
			map.entrySet().forEach(entry -> {
				Users user = entry.getValue();
				Members member = new Members(user.getUserId(), group.getGroupId(), false, true);
				memberRepo.save(member);
				Contacts contact = new Contacts(user, roomChatRepo.getRoomByGroupId(group.getGroupId()).get(0));
				contactRepo.save(contact);
			});
			List<Members> listMember = memberRepo.getMemberInGroup(groupId);
			group.setTotalMember(Long.parseLong(listMember.size() + ""));
			groupRepo.save(group);
			List<MemberDTO> listMemberDTO = listMember.stream()
					.map(element -> modelMapper.map(element, MemberDTO.class))
					.collect(Collectors.toList());
			return listMemberDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public GroupDTO deleteGroup(Long groupId) {
		Groups group = groupRepo.findById(groupId).get();
		group.setStatus(false);
		groupRepo.save(group);
		GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
		return groupDTO;
	}

	@Override
	public List<GroupDTO> findByKeywork(String keywork, Long userId) {
		List<Groups> groups = groupRepo.findByGroupName(keywork);
		List<GroupDTO> groupDTO = groups.stream().map(group -> modelMapper.map(group, GroupDTO.class))
				.collect(Collectors.toList());
		List<Members> listMember = memberRepo.getAllGroupByUser(userId);
		try {
			for (int i = 0; i < groupDTO.size(); i++) {
				for (int j = 0; j < listMember.size(); j++) {
					if (listMember.get(j).getGroupId() == groupDTO.get(i).getGroupId()) {
						// groupDTO.remove(i);
					}
				}
				Long roomId = roomChatRepo.getRoomByGroupId(groupDTO.get(i).getGroupId()).get(0).getRoomId();
				List<Contacts> listContact = contactRepo.getContactByRoomId(roomId);
				List<ContactDTO> listContactDTO = new ArrayList<ContactDTO>();
				for (int j = 0; j < listContact.size(); j++) {
					Users user = userRepo.findById(listContact.get(j).getUser().getUserId()).get();
					ContactDTO contactDTO = new ContactDTO(user.getUserId(), user.getFullName(), user.getEmail(),
							user.getAvatar(), user.getStudentCode(), listContact.get(j).getContactId());
					listContactDTO.add(contactDTO);
				}
				groupDTO.get(i).setListContact(listContactDTO);
			}
		} catch (Exception e) {
		}
		return groupDTO;
	}

	@Override
	public UserDTO getOneMemberInGroup(String email, Long groupId) {
		Long userId = (long) userRepo.getIdUserByEmail(email);
		memberRepo.getOneMemberInGroup(userId, groupId);
		Users users = userRepo.findById(userId).get();
		UserDTO userDTO2 = modelMapper.map(users, UserDTO.class);
		return userDTO2;

	}

	@Override
	public MemberDTO saveMember(StudentDTO user) {
		Members member = new Members(user.getUserId(), user.getGroupId(), false, true);
		MemberDTO memberDTO = modelMapper.map(memberRepo.save(member), MemberDTO.class);
		Groups group = groupRepo.findById(user.getGroupId()).get();
		group.setTotalMember(memberRepo.countMemberInGroup(group.getGroupId()));
		groupRepo.save(group);

		return memberDTO;
	}

	@Override
	public List<MemberGroupDTO> getAllGroupByStudent(Long userId) {
		List<Members> list = memberRepo.getAllGroupByStudent(userId);

		List<MemberGroupDTO> listDTO = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Groups groupOne = groupRepo.findById(list.get(i).getGroupId()).get();
			Long roomChatId = roomChatRepo.getRoomByGroupId(groupOne.getGroupId()).get(0).getRoomId();
			MemberGroupDTO member = new MemberGroupDTO(groupOne.getGroupId(), groupOne.getName(),
					list.get(i).getIsTeacher(), groupOne.getTotalMember(), groupOne.getAvatar(), roomChatId);
			member.setClassName(groupOne.getClassName());
			try {
				Long roomId = roomChatRepo.getRoomByGroupId(groupOne.getGroupId()).get(0).getRoomId();
				List<Contacts> contact = contactRepo.getContactByRoomId(roomId);
				List<ContactDTO> listContactDTO = new ArrayList<>();
				for (int j = 0; j < contact.size(); j++) {
					ContactDTO contactDTO = new ContactDTO(contact.get(j).getUser().getUserId(),
							contact.get(j).getUser().getFullName(), contact.get(j).getUser().getEmail(),
							contact.get(j).getUser().getAvatar(), contact.get(j).getUser().getStudentCode(),
							contact.get(j).getContactId());
					listContactDTO.add(contactDTO);
				}
				member.setListContact(listContactDTO);
			} catch (Exception e) {
			}
			listDTO.add(member);

		}

		return listDTO;
	}

	@Override
	public List<MemberGroupDTO> getAllGroupByTeacher(Long userId) {
		List<Members> list = memberRepo.getAllGroupByTeacher(userId);

		List<MemberGroupDTO> listDTO = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Groups groupOne = groupRepo.findById(list.get(i).getGroupId()).get();
			Long roomChatId = roomChatRepo.getRoomByGroupId(groupOne.getGroupId()).get(0).getRoomId();
			MemberGroupDTO member = new MemberGroupDTO(groupOne.getGroupId(), groupOne.getName(),
					list.get(i).getIsTeacher(), groupOne.getTotalMember(), groupOne.getAvatar(), roomChatId);
			member.setClassName(groupOne.getClassName());
			try {
				Long roomId = roomChatRepo.getRoomByGroupId(groupOne.getGroupId()).get(0).getRoomId();
				List<Contacts> contact = contactRepo.getContactByRoomId(roomId);
				List<ContactDTO> listContactDTO = new ArrayList<>();
				for (int j = 0; j < contact.size(); j++) {
					ContactDTO contactDTO = new ContactDTO(contact.get(j).getUser().getUserId(),
							contact.get(j).getUser().getFullName(), contact.get(j).getUser().getEmail(),
							contact.get(j).getUser().getAvatar(), contact.get(j).getUser().getStudentCode(),
							contact.get(j).getContactId());
					listContactDTO.add(contactDTO);
				}
				member.setListContact(listContactDTO);
			} catch (Exception e) {

			}
			listDTO.add(member);
		}
		return listDTO;
	}

	@Override
	public Page<Groups> getAllGroupFalse(Pageable page) {
		return groupRepo.getAllGroupFalse(page);
	}

	@Override
	public MemberDTO memberJoinGroup(Long groupId, Long userId) {
		Members member = new Members(userId, groupId, false, false);
		MemberDTO memberDTO = modelMapper.map(memberRepo.save(member), MemberDTO.class);
		Groups group = groupRepo.findById(groupId).get();
		group.setTotalMember(memberRepo.countMemberInGroup(groupId));
		groupRepo.save(group);
		return memberDTO;
	}

	@Override
	public List<MemberDTO2> getAllMemberJoinGroupFalse(Long groupId) {
		List<Members> listMember = memberRepo.getUserJoin(groupId);
		List<MemberDTO2> listUser = new ArrayList();
		for (Members member : listMember) {
			Users user = userRepo.findById(member.getUserId()).get();
			MemberDTO2 userDTO = modelMapper.map(user, MemberDTO2.class);
			listUser.add(userDTO);
		}
		return listUser;

	}

	@Override
	public UserDTO confirmOneMemberGroup(Long groupId, Long userId) {
		memberRepo.confirmUser(groupId, userId);
		Users user = userRepo.findById(userId).get();
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		Groups group = groupRepo.findById(groupId).get();
		group.setTotalMember(memberRepo.countMemberInGroup(group.getGroupId()));
		groupRepo.save(group);
		return userDTO;
	}

	@Override
	public List<Members> confirmAllMemberGroup(Long groupId) {
		memberRepo.confirmAllUser(groupId);
		List<Members> listMember = memberRepo.getMemberInGroup(groupId);
		return listMember;
	}

	@Override
	public void memberLeaveGroup(Long groupId, Long userId) {
		memberRepo.memberLeaveGroup(groupId, userId);
		Groups group = groupRepo.findById(groupId).get();
		group.setTotalMember(memberRepo.countMemberInGroup(group.getGroupId()));
		groupRepo.save(group);

		Long roomId = roomChatRepo.getRoomByGroupId(groupId).get(0).getRoomId();
		List<Contacts> listContact = contactRepo.getContactByRoomId(roomId);
		for (Contacts contact : listContact) {
			if (contact.getUser().getUserId() == userId) {
				contact.setStatus(false);
				contactRepo.save(contact);
				Messages message = new Messages();
				message.setContact(contact);
				message.setStatus(true);
				String encodedStringContent = Base64.getEncoder().encodeToString(
						(userRepo.findById(userId).get().getFullName() + " đã rời khỏi nhóm").getBytes());
				message.setContent(encodedStringContent);
				messageRepo.save(message);
				break;
			}
		}

	}

	@Override
	public List<MemberGroupDTO> getAllGroupByUser(Long userId) {
		List<Members> list = memberRepo.getAllGroupByUser(userId);
		List<MemberGroupDTO> listDTO = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Groups groupOne = groupRepo.findById(list.get(i).getGroupId()).get();
			Long roomChatId = roomChatRepo.getRoomByGroupId(groupOne.getGroupId()).get(0).getRoomId();
			MemberGroupDTO member = new MemberGroupDTO(groupOne.getGroupId(), groupOne.getName(),
					list.get(i).getIsTeacher(), groupOne.getTotalMember(), groupOne.getAvatar(), roomChatId);
			member.setClassName(groupOne.getClassName());
			try {
				Long roomId = roomChatRepo.getRoomByGroupId(groupOne.getGroupId()).get(0).getRoomId();
				List<Contacts> contact = contactRepo.getContactByRoomId(roomId);
				List<ContactDTO> listContactDTO = new ArrayList<>();
				for (int j = 0; j < contact.size(); j++) {
					ContactDTO contactDTO = new ContactDTO(contact.get(j).getUser().getUserId(),
							contact.get(j).getUser().getFullName(), contact.get(j).getUser().getEmail(),
							contact.get(j).getUser().getAvatar(), contact.get(j).getUser().getStudentCode(),
							contact.get(j).getContactId());
					listContactDTO.add(contactDTO);
				}
				member.setListContact(listContactDTO);
			} catch (Exception e) {

			}
			listDTO.add(member);
		}
		return listDTO;
	}

}
