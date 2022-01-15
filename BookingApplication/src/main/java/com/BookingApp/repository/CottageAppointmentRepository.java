package com.BookingApp.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BookingApp.model.CottageAppointment;

@Repository
public interface CottageAppointmentRepository extends JpaRepository<CottageAppointment, Long>{

	
	@Query("select c from CottageAppointment c where c.client.id = :appUserId")
	public List<CottageAppointment> findAllAppointmentsByClient(@Param("appUserId") long appUserId);
	
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from CottageAppointment c")
    public List<CottageAppointment> findAllPls();
}