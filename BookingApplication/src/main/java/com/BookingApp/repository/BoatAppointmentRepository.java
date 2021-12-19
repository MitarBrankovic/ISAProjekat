package com.BookingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.BoatAppointment;

public interface BoatAppointmentRepository extends JpaRepository<BoatAppointment, Long>{

	@Query("select b from BoatAppointment b where b.client.id = :appUserId")
	public List<BoatAppointment> findAllAppointmentsByClient(@Param("appUserId") long appUserId);
}