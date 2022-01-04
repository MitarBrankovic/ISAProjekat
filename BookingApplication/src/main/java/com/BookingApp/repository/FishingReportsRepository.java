package com.BookingApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookingApp.model.FishingAppointmentReport;

public interface FishingReportsRepository extends JpaRepository<FishingAppointmentReport, Long> {

	@Query("SELECT far FROM FishingAppointmentReport far WHERE far.appointment.id=?1")
	public FishingAppointmentReport findByAppointmentId(long id);
	
	@Query("SELECT far FROM FishingAppointmentReport far WHERE far.isApproved=?1")
	public Set<FishingAppointmentReport> findUnapprovedPenalties(boolean bool);
}
