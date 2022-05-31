package com.BookingApp.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BookingApp.model.CottageAppointment;

@Repository
public interface CottageAppointmentRepository extends JpaRepository<CottageAppointment, Long>{

	
	@Query("select c from CottageAppointment c where c.client.id = :appUserId")
	public List<CottageAppointment> findAllAppointmentsByClient(@Param("appUserId") long appUserId);
	
	@Query("SELECT c FROM CottageAppointment c WHERE c.cottage.cottageOwner.id=?1")
	public List<CottageAppointment> findOwnersReservationHistory(long id);
	
}