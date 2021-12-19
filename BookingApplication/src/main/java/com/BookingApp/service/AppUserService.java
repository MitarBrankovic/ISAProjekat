package com.BookingApp.service;

import java.util.ArrayList;
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

import com.BookingApp.dto.ComplaintDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.Complaint;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.ComplaintRepository;
import com.BookingApp.repository.CottageOwnerRepository;
import com.BookingApp.repository.CottageRepository;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.FishingInstructorRepository;
import com.BookingApp.repository.RequestDeleteAccRepository;
import com.BookingApp.repository.ShipOwnerRepository;
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
	@Autowired
	private CottageOwnerRepository cottageOwnerRepository;
	@Autowired
	private ShipOwnerRepository shipOwnerRepository;
	@Autowired
	private FishingInstructorRepository fishingInstructorRepository;
	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private BoatRepository boatRepository;
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	
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
	public ResponseEntity<List<ComplaintDto>> getComplaints()
	{	
		List<Complaint> complaints = complaintRepository.findAll();
		List<ComplaintDto> dtos = new ArrayList<ComplaintDto>();
		ComplaintDto dto = new ComplaintDto();
		for(Complaint c: complaints){
			AppUser user = userRepository.findById(c.owner).get();
			dto.nameSurnameOwner = user.name + " " + user.surname;
			dto.client = c.client;
			dto.text = c.text;
			dto.entityId = c.entityId;
			dto.ownerId = c.owner;
			dto.id = c.id;
			if(cottageOwnerRepository.findById(c.owner).isPresent()) {
				dto.entity = cottageRepository.findById(c.entityId).get().name;
			}
			if(shipOwnerRepository.findById(c.owner).isPresent()) {
				dto.entity = boatRepository.findById(c.entityId).get().name;
			}
			if(fishingInstructorRepository.findById(c.owner).isPresent()) {
				dto.entity = fishingAdventureRepository.findById(c.entityId).get().name;
			}
			dtos.add(dto);
		}

		return new ResponseEntity<List<ComplaintDto>>(dtos,HttpStatus.OK);
	}

	
}
