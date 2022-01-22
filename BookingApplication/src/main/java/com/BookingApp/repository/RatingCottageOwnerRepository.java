package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookingApp.model.RatingCottageOwner;

@Repository
public interface RatingCottageOwnerRepository extends JpaRepository<RatingCottageOwner, Long> {

}
