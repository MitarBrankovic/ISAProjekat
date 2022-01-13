package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.AdventurePhoto;
import com.BookingApp.model.FishingAdventure;

public interface AdventurePhotoRepository extends JpaRepository<AdventurePhoto, Long>  {

	@Query("SELECT ap FROM AdventurePhoto ap WHERE ap.fishingAdventure.id=?1")
	public Set<AdventurePhoto> findAdventuresPhotos(long id);
}

