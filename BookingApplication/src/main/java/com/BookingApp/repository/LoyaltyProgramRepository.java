package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.model.PricelistItem;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Long>  {

	@Query("SELECT l FROM LoyaltyProgram l WHERE l.id=1")
	public LoyaltyProgram getLoyalty();
}

