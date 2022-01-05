package com.BookingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Long>  {

	@Query("select c from Cottage c where c.cottageOwner.id = :appUserId")
	public List<Cottage> getAllCottagesForOwner(@Param("appUserId") long appUserId);
}
