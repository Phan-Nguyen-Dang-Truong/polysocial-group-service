package com.polysocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polysocial.entity.Contacts;

@Repository
public interface ContactRepository extends JpaRepository<Contacts, Long> {

    
}
