package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Long>  {

}
