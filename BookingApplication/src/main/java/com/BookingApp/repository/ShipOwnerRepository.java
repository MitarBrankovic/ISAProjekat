package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.ShipOwner;

public interface ShipOwnerRepository  extends JpaRepository<ShipOwner, Long>  {

}
