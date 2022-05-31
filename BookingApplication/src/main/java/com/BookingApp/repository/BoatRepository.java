package com.BookingApp.repository;

import java.util.Set;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.Boat;
import com.BookingApp.model.FishingAdventure;

public interface BoatRepository  extends JpaRepository<Boat, Long>  {

	
    @Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select c from Boat c where c.id = :id")
	public Boat findByIdPess(@Param("id") long id);
    
    @Query("SELECT fi FROM Boat fi WHERE fi.shipOwner.id=?1")
	public Set<Boat> findOwnersBoats(long id);
}
