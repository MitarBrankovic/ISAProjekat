package com.BookingApp.repository;

import java.util.Set;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.FishingAppointment;

public interface FishingAppointmentRepository extends JpaRepository<FishingAppointment, Long>{
	
	@Query("SELECT fa FROM FishingAppointment fa WHERE fa.fishingAdventure.id=?1")
	public Set<FishingAppointment> findAdventuresAppointments(long id);

	@Query("select f from FishingAppointment f where f.client.id = :appUserId")
	public List<FishingAppointment> findAllAppointmentsByClient(@Param("appUserId") long appUserId);
	
	@Query("SELECT fa FROM FishingAppointment fa WHERE fa.fishingAdventure.fishingInstructor.id=?1")
	public List<FishingAppointment> findInstructorsReservationHistory(long id);
}
