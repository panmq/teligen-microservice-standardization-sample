package com.teligen.repository;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.teligen.domain.ResourceHandle;

@Profile("dispach")
@Repository
public interface ResourceHandleRepository extends JpaRepository<ResourceHandle, Long> {

	List<ResourceHandle> findByResourceTypeAndFileName(Integer resourceType, String file_name);
	
	@Modifying 
    @Query("update ResourceHandle r set r.completeTime = ?1 where r.resourceType = ?2 and r.fileName = ?3")
    int update(Date completeTime, Integer resourceType, String fileName);

}
