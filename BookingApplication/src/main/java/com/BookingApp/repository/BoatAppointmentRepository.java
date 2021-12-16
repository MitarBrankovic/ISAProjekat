package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.BoatAppointment;

public interface BoatAppointmentRepository extends JpaRepository<BoatAppointment, Long>{

}