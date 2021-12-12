package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.RequestDeleteAcc;

public interface RequestDeleteAccRepository  extends JpaRepository<RequestDeleteAcc, Long> {

	@Query("select r from RequestDeleteAcc r where r.appUserId = :appUserId")
	public RequestDeleteAcc findByAppUserId(@Param("appUserId") long appUserId);
}
