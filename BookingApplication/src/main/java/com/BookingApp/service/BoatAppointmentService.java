package com.BookingApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.ReservedBoatAppointmentDto;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Client;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.repository.BoatAppointmentRepository;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.ClientRepository;

@RestController
@RequestMapping("/boatAppointments")
public class BoatAppointmentService {
	@Autowired
	private BoatAppointmentRepository boatAppointmentRepository;
	@Autowired
	private BoatRepository boatRepository;
	@Autowired
	private ClientRepository clientRepository;
	
	@GetMapping(path = "/getAllQuickAppointments/{boatId}")
	public ResponseEntity<List<BoatAppointment>> getAllQuickAppointmentsForBoat(@PathVariable("boatId") long id)
	{	
		List<BoatAppointment> appointments = new ArrayList<BoatAppointment>();
		for(BoatAppointment ca : boatAppointmentRepository.findAll())
		{
			if(ca.boat.id == id && ca.appointmentStart.isAfter(LocalDateTime.now()) && 
					 ca.appointmentType == AppointmentType.quick) {
				appointments.add(ca);
			}
		}
		return new ResponseEntity<List<BoatAppointment>>(appointments,HttpStatus.OK);
	}
	
	
	@PutMapping(path = "/scheduleBoatAppointment/{boatId}/{userId}")
	public boolean scheduleAdventureAppointment(@PathVariable("boatId") long id, @PathVariable("userId") long userId)
	{
		Optional<BoatAppointment> oldAppointment = boatAppointmentRepository.findById(id);
		Client client = new Client();
		for(Client oldClient : clientRepository.findAll()) {
			if(oldClient.id == userId) {
				client = oldClient;
			}
		}
		
		if(oldAppointment.isPresent()) {
			BoatAppointment appointment = oldAppointment.get();
			appointment.client = client;
			boatAppointmentRepository.save(appointment);
			return true;
		}
		return false;
	}
	
	@PutMapping(path = "/cancelBoatAppointment/{boatId}")
	public boolean cancelBoatAppointment(@PathVariable("boatId") long id)
	{
		Optional<BoatAppointment> oldAppointment = boatAppointmentRepository.findById(id);

		if(oldAppointment.isPresent()) {
			BoatAppointment appointment = oldAppointment.get();
			appointment.client = null;
			boatAppointmentRepository.save(appointment);
			return true;
		}
		return false;
	}
	
	
	@GetMapping(path = "/getReservedBoatAppointmentsByClient/{clientId}")
	public ResponseEntity<List<ReservedBoatAppointmentDto>> getReservedBoatAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<ReservedBoatAppointmentDto> dtos = new ArrayList<ReservedBoatAppointmentDto>();
		for(BoatAppointment boatAppointment : boatAppointmentRepository.findAll())
		{
			if(boatAppointment.client != null && boatAppointment.client.id == id && boatAppointment.appointmentStart.isAfter(LocalDateTime.now())) {
				ReservedBoatAppointmentDto dto = new ReservedBoatAppointmentDto();
				dto.appointment = boatAppointment;
				if(LocalDateTime.now().isBefore(boatAppointment.appointmentStart.minusDays(3)))
					dto.dateIsCorrect = true;
				else
					dto.dateIsCorrect = false;
					
				dtos.add(dto);
			}
		}
		return new ResponseEntity<List<ReservedBoatAppointmentDto>>(dtos,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getAllBoatAppointmentsByClient/{clientId}")
	public ResponseEntity<List<BoatAppointment>> getAllBoatAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<BoatAppointment> boats = boatAppointmentRepository.findAllAppointmentsByClient(id);
		
		return new ResponseEntity<List<BoatAppointment>>(boats,HttpStatus.OK);
	}
}
