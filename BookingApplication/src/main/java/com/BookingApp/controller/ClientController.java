package com.BookingApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.BookingApp.model.AppUser;
import com.BookingApp.model.Complaint;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.ComplaintRepository;
import com.BookingApp.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ComplaintRepository complaintRepository;
	
	@PutMapping(path="/edit")
	public void updatePatient(@RequestBody AppUser appUser)
	{
		AppUser user = userRepository.findByEmail(appUser.email);
		user.name = appUser.name;
		user.surname = appUser.surname;
		user.password = appUser.password;
		user.address = appUser.address;
		user.city = appUser.city;
		user.country = appUser.country;
		user.phoneNumber = appUser.phoneNumber;
		userRepository.save(user);
	}
	
	@PostMapping(path = "/sendComplaint")
	@PreAuthorize("hasAuthority('CLIENT')")
    public boolean sendComplaint(@RequestBody Complaint complaintDto)
	{	
		if(complaintDto.entityId != 0) {
			Complaint complaint = new Complaint(complaintDto.text, complaintDto.entityId, complaintDto.owner, complaintDto.client);
			complaintRepository.save(complaint);
			return true;
		}
			
		return false;
	}
}
