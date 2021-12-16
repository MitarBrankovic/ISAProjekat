package com.BookingApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.FishingAppointmentDto;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.FishingAppointmentRepository;
import java.util.Optional;

@RestController
@RequestMapping("/fishingAppointments")
public class FishingAppointmentService {

	@Autowired
	private FishingAppointmentRepository fishingAppointmentRepository;
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;

	public ResponseEntity<List<FishingAppointment>> getAdventureQuickAppointments(long id)
	{
		List<FishingAppointment> appointments = new ArrayList<FishingAppointment>();
		for(FishingAppointment appointment : fishingAppointmentRepository.findAll())
		{
			if (appointment.fishingAdventure.id == id)
				appointments.add(appointment);
		}
		return new ResponseEntity<List<FishingAppointment>>(appointments,HttpStatus.OK);
	} 

	@PostMapping(path = "/addQuickAppointment")
    public ResponseEntity<List<FishingAppointment>> addAppointment(@RequestBody FishingAppointmentDto appointmentDTO)
	{	
		if(appointmentDTO != null) {
			FishingAppointment appointment = new FishingAppointment(appointmentDTO.formatDateFrom(), appointmentDTO.address, appointmentDTO.city, 
											 appointmentDTO.durationInHours(), appointmentDTO.maxAmountOfPeople, AppointmentType.quick, true,
											 "", appointmentDTO.price);
			appointment.fishingAdventure = getAdventureById(appointmentDTO.adventureId);
			fishingAppointmentRepository.save(appointment);
			return getAdventureQuickAppointments(appointmentDTO.adventureId);
		}
		return null;
	}
	
	
	private FishingAdventure getAdventureById(long id) {
		Optional<FishingAdventure> adventure = fishingAdventureRepository.findById(id);
		FishingAdventure ret = adventure.get(); 
		return ret;
	}
	
	@GetMapping(path = "/getQuickFishingAppointments/{adventureId}")
	public ResponseEntity<List<FishingAppointment>> getInstructorsAdventures(@PathVariable("adventureId") long id)
	{
		List<FishingAppointment> appointments = new ArrayList<FishingAppointment>();
		for(FishingAppointment appointment : fishingAppointmentRepository.findAll())
		{
			if (appointment.fishingAdventure.id == id)
				appointments.add(appointment);
		}
		return new ResponseEntity<List<FishingAppointment>>(appointments,HttpStatus.OK);
	}

}
