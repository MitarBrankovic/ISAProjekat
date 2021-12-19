package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.FishingInstructor;
import com.BookingApp.model.PricelistItem;

public interface FishingInstructorRepository  extends JpaRepository<FishingInstructor, Long> {

}
