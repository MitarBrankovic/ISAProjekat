package com.BookingApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.DateReservationDto;
import com.BookingApp.dto.ReserveAdventureDto;
import com.BookingApp.dto.ReserveCottageDto;
import com.BookingApp.dto.ReservedCottageAppointmentDto;
import com.BookingApp.dto.SearchAppointmentDto;
import com.BookingApp.dto.SearchDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.Client;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.CottageOwner;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.repository.BoatReportsRepository;
import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.model.LoyaltyStatus;
import com.BookingApp.model.ShipOwner;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageOwnerRepository;
import com.BookingApp.repository.CottageReportsRepository;
import com.BookingApp.repository.CottageRepository;
import com.BookingApp.repository.FishingReportsRepository;
import com.BookingApp.repository.LoyaltyProgramRepository;
import com.BookingApp.repository.UserRepository;
import com.BookingApp.service2.CottageAppointmentService2;

@CrossOrigin
@RestController
@Controller
@RequestMapping("/cottageAppointments")
public class CottageAppointmentService {
	@Autowired
	private CottageAppointmentRepository cottageAppointmentRepository;
	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private FishingReportsRepository fishingReportsRepository;
	@Autowired
	private BoatReportsRepository boatReportsRepository;
	@Autowired
	private CottageReportsRepository cottageReportsRepository;
	@Autowired
	private LoyaltyProgramRepository loyaltyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CottageAppointmentService2 cottageAppointmentService2;
	
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
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean scheduleCottageAppointment(@PathVariable("cottageId") long id, @PathVariable("userId") long userId) throws Exception
	{
		return cottageAppointmentService2.scheduleIt(id, userId);
	}
	
	@PutMapping(path = "/cancelCottageAppointment/{cottageId}")
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean cancelCottageAppointment(@PathVariable("cottageId") long id)
	{
		Optional<CottageAppointment> oldAppointment = cottageAppointmentRepository.findById(id);

		if(oldAppointment.isPresent()) {
			CottageAppointment appointment = oldAppointment.get();
			cottageAppointmentService2.removeLoyaltyPoints(appointment.client, appointment.cottage.cottageOwner);
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
				dto.end = dto.appointment.appointmentStart.plusHours(dto.appointment.duration);
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
	
	
	@GetMapping(path = "/getFinishedCottagesByClient/{clientId}")
	public ResponseEntity<List<Cottage>> getFinishedCottAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<CottageAppointment> appointments = new ArrayList<CottageAppointment>();
		List<Cottage> cottages = new ArrayList<Cottage>();
		for(CottageAppointment cottageAppointment : cottageAppointmentRepository.findAll())
		{
			if(cottageAppointment.client != null && cottageAppointment.client.id == id && cottageAppointment.appointmentStart.isBefore(LocalDateTime.now())) {
				appointments.add(cottageAppointment);
				cottages.add(cottageAppointment.cottage);
			}
		}
		return new ResponseEntity<List<Cottage>>(cottages,HttpStatus.OK);
	}
	
	@PostMapping(path = "/getAllFreeCottages")
	public ResponseEntity<List<Cottage>> getAllFreeCottages(@RequestBody DateReservationDto dateReservationDto)
	{	
		LocalDateTime datePick = dateReservationDto.datePick.atStartOfDay().plusHours(dateReservationDto.time);
		
		boolean exist;
		List<Cottage> cottages = new ArrayList<Cottage>();
		for(Cottage cottage: cottageRepository.findAll()) {
			exist = false;
			for(CottageAppointment cottageAppointment : cottageAppointmentRepository.findAll()) {
				LocalDateTime start = cottageAppointment.appointmentStart;
				LocalDateTime end = cottageAppointment.appointmentStart.plusHours(cottageAppointment.duration);
				LocalDateTime datePickEnd = datePick.plusDays(dateReservationDto.day);
				if( ((((datePick.isAfter(start) && datePick.isBefore(end)) || datePick.isEqual(start) || datePick.isEqual(end))	
						|| ((datePickEnd.isAfter(start) && datePickEnd.isBefore(end)) || datePickEnd.isEqual(start) || datePickEnd.isEqual(end))
						|| (start.isAfter(datePick) && start.isBefore(datePickEnd))
						|| (end.isAfter(datePick) && end.isBefore(datePickEnd)))
						&& cottage.id == cottageAppointment.cottage.id)
						|| cottage.maxAmountOfPeople <= dateReservationDto.num) {
					exist = true;
					break;
				}				
			}
			if(!exist) {
				cottages.add(cottage);
			}	
		}
		return new ResponseEntity<List<Cottage>>(cottages,HttpStatus.OK);
	}
	
	@PostMapping(path = "/reserveCottage")
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean reserveCottage(@RequestBody ReserveCottageDto reserveCottageDto) throws Exception
	{	
		return cottageAppointmentService2.reserveCottage(reserveCottageDto);
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
