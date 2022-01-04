package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.RatingCottage;

public interface RatingCottageRepository extends JpaRepository<RatingCottage, Long>  {

	@Query("SELECT rc FROM RatingCottage rc WHERE rc.isApproved = false")
	public Set<RatingCottage> findAllUnapproved();
}
