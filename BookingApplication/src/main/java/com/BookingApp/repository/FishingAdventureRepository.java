package com.BookingApp.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.BookingApp.model.FishingAdventure;

@Service
public interface FishingAdventureRepository extends JpaRepository<FishingAdventure, Long> {

	@Query("SELECT fi FROM FishingAdventure fi WHERE fi.fishingInstructor.id=?1")
	public Set<FishingAdventure> findInstructorsAdventures(long id);
	
	@Query("SELECT fi FROM FishingAdventure fi WHERE fi.fishingInstructor.id=?1")
	public List<FishingAdventure> findInstructorsAdventuresList(long id);
	
	@Modifying
	@Query("delete from FishingAdventure fi where fi.id = ?1")
	public void delete(long entityId);
	
	@Modifying
	@Query("DELETE FROM FishingAdventure fi WHERE fi.id = :pid")
	public void deleteByPid(@Param("pid") Long theId);
	
    @Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select c from FishingAdventure c where c.id = :id")
	public FishingAdventure findByIdPess(@Param("id") long id);
}
