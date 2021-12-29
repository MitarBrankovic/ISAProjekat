package com.BookingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BookingApp.model.SubscribeCottage;

public interface SubscribeCottageRepository extends JpaRepository<SubscribeCottage, Long> {

	
	@Query("select r from SubscribeCottage r where r.cottage.id = :id")
	public SubscribeCottage findByCottage(@Param("id") long id);
	
	@Query("select r from SubscribeCottage r where r.client.id = :id")
	public List<SubscribeCottage> findAllByClient(@Param("id") long id);
}
