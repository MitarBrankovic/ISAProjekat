package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.FishingAppointment;

public interface FishingAppointmentRepository extends JpaRepository<FishingAppointment, Long>{
	
	@Query("SELECT fa FROM FishingAppointment fa WHERE fa.fishingAdventure.id=?1")
	public Set<FishingAppointment> findAdventuresAppointments(long id);

}
