package com.polysocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.polysocial.entity.RoomChats;

@Repository
public interface RoomChatRepository extends JpaRepository<RoomChats, Long> {

    @Query("SELECT r FROM RoomChats r WHERE r.group.groupId =?1")
    List<RoomChats> getRoomByGroupId(Long groupId);
    
}
