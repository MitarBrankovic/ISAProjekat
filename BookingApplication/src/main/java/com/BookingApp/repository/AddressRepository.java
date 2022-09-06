package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.Address;
import com.BookingApp.model.Admin;

public interface AddressRepository  extends JpaRepository<Address, Long>  {

}
