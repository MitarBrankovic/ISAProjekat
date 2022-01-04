package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.CottageAppointmentReport;
import com.BookingApp.model.FishingAppointmentReport;

public interface CottageReportsRepository extends JpaRepository<CottageAppointmentReport, Long> {

	@Query("SELECT c FROM CottageAppointmentReport c WHERE c.isApproved=?1")
	public Set<CottageAppointmentReport> findUnapprovedPenalties(boolean bool);
	
}
