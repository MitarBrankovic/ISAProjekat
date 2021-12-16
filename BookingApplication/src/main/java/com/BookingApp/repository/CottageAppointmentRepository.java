package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.CottageAppointment;

public interface CottageAppointmentRepository extends JpaRepository<CottageAppointment, Long>{

}