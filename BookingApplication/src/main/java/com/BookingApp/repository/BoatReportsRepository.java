package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.BoatAppointmentReport;

public interface BoatReportsRepository extends JpaRepository<BoatAppointmentReport, Long> {

	@Query("SELECT b FROM BoatAppointmentReport b WHERE b.isApproved=?1")
	public Set<BoatAppointmentReport> findUnapprovedPenalties(boolean bool);
}
