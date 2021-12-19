package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.SubscribeBoat;

public interface SubscribeBoatRepository extends JpaRepository<SubscribeBoat, Long>  {
	
	@Query("select r from SubscribeBoat r where r.boat.id = :id")
	public SubscribeBoat findByBoat(@Param("id") long id);
}
