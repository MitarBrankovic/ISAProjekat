package com.BookingApp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.BookingApp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	List<Role> findByName(String name);
}
