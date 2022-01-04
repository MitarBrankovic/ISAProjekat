package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.RatingBoat;

public interface RatingBoatRepository extends JpaRepository<RatingBoat, Long> {

	@Query("SELECT rb FROM RatingBoat rb WHERE rb.isApproved = false")
	public Set<RatingBoat> findAllUnapproved();
}
