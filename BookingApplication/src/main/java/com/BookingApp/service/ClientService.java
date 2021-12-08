package com.BookingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.AppUser;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.UserRepository;

@RestController
@RequestMapping("/client")
public class ClientService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClientRepository clientRepository;
	
	
	@PutMapping(path="/edit")
	public void updatePatient(@RequestBody AppUser appUser)
	{
		AppUser user = userRepository.findByEmail(appUser.email);
		user.name = appUser.name;
		user.surname = appUser.surname;
		//user.email = appUser.email;
		user.password = appUser.password;
		user.address = appUser.address;
		user.city = appUser.city;
		user.country = appUser.country;
		user.phoneNumber = appUser.phoneNumber;
		//user.role = appUser.role;
		//user.verificationCode = appUser.verificationCode;
		//user.verified = appUser.verified;
		userRepository.save(user);
	}
}
