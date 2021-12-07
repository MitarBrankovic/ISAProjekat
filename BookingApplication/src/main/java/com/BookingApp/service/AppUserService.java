package com.BookingApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.AppUser;
import com.BookingApp.repository.UserRepository;

@RestController
@RequestMapping("/appUser")
public class AppUserService {
	@Autowired
	private UserRepository userRepository;
	
	
	
	@GetMapping(path="/getUsers")
	public ResponseEntity<List<AppUser>> getPharmacists()
	{	
		return new ResponseEntity<List<AppUser>>(userRepository.findAll(),HttpStatus.OK);
	}
}
