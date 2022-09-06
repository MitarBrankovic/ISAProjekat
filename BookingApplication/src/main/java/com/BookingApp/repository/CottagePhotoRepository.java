package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.AdventurePhoto;
import com.BookingApp.model.CottagePhoto;


public interface CottagePhotoRepository extends JpaRepository<CottagePhoto, Long>  {

	@Query("SELECT ap FROM CottagePhoto ap WHERE ap.cottage.id=?1")
	public Set<CottagePhoto> findCottagePhotos(long id);
}

