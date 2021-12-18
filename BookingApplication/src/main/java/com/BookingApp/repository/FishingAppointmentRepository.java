package com.BookingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.FishingAppointment;

public interface FishingAppointmentRepository extends JpaRepository<FishingAppointment, Long>{

	@Query("select f from FishingAppointment f where f.client.id = :appUserId")
	public List<FishingAppointment> findAllAppointmentsByClient(@Param("appUserId") long appUserId);
}
