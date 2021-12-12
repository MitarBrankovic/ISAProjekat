package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.Admin;

public interface AdminRepository  extends JpaRepository<Admin, Long>  {

}
