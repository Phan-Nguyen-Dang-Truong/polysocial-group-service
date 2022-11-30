package com.polysocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.polysocial.entity.Contacts;

@Repository
public interface ContactRepository extends JpaRepository<Contacts, Long> {

    @Query("SELECT c FROM Contacts c WHERE c.room.roomId = ?1")
    List<Contacts> getContactByRoomId(Long roomId);
    
}
