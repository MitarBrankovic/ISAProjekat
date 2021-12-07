package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.CottageOwner;

public interface CottageOwnerRepository extends JpaRepository<CottageOwner, Long> {

}
