package com.BookingApp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BookingApp.model.FishingAppointmentReport;

@Repository
public interface FishingReportsRepository extends JpaRepository<FishingAppointmentReport, Long> {

	@Query("SELECT far FROM FishingAppointmentReport far WHERE far.appointment.id=?1")
	public FishingAppointmentReport findByAppointmentId(long id);
	
	@Query("SELECT far FROM FishingAppointmentReport far WHERE far.isApproved=?1")
	public Set<FishingAppointmentReport> findUnapprovedPenalties(boolean bool);
	
	@Query("select r from FishingAppointmentReport r where r.client.id = :id")
	public List<FishingAppointmentReport> findAllByClient(@Param("id") long id);
}
