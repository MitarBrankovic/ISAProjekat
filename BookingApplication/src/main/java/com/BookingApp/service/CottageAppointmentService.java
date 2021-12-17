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

import com.BookingApp.dto.CottageReservedAppointmentDto;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.Client;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.CottageOwner;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageOwnerRepository;
import com.BookingApp.repository.CottageRepository;


@RestController
@RequestMapping("/cottageAppointments")
public class CottageAppointmentService {
	@Autowired
	private CottageAppointmentRepository cottageAppointmentRepository;
	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private ClientRepository clientRepository;
	
	@GetMapping(path = "/getAllQuickAppointments/{cottageId}")
	public ResponseEntity<List<CottageAppointment>> getAllQuickAppointmentsForCottage(@PathVariable("cottageId") long id)
	{	
		List<CottageAppointment> appointments = new ArrayList<CottageAppointment>();
		for(CottageAppointment ca : cottageAppointmentRepository.findAll())
		{
			if(ca.cottage.id == id && ca.appointmentStart.isAfter(LocalDateTime.now()) && 
					 ca.appointmentType == AppointmentType.quick) {
				appointments.add(ca);
			}
		}
		return new ResponseEntity<List<CottageAppointment>>(appointments,HttpStatus.OK);
	}
	
	@PutMapping(path = "/scheduleCottageAppointment/{cottageId}/{userId}")
	public boolean scheduleCottageAppointment(@PathVariable("cottageId") long id, @PathVariable("userId") long userId)
	{
		Optional<CottageAppointment> oldAppointment = cottageAppointmentRepository.findById(id);
		Client client = new Client();
		for(Client oldClient : clientRepository.findAll()) {
			if(oldClient.id == userId) {
				client = oldClient;
			}
		}
		
		if(oldAppointment.isPresent()) {
			CottageAppointment appointment = oldAppointment.get();
			appointment.client = client;
			cottageAppointmentRepository.save(appointment);
			return true;
		}
		return false;
	}
	
	@PutMapping(path = "/cancelCottageAppointment/{cottageId}")
	public boolean cancelCottageAppointment(@PathVariable("cottageId") long id)
	{
		Optional<CottageAppointment> oldAppointment = cottageAppointmentRepository.findById(id);

		if(oldAppointment.isPresent()) {
			CottageAppointment appointment = oldAppointment.get();
			appointment.client = null;
			cottageAppointmentRepository.save(appointment);
			return true;
		}
		return false;
	}
	
	@GetMapping(path = "/getReservedCottAppointmentsByClient/{clientId}")
	public ResponseEntity<List<CottageReservedAppointmentDto>> getReservedCottAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<CottageReservedAppointmentDto> dtos = new ArrayList<CottageReservedAppointmentDto>();
		for(CottageAppointment cottageAppointment : cottageAppointmentRepository.findAll())
		{
			if(cottageAppointment.client != null && cottageAppointment.client.id == id && cottageAppointment.appointmentStart.isAfter(LocalDateTime.now())) {
				CottageReservedAppointmentDto dto = new CottageReservedAppointmentDto();
				dto.appointment = cottageAppointment;
				if(LocalDateTime.now().isBefore(cottageAppointment.appointmentStart.minusDays(3)))
					dto.dateIsCorrect = true;
				else
					dto.dateIsCorrect = false;
					
				dtos.add(dto);
			}
		}
		return new ResponseEntity<List<CottageReservedAppointmentDto>>(dtos,HttpStatus.OK);
	}
}
