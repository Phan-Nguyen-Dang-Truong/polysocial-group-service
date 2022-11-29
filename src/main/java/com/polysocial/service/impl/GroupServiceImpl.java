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
import com.polysocial.entity.RoomChats;
import com.polysocial.entity.StorageCapacity;
import com.polysocial.entity.Users;
import com.polysocial.redis.GroupRedisRepo;
import com.polysocial.redis.MemberDTO2RedisRepo;
import com.polysocial.redis.UserDTORedisRepo;
import com.polysocial.repository.ContactRepository;
import com.polysocial.repository.GroupRepository;
import com.polysocial.repository.MemberRepository;
import com.polysocial.repository.RoomChatRepository;
import com.polysocial.repository.StorageCapacityRepo;
import com.polysocial.repository.UserRepository;
import com.polysocial.service.ExcelService;
import com.polysocial.service.FileUploadUtil;
import com.polysocial.service.GroupService;

import java.io.IOException;
import java.util.ArrayList;
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
	private StorageCapacityRepo storageCapacityRepo;
	@Autowired
	private RoomChatRepository roomChatRepo;
	@Autowired
	private ContactRepository contactRepo;
	@Autowired
	private GroupRedisRepo groupRedisRepo;
	@Autowired
	private UserDTORedisRepo userDTORedisRepo;
	@Autowired
	private MemberDTO2RedisRepo memberDTO2RedisRepo;

	@Override
	public Page<Groups> getAll(Pageable page) {
		return groupRepo.getAll(page);
	}

	@Override
	public GroupDTO getOne(Long id) {
		Groups group = groupRepo.getGroup(id);
		GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
		return groupDTO;
	}

	@Override
	public void addMemberToGroup(Users user, Groups group) {
		Members member = new Members(user.getUserId(), group.getGroupId(), false, true);
		memberRepo.save(member);

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
		Groups groups = groupRepo.save(groupEntity);
		GroupDTO groupDTO = modelMapper.map(groups, GroupDTO.class);
		RoomChats room = new RoomChats();
		room.setGroup(groups);
		RoomChats roomCreate = roomChatRepo.save(room);
		Contacts contact = new Contacts();
		contact.setRoomId(roomCreate.getRoomId());
		contact.setRoom(roomCreate);
		contact.setUser(userRepo.findById(group.getAdminId()).get());
		contact.setIsAdmin(true);
		contactRepo.save(contact);
		Members member = new Members(group.getAdminId(), groupDTO.getGroupId(), true, true);
		memberRepo.save(member);
		return groupDTO;
	}

	@Override
	public List<UserDTO> getMemberInGroup(Long id) {
		List<Members> listMember = memberRepo.getMemberInGroup(id);
		List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
		try {
			Map<Long, List<UserDTO>> map = userDTORedisRepo.getAllList();
			List<List<UserDTO>> values = map.values().stream().collect(Collectors.toList());
			if (listMember.size() == values.get(0).size()) {
				return values.get(0);
			}
		} catch (Exception e) {
			for (int i = 0; i < listMember.size(); i++) {
				Users user = userRepo.findById(listMember.get(i).getUserId()).get();
				UserDTO userDTO = modelMapper.map(user, UserDTO.class);
				listUserDTO.add(userDTO);
			}
		}

		userDTORedisRepo.createList(listUserDTO);
		return listUserDTO;
	}

	@Override
	public GroupDTO updateGroup(GroupDTO group) {
		Groups groupEntity = modelMapper.map(group, Groups.class);
		Groups groups = groupRepo.save(groupEntity);
		GroupDTO groupDTO = modelMapper.map(groups, GroupDTO.class);
		return groupDTO;
	}

	public Boolean checkCapacity(Long userId, Long size) {
		StorageCapacity storageCapacity = storageCapacityRepo.findByUserId(userId);
		if (storageCapacity.getCapacity() > storageCapacity.getUsed() + size) {
			storageCapacity.setUsed(storageCapacity.getUsed() + size);
			storageCapacityRepo.save(storageCapacity);
			return true;
		}
		return false;
	}

	@Override
	public List<MemberDTO> createExcel(MultipartFile multipartFile, Long groupId, Long userId) throws IOException {
		try {
			if (checkCapacity(userId, multipartFile.getSize()) == false)
				return null;
			ExcelService excel = new ExcelService();
			HashMap<Integer, Users> map = new HashMap();
			FileUploadUtil.saveFile("abc.xlsx", multipartFile);
			String excelFilePath = "./Files/abc.xlsx";
			Long user_id = (long) 1;
			;
			List<Book> books = excel.readExcel(excelFilePath);
			System.out.println(books.size() + "---");
			for (int i = 0; i < books.size() - 1; i++) {
				user_id = Long.parseLong(userRepo.getIdUserByEmail(books.get(i).getEmail()) + "");
				map.put(i, userRepo.findById(user_id).get());
			}
			Groups group = new Groups(groupRepo.findById(groupId).get().getName(), Long.parseLong(map.size() + ""));
			group.setGroupId(groupId);
			group.setClassName(groupRepo.findById(groupId).get().getClassName());
			group.setDescription(groupRepo.findById(groupId).get().getDescription());

			groupRepo.save(group);
			map.entrySet().forEach(entry -> {
				Users user = entry.getValue();
				Members member = new Members(user.getUserId(), group.getGroupId(), false, true);
				memberRepo.save(member);
				Contacts contact = new Contacts(user, roomChatRepo.getRoomByGroupId(group.getGroupId()).get(0));
				contactRepo.save(contact);
			});
			List<Members> listMember = memberRepo.getMemberInGroup(groupId);
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
	public List<GroupDTO> findByKeywork(String keywork) {
		List<Groups> groups = groupRepo.findByGroupName(keywork);
		List<GroupDTO> groupDTO = groups.stream().map(group -> modelMapper.map(group, GroupDTO.class))
				.collect(Collectors.toList());
		return groupDTO;
	}

	@Override
	public UserDTO getOneMemberInGroup(String email, Long groupId) {
		Long userId = (long) userRepo.getIdUserByEmail(email);
		UserDTO userDTO = userDTORedisRepo.get(userId);
		if (userDTO == null) {
			memberRepo.getOneMemberInGroup(userId, groupId);
			Users users = userRepo.findById(userId).get();
			UserDTO userDTO2 = modelMapper.map(users, UserDTO.class);
			userDTORedisRepo.create(userDTO2);
			return userDTO2;
		}
		return userDTO;

	}

	@Override
	public MemberDTO saveMember(StudentDTO user) {
		Members member = new Members(user.getUserId(), user.getGroupId(), false, true);
		MemberDTO memberDTO = modelMapper.map(memberRepo.save(member), MemberDTO.class);
		return memberDTO;
	}

	@Override
	public List<MemberGroupDTO> getAllGroupByStudent(Long userId) {
		List<Members> list = memberRepo.getAllGroupByStudent(userId);
		try {
			Map<Long, List<MemberGroupDTO>> map = groupRedisRepo.getAllList();
			List<List<MemberGroupDTO>> values = map.values().stream().collect(Collectors.toList());
			if (list.size() == values.get(0).size()) {
				return values.get(0);
			}
		} catch (Exception e) {
		}
		List<MemberGroupDTO> listDTO = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Groups groupOne = groupRepo.findById(list.get(i).getGroupId()).get();
			MemberGroupDTO member = new MemberGroupDTO(groupOne.getGroupId(), groupOne.getName(),
					list.get(i).getIsTeacher(), groupOne.getTotalMember());
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

		groupRedisRepo.createList(listDTO);
		return listDTO;
	}

	@Override
	public List<MemberGroupDTO> getAllGroupByTeacher(Long userId) {
		List<Members> list = memberRepo.getAllGroupByTeacher(userId);
		Map<Long, List<MemberGroupDTO>> map = groupRedisRepo.getAllList();
		List<List<MemberGroupDTO>> values = map.values().stream().collect(Collectors.toList());
		if (list.size() == values.get(0).size()) {
			return values.get(0);
		}
		List<MemberGroupDTO> listDTO = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Groups groupOne = groupRepo.findById(list.get(i).getGroupId()).get();
			MemberGroupDTO member = new MemberGroupDTO(groupOne.getGroupId(), groupOne.getName(),
					list.get(i).getIsTeacher(), groupOne.getTotalMember());
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
		groupRedisRepo.createList(listDTO);
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
		return memberDTO;
	}

	@Override
	public List<MemberDTO2> getAllMemberJoinGroupFalse(Long groupId) {
		List<Members> listMember = memberRepo.getUserJoin(groupId);
		List<MemberDTO2> listUser = new ArrayList();
		try{
			Map<Long, List<MemberDTO2>> map = memberDTO2RedisRepo.getAllList();
			List<List<MemberDTO2>> values = map.values().stream().collect(Collectors.toList());
			if(listMember.size() == values.get(0).size()){
				return values.get(0);
			}
		}catch(Exception e){
			for (Members member : listMember) {
				Users user = userRepo.findById(member.getUserId()).get();
				MemberDTO2 userDTO = modelMapper.map(user, MemberDTO2.class);
				listUser.add(userDTO);
			}
		}
		memberDTO2RedisRepo.createList(listUser);
		return listUser;

	}

	@Override
	public UserDTO confirmOneMemberGroup(Long groupId, Long userId) {
		memberRepo.confirmUser(groupId, userId);
		Users user = userRepo.findById(userId).get();
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
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
	}

}
