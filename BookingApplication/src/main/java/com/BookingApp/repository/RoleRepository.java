package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.BookingApp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
