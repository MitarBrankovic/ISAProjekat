package com.BookingApp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.BoatAppointmentReport;

public interface BoatReportsRepository extends JpaRepository<BoatAppointmentReport, Long> {

	@Query("SELECT b FROM BoatAppointmentReport b WHERE b.isApproved=?1")
	public Set<BoatAppointmentReport> findUnapprovedPenalties(boolean bool);
	
	@Query("select r from BoatAppointmentReport r where r.client.id = :id")
	public List<BoatAppointmentReport> findAllByClient(@Param("id") long id);
}
