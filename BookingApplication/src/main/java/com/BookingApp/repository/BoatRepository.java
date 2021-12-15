package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.Boat;

public interface BoatRepository  extends JpaRepository<Boat, Long>  {

}
