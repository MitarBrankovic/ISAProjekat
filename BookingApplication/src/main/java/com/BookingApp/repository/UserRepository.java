package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.BookingApp.model.AppUser;

@Repository //ovo mozda nije potrebno	
public interface UserRepository  extends JpaRepository<AppUser, Long> {

	public AppUser findByEmail(String email);
}
