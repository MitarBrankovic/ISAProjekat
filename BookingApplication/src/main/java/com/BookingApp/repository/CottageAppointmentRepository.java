package com.BookingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.CottageAppointment;


public interface CottageAppointmentRepository extends JpaRepository<CottageAppointment, Long>{

	
	@Query("select c from CottageAppointment c where c.client.id = :appUserId")
	public List<CottageAppointment> findAllAppointmentsByClient(@Param("appUserId") long appUserId);
}