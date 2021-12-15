package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.FishingAppointment;

public interface FishingAppointmentRepository extends JpaRepository<FishingAppointment, Long>{

}
