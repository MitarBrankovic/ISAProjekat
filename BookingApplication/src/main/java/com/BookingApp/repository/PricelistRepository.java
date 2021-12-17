package com.BookingApp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BookingApp.model.PricelistItem;

@Service
public interface PricelistRepository extends JpaRepository<PricelistItem, Long>  {

	@Query("SELECT a FROM PricelistItem a WHERE a.id.appUser.id=?1")
	public Set<PricelistItem> findAllItemsByInstructorsId(long id);
}
