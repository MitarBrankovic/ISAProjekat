package com.BookingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.SubscribeAdventure;

public interface SubscribeAdvRepository extends JpaRepository<SubscribeAdventure, Long>  {

	@Query("select r from SubscribeAdventure r where r.fishingAdventure.id = :id")
	public SubscribeAdventure findByAdventure(@Param("id") long id);
	
	@Query("select r from SubscribeAdventure r where r.client.id = :id")
	public List<SubscribeAdventure> findAllByClient(@Param("id") long id);
	
	@Query("select r from SubscribeAdventure r where r.fishingAdventure.id = :id")
	public List<SubscribeAdventure> findAllByAdventure(@Param("id") long id);
}
