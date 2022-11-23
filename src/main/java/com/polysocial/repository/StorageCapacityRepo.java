package com.polysocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.polysocial.entity.StorageCapacity;

@Repository
public interface StorageCapacityRepo extends JpaRepository<StorageCapacity, Long> {

    @Query("SELECT o FROM StorageCapacity o WHERE o.user.userId =?1")
    StorageCapacity findByUserId(Long userId);
}
