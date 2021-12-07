package com.BookingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingApp.model.Client;

public interface ClientRepository  extends JpaRepository<Client, Long>  {

}
