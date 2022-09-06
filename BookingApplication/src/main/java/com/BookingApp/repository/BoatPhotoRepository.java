package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.AdventurePhoto;
import com.BookingApp.model.BoatPhoto;
import com.BookingApp.model.CottagePhoto;


public interface BoatPhotoRepository extends JpaRepository<BoatPhoto, Long>  {

	@Query("SELECT ap FROM BoatPhoto ap WHERE ap.boat.id=?1")
	public Set<BoatPhoto> findBoatPhotos(long id);
}

