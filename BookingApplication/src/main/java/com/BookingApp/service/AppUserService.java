package com.BookingApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.AppUser;
import com.BookingApp.model.Complaint;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.ComplaintRepository;
import com.BookingApp.repository.RequestDeleteAccRepository;
import com.BookingApp.repository.UserRepository;

@RestController
@RequestMapping("/appUser")
public class AppUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RequestDeleteAccRepository requestDeleteAccRepository;
	@Autowired
	private ComplaintRepository complaintRepository;
	
	
	@GetMapping(path="/getUsers")
	public ResponseEntity<List<AppUser>> getUsers()
	{	
		return new ResponseEntity<List<AppUser>>(userRepository.findAll(),HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/sendRequest/{textArea}/{userId}")
    public boolean sendRequest(@PathVariable("textArea") String textArea, @PathVariable("userId") long userId)
	{	
		Optional<RequestDeleteAcc> oldRequest = Optional.ofNullable(requestDeleteAccRepository.findByAppUserId(userId));
		
		if(!oldRequest.isPresent()) {
			RequestDeleteAcc request = new RequestDeleteAcc(userId, false, textArea);
			requestDeleteAccRepository.save(request);
			return true;
		}else return false;
		
	}
	
	@GetMapping(path = "/requestExists/{userId}")
    public boolean requestExists(@PathVariable("userId") long appUserId)
	{	
		Optional<RequestDeleteAcc> oldRequest = Optional.ofNullable(requestDeleteAccRepository.findByAppUserId(appUserId));
		RequestDeleteAcc r = requestDeleteAccRepository.findByAppUserId(appUserId);
		if(!oldRequest.isPresent())
			return false;
		else
			return true;
		
	}
	
	@GetMapping(path="/getRequests")
	public ResponseEntity<List<RequestDeleteAcc>> getRequests()
	{	
		return new ResponseEntity<List<RequestDeleteAcc>>(requestDeleteAccRepository.findAll(),HttpStatus.OK);
	}
	
	@GetMapping(path="/getComplaints")
	public ResponseEntity<List<Complaint>> getComplaints()
	{	
		return new ResponseEntity<List<Complaint>>(complaintRepository.findAll(),HttpStatus.OK);
	}
	
	
}
