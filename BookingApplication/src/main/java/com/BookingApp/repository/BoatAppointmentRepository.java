package com.BookingApp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.FishingAppointment;

public interface BoatAppointmentRepository extends JpaRepository<BoatAppointment, Long>{

	@Query("select b from BoatAppointment b where b.client.id = :appUserId")
	public List<BoatAppointment> findAllAppointmentsByClient(@Param("appUserId") long appUserId);

	@Query("SELECT b FROM BoatAppointment b WHERE b.boat.shipOwner.id=?1")
	public List<BoatAppointment> findOwnersReservationHistory(long id);
	
	@Query("SELECT b FROM BoatAppointment b WHERE b.boat.id=?1")
	public Set<BoatAppointment> findBoatAppointments(long id);
}