package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.BookingApp.model.FishingAdventure;

@Service
public interface FishingAdventureRepository extends JpaRepository<FishingAdventure, Long> {

	@Query("SELECT fi FROM FishingAdventure fi WHERE fi.fishingInstructor.id=?1")
	public Set<FishingAdventure> findInstructorsAdventures(long id);
	
}
