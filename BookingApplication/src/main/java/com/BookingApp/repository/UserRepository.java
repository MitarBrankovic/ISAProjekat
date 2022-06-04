package com.BookingApp.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BookingApp.model.AppUser;

@Repository //ovo mozda nije potrebno	
public interface UserRepository  extends JpaRepository<AppUser, Long> {

	public AppUser findByEmail(String email);
	public AppUser findByVerificationCode(String verificationCode);
	
    @Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select c from AppUser c where c.id = :id")
	public AppUser findByIdPess(@Param("id") long id);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    void deleteById(long id);
    
    
}
