package com.BookingApp.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.Boat;

public interface BoatRepository  extends JpaRepository<Boat, Long>  {

	
    @Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select c from Boat c where c.id = :id")
	public Boat findByIdPess(@Param("id") long id);
}
