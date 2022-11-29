package com.polysocial.redis;


import com.polysocial.dto.UserDTO;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDTORedisRepo {
    private HashOperations hashOperations;

	public UserDTORedisRepo(RedisTemplate redisTemplate) {
		this.hashOperations = redisTemplate.opsForHash();
	}

	public void createList(List<UserDTO> user) {
		hashOperations.put("ListUserDTO", "listUserDTO", user);
	}
	
	public UserDTO getList(Long groupId) {
		return (UserDTO) hashOperations.get("ListUserDTO", groupId);
	}

	public Map<Long, List<UserDTO>>getAllList(){
		return hashOperations.entries("ListUserDTO");
	}
	
	public void updateList(UserDTO user) {
		hashOperations.put("ListUserDTO", user.getUserId(), user);
	}
	
	public void deleteList(Long userId) {
		hashOperations.delete("ListUserDTO", userId);
	}

	//

	public void create(UserDTO user) {
		hashOperations.put("UserDTO", user.getUserId(), user);
	}
	
	public UserDTO get(Long userId) {
		return (UserDTO) hashOperations.get("UserDTO", userId);
	}
}
