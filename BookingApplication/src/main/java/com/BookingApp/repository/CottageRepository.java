package com.BookingApp.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.Boat;
import com.BookingApp.model.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Long>  {

	@Query("select c from Cottage c where c.cottageOwner.id = :appUserId")
	public List<Cottage> getAllCottagesForOwner(@Param("appUserId") long appUserId);
	
    @Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select c from Cottage c where c.id = :id")
	public Cottage findByIdPess(@Param("id") long id);
    
    @Query("select c from Cottage c where c.cottageOwner.id=?1")
   	public Set<Cottage> findOwnersCottages(long id);
}
