package com.BookingApp.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.EditAdventureDto;
import com.BookingApp.dto.FishingInstructorAvailabilityDto;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.repository.FishingInstructorRepository;

@RestController
@RequestMapping("/fishingInstructor")
public class FishingInstructorService {

	@Autowired
	private FishingInstructorRepository fishingInstructorRepository;
	
	@PostMapping(path = "/editInstructor")
    public FishingInstructor editInstructor(@RequestBody FishingInstructor instructor)
	{	
		
		if(instructor != null) {
			List<FishingInstructor> instructors = fishingInstructorRepository.findAll();
			for (FishingInstructor fi : instructors)
				if (fi.id == instructor.id) {
					fi.name = instructor.name;
					fi.surname = instructor.surname;
					fi.address = instructor.address;
					fi.city = instructor.city;
					fi.email = instructor.email;
					fi.password = instructor.password;
					fi.country = instructor.country;
					fi.phoneNumber = instructor.phoneNumber;
					fi.biography = instructor.biography;
				}
			//probaj samo da je fi = instructor posle
			fishingInstructorRepository.saveAll(instructors);
			return fishingInstructorRepository.findById(instructor.id).get();
		}
		return null;
	}
	
	@PostMapping(path = "/editInstructorsAvailability")
    public FishingInstructor editInstructorsAvailability(@RequestBody FishingInstructorAvailabilityDto availabilityDTO)
	{	
		
		if(availabilityDTO != null) {
			List<FishingInstructor> instructors = fishingInstructorRepository.findAll();
			for (FishingInstructor fi : instructors)
				if (fi.id == availabilityDTO.instructorsId) {
					fi.availableFrom = availabilityDTO.formatDateFrom();
					fi.availableUntil = availabilityDTO.formatDateUntil();
				}
			fishingInstructorRepository.saveAll(instructors);
			return fishingInstructorRepository.findById(availabilityDTO.instructorsId).get();
		}
		return null;
	}
	
	
}
