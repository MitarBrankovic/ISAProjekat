package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.RatingFishingAdventure;

public interface RatingFishingAdventureRepository extends JpaRepository<RatingFishingAdventure, Long> {

	@Query("SELECT fi FROM RatingFishingAdventure fi WHERE fi.isApproved = false")
	public Set<RatingFishingAdventure> findAllUnapproved();
}
