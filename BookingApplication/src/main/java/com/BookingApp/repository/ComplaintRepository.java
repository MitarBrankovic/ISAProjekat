package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long>  {

}
