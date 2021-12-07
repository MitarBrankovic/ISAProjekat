package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.FishingInstructor;

public interface FishingInstructorRepository  extends JpaRepository<FishingInstructor, Long> {

}
