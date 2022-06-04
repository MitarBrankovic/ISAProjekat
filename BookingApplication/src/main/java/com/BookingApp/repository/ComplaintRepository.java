package com.BookingApp.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.Complaint;
import com.BookingApp.model.RequestDeleteAcc;

public interface ComplaintRepository extends JpaRepository<Complaint, Long>  {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select c from Complaint c where c.id = :id")
	public Complaint findByIdPess(@Param("id") long id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	void deleteById(long id);

}
