package com.BookingApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.ComplaintDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.Complaint;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.ComplaintRepository;
import com.BookingApp.repository.UserRepository;

@RestController
@RequestMapping("/client")
public class ClientService {
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
	
	@PostMapping(path = "/sendComplaint")
    public boolean sendComplaint(@RequestBody ComplaintDto complaintDto)
	{	
		if(complaintDto.cottage != null) {
			Complaint complaint = new Complaint(complaintDto.text, complaintDto.cottage, complaintDto.appUser);
			complaintRepository.save(complaint);
			return true;
		}
			
		return false;
	}
}
