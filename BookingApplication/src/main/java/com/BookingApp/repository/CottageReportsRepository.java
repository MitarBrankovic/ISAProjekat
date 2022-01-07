package com.BookingApp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.CottageAppointmentReport;

public interface CottageReportsRepository extends JpaRepository<CottageAppointmentReport, Long> {

	@Query("SELECT c FROM CottageAppointmentReport c WHERE c.isApproved=?1")
	public Set<CottageAppointmentReport> findUnapprovedPenalties(boolean bool);
	
	
	@Query("select r from CottageAppointmentReport r where r.client.id = :id")
	public List<CottageAppointmentReport> findAllByClient(@Param("id") long id);
}
