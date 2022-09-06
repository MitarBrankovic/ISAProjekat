package com.BookingApp.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.CottageAppointment;

@Repository
public interface CottageAppointmentRepository extends JpaRepository<CottageAppointment, Long>{

	
	
	@Query("select c from CottageAppointment c where c.client.id = :appUserId")
	public List<CottageAppointment> findAllAppointmentsByClient(@Param("appUserId") long appUserId);
	
	@Query("SELECT c FROM CottageAppointment c WHERE c.cottage.cottageOwner.id=?1")
	public List<CottageAppointment> findOwnersReservationHistory(long id);
	
	@Query("SELECT b FROM CottageAppointment b WHERE b.cottage.id=?1")
	public Set<CottageAppointment> findCottageAppointments(long id);
	@Query("SELECT b FROM CottageAppointment b WHERE b.cottage.id=?1")
	public List<CottageAppointment> findCottageAppointmentsHistory(long id);
	
}