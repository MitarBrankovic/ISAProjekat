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

import com.BookingApp.dto.DateReservationDto;
import com.BookingApp.dto.ReserveAdventureDto;
import com.BookingApp.dto.ReserveBoatDto;
import com.BookingApp.dto.ReservedBoatAppointmentDto;
import com.BookingApp.dto.SearchAppointmentDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Client;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.model.LoyaltyStatus;
import com.BookingApp.model.ShipOwner;
import com.BookingApp.repository.BoatAppointmentRepository;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.LoyaltyProgramRepository;
import com.BookingApp.repository.UserRepository;

@RestController
@RequestMapping("/boatAppointments")
public class BoatAppointmentService {
	@Autowired
	private BoatAppointmentRepository boatAppointmentRepository;
	@Autowired
	private BoatRepository boatRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private LoyaltyProgramRepository loyaltyRepository;
	@Autowired
	private UserRepository userRepository;
	
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
			addLoyaltyPoints(client, appointment.boat.shipOwner);
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
			removeLoyaltyPoints(appointment.client, appointment.boat.shipOwner);
			return true;
		}
		return false;
	}
	
	private void addLoyaltyPoints(Client client, ShipOwner owner) {
		List <AppUser> users = new ArrayList<AppUser>();
		for(AppUser au: userRepository.findAll()) {
			if (au.id == client.id) {
				au.loyaltyPoints += calculateClientLoyalty(client);
			}
			else if (au.id == owner.id) {
				au.loyaltyPoints += calculateOwnerLoyalty(owner);
			}
			au.loyaltyStatus = updateLoyaltyStatus(au.loyaltyPoints);
		users.add(au);
		}
		userRepository.saveAll(users);
	}
	
	private void removeLoyaltyPoints(Client client, ShipOwner owner) {
		List <AppUser> users = new ArrayList<AppUser>();
		for(AppUser au: userRepository.findAll()) {
			if (au.id == client.id) {
				au.loyaltyPoints -= calculateClientLoyalty(client);
			}
			else if (au.id == owner.id) {
				au.loyaltyPoints -= calculateOwnerLoyalty(owner);
			}
			au.loyaltyStatus = updateLoyaltyStatus(au.loyaltyPoints);
		users.add(au);
		}
		userRepository.saveAll(users);
	}
	
	private double calculateClientLoyalty(Client client) {
		if (client.loyaltyStatus == LoyaltyStatus.silver)
			return loyaltyRepository.getLoyalty().silverClient;
		else if (client.loyaltyStatus == LoyaltyStatus.gold)
			return loyaltyRepository.getLoyalty().goldClient;
		else
			return loyaltyRepository.getLoyalty().bronzeClient;
	}
	
	private double calculateOwnerLoyalty(ShipOwner owner) {
		if (owner.loyaltyStatus == LoyaltyStatus.silver)
			return loyaltyRepository.getLoyalty().silverClient;
		else if (owner.loyaltyStatus == LoyaltyStatus.gold)
			return loyaltyRepository.getLoyalty().goldClient;
		else
			return loyaltyRepository.getLoyalty().bronzeClient;
	}
	
	private LoyaltyStatus updateLoyaltyStatus(double loyaltyPoints) {
		LoyaltyProgram loyalty = loyaltyRepository.getLoyalty();
		if (loyaltyPoints < loyalty.bronzePoints)
			return LoyaltyStatus.regular;
		else if (loyaltyPoints < loyalty.silverPoints)
			return LoyaltyStatus.bronze;
		else if (loyaltyPoints < loyalty.goldPoints)
			return LoyaltyStatus.silver;
		else
			return LoyaltyStatus.gold;
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
	
	@GetMapping(path = "/getFinishedBoatsByClient/{clientId}")
	public ResponseEntity<List<Boat>> getFinishedBoatAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<BoatAppointment> appointments = new ArrayList<BoatAppointment>();
		List<Boat> boats = new ArrayList<Boat>();
		for(BoatAppointment boatAppointment : boatAppointmentRepository.findAll())
		{
			if(boatAppointment.client != null && boatAppointment.client.id == id && boatAppointment.appointmentStart.isBefore(LocalDateTime.now())) {
				appointments.add(boatAppointment);	
				boats.add(boatAppointment.boat);
			}
		}
		return new ResponseEntity<List<Boat>>(boats,HttpStatus.OK);
	}
	
	@PostMapping(path = "/getAllFreeBoats")
	public ResponseEntity<List<Boat>> getAllFreeBoats(@RequestBody DateReservationDto dateReservationDto)
	{	
		LocalDateTime datePick = dateReservationDto.datePick.atStartOfDay().plusHours(dateReservationDto.time);
		
		boolean exist;
		List<Boat> boats = new ArrayList<Boat>();
		for(Boat boat: boatRepository.findAll()) {
			exist = false;
			for(BoatAppointment fishingAppointment : boatAppointmentRepository.findAll()) {
				LocalDateTime start = fishingAppointment.appointmentStart;
				LocalDateTime end = fishingAppointment.appointmentStart.plusHours(fishingAppointment.duration);
				LocalDateTime datePickEnd = datePick.plusHours(24);
				if( (((datePick.isAfter(start) && datePick.isBefore(end)) || datePick.isEqual(start) || datePick.isEqual(end))		
						|| ((datePickEnd.isAfter(start) && datePickEnd.isBefore(end))) || datePickEnd.isEqual(start) || datePickEnd.isEqual(end))
						&& boat.id == fishingAppointment.boat.id) {
					exist = true;
					break;
				}				
			}
			if(!exist) {
				boats.add(boat);
			}	
		}	
		return new ResponseEntity<List<Boat>>(boats,HttpStatus.OK);
	}
	
	@PostMapping(path = "/reserveBoat")
	public boolean reserveAdventure(@RequestBody ReserveBoatDto reserveBoatDto)
	{	
		BoatAppointment appointment = new BoatAppointment(reserveBoatDto.datePick.atStartOfDay().plusHours(reserveBoatDto.time), 24, 4, AppointmentType.regular, 
				reserveBoatDto.additionalPricingText, reserveBoatDto.totalPrice, reserveBoatDto.boat, reserveBoatDto.client);

		boatAppointmentRepository.save(appointment);
		addLoyaltyPoints(appointment.client, appointment.boat.shipOwner);
		return true;
	}
	
	@PostMapping(path = "/searchBoatAppointments")
	public ResponseEntity<List<BoatAppointment>> searchBoatAppointments(@RequestBody SearchAppointmentDto dto)
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
		

		List<BoatAppointment> appointments = boatAppointmentRepository.findAllAppointmentsByClient(userId);

		if (name.equals("") && owner.equals(""))
			appointments = boatAppointmentRepository.findAllAppointmentsByClient(userId);
		
		if (!name.equals("")) {
			appointments =  appointments.stream().filter(m -> m.boat.name.toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!owner.equals("")) {
			appointments =  appointments.stream().filter(m -> (m.boat.shipOwner.name + " " + m.boat.shipOwner.surname).toLowerCase().contains(owner.toLowerCase()))
					.collect(Collectors.toList()); }
			
		if (nameAsc) {
			int n = appointments.size();
			BoatAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).boat.name
							.compareTo(appointments.get(j).boat.name) > 0) {
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
			BoatAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).boat.name
							.compareTo(appointments.get(j).boat.name) < 0) {
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
			BoatAppointment temp = null;
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
			BoatAppointment temp = null;
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
			BoatAppointment temp = null;
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
			BoatAppointment temp = null;
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
			BoatAppointment temp = null;
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
			BoatAppointment temp = null;
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
		
		return new ResponseEntity<List<BoatAppointment>>(appointments,HttpStatus.OK);
	}
}
