package com.BookingApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.BookingApp.dto.ReservedCottageAppointmentDto;
import com.BookingApp.dto.SearchAppointmentDto;
import com.BookingApp.dto.SearchDto;
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
	public ResponseEntity<List<ReservedCottageAppointmentDto>> getReservedCottAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<ReservedCottageAppointmentDto> dtos = new ArrayList<ReservedCottageAppointmentDto>();
		for(CottageAppointment cottageAppointment : cottageAppointmentRepository.findAll())
		{
			if(cottageAppointment.client != null && cottageAppointment.client.id == id && cottageAppointment.appointmentStart.isAfter(LocalDateTime.now())) {
				ReservedCottageAppointmentDto dto = new ReservedCottageAppointmentDto();
				dto.appointment = cottageAppointment;
				if(LocalDateTime.now().isBefore(cottageAppointment.appointmentStart.minusDays(3)))
					dto.dateIsCorrect = true;
				else
					dto.dateIsCorrect = false;
					
				dtos.add(dto);
			}
		}
		return new ResponseEntity<List<ReservedCottageAppointmentDto>>(dtos,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getAllCottAppointmentsByClient/{clientId}")
	public ResponseEntity<List<CottageAppointment>> getAllCottAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<CottageAppointment> appointments = cottageAppointmentRepository.findAllAppointmentsByClient(id);

		return new ResponseEntity<List<CottageAppointment>>(appointments,HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/searchCottAppointments")
	public ResponseEntity<List<CottageAppointment>> searchCottAppointments(@RequestBody SearchAppointmentDto dto)
	{
		String name = dto.name;
		String owner = dto.owner;
	    boolean nameAsc = dto.nameAsc;
	    boolean nameDesc = dto.nameAsc;
	    boolean dateAsc = dto.dateAsc;
	    boolean dateDesc = dto.dateDesc;
	    boolean durationAsc = dto.durationAsc;
	    boolean durationDesc = dto.durationDesc;
	    boolean priceAsc = dto.priceAsc;
	    boolean priceDesc = dto.priceDesc;
	    long userId = dto.activeUserId;
		

		List<CottageAppointment> appointments = cottageAppointmentRepository.findAllAppointmentsByClient(userId);

		if (name.equals("") && owner.equals(""))
			appointments = cottageAppointmentRepository.findAllAppointmentsByClient(userId);
		
		if (!name.equals("")) {
			appointments =  appointments.stream().filter(m -> m.cottage.name.toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!owner.equals("")) {
			appointments =  appointments.stream().filter(m -> (m.cottage.cottageOwner.name + " " + m.cottage.cottageOwner.surname).toLowerCase().contains(owner.toLowerCase()))
					.collect(Collectors.toList()); }
			
		if (nameAsc) {
			int n = appointments.size();
			CottageAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).cottage.name
							.compareTo(appointments.get(j).cottage.name) > 0) {
						// swap elements
						temp = appointments.get(j - 1);
						appointments.set(j - 1, appointments.get(j));
						appointments.set(j, temp);
					}

				}
			}
		}
		
		
		if (nameDesc) {
			int n = appointments.size();
			CottageAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).cottage.name
							.compareTo(appointments.get(j).cottage.name) < 0) {
						// swap elements
						temp = appointments.get(j - 1);
						appointments.set(j - 1, appointments.get(j));
						appointments.set(j, temp);
					}

				}
			}
		}

		if (dateAsc) {
			int n = appointments.size();
			CottageAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).appointmentStart.isAfter(appointments.get(j).appointmentStart)) {
						// swap elements
						temp = appointments.get(j - 1);
						appointments.set(j - 1, appointments.get(j));
						appointments.set(j, temp);
					}

				}
			}
		}
		if (dateDesc) {
			int n = appointments.size();
			CottageAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).appointmentStart.isBefore(appointments.get(j).appointmentStart)) {
						// swap elements
						temp = appointments.get(j - 1);
						appointments.set(j - 1, appointments.get(j));
						appointments.set(j, temp);
					}

				}
			}
		}
		
		if (durationAsc) {
			int n = appointments.size();
			CottageAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).duration > appointments.get(j).duration) {
						// swap elements
						temp = appointments.get(j - 1);
						appointments.set(j - 1, appointments.get(j));
						appointments.set(j, temp);
					}

				}
			}
		}			
		if (durationDesc) {
			int n = appointments.size();
			CottageAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).duration < appointments.get(j).duration) {
						// swap elements
						temp = appointments.get(j - 1);
						appointments.set(j - 1, appointments.get(j));
						appointments.set(j, temp);
					}

				}
			}
		}
		
		
		if (priceAsc) {
			int n = appointments.size();
			CottageAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).price > appointments.get(j).price) {
						// swap elements
						temp = appointments.get(j - 1);
						appointments.set(j - 1, appointments.get(j));
						appointments.set(j, temp);
					}

				}
			}
		}			
		if (priceDesc) {
			int n = appointments.size();
			CottageAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).price < appointments.get(j).price) {
						// swap elements
						temp = appointments.get(j - 1);
						appointments.set(j - 1, appointments.get(j));
						appointments.set(j, temp);
					}

				}
			}
		}
		
		return new ResponseEntity<List<CottageAppointment>>(appointments,HttpStatus.OK);
	}
}
