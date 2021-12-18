package com.BookingApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.ReservedFishingAppointmentDto;
import com.BookingApp.dto.FishingAppointmentDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Client;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.FishingAppointmentRepository;
import com.BookingApp.repository.FishingInstructorRepository;
import com.BookingApp.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/fishingAppointments")
public class FishingAppointmentService {

	@Autowired
	private FishingAppointmentRepository fishingAppointmentRepository;
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	@Autowired
	private ClientRepository clientRepository;

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
	
	@PutMapping(path = "/scheduleAdventureAppointment/{adventureId}/{userId}")
	public boolean scheduleAdventureAppointment(@PathVariable("adventureId") long id, @PathVariable("userId") long userId)
	{
		Optional<FishingAppointment> oldAppointment = fishingAppointmentRepository.findById(id);
		Client client = new Client();
		for(Client oldClient : clientRepository.findAll()) {
			if(oldClient.id == userId) {
				client = oldClient;
			}
		}
		
		if(oldAppointment.isPresent()) {
			FishingAppointment appointment = oldAppointment.get();
			appointment.client = client;
			fishingAppointmentRepository.save(appointment);
			return true;
		}
		return false;
	}
	
	@PutMapping(path = "/cancelAdventureAppointment/{adventureId}")
	public boolean cancelAdventureAppointment(@PathVariable("adventureId") long id)
	{
		Optional<FishingAppointment> oldAppointment = fishingAppointmentRepository.findById(id);

		if(oldAppointment.isPresent()) {
			FishingAppointment appointment = oldAppointment.get();
			appointment.client = null;
			fishingAppointmentRepository.save(appointment);
			return true;
		}
		return false;
	}
	
	@GetMapping(path = "/getReservedAdvAppointmentsByClient/{clientId}")
	public ResponseEntity<List<ReservedFishingAppointmentDto>> getReservedAdvAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<ReservedFishingAppointmentDto> dtos = new ArrayList<ReservedFishingAppointmentDto>();
		for(FishingAppointment fishingAppointment : fishingAppointmentRepository.findAll())
		{
			if(fishingAppointment.client != null && fishingAppointment.client.id == id && fishingAppointment.appointmentStart.isAfter(LocalDateTime.now())) {
				ReservedFishingAppointmentDto dto = new ReservedFishingAppointmentDto();
				dto.appointment = fishingAppointment;
				if(LocalDateTime.now().isBefore(fishingAppointment.appointmentStart.minusDays(3)))
					dto.dateIsCorrect = true;
				else
					dto.dateIsCorrect = false;
					
				dtos.add(dto);
			}
		}
		return new ResponseEntity<List<ReservedFishingAppointmentDto>>(dtos,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getAllAdvAppointmentsByClient/{clientId}")
	public ResponseEntity<List<FishingAppointment>> getAllAdvAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<FishingAppointment> adventures = fishingAppointmentRepository.findAllAppointmentsByClient(id);
		
		return new ResponseEntity<List<FishingAppointment>>(adventures,HttpStatus.OK);
	}
	

}
