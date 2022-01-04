package com.BookingApp.service;

import java.time.Duration;
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
import com.BookingApp.dto.SearchAppointmentDto;
import com.BookingApp.dto.DateReservationDto;
import com.BookingApp.dto.FishingAppointmentDto;
import com.BookingApp.dto.ReserveAdventureDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Client;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingAppointmentReport;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.FishingAppointmentRepository;
import com.BookingApp.repository.FishingInstructorRepository;
import com.BookingApp.repository.FishingReportsRepository;
import com.BookingApp.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fishingAppointments")
public class FishingAppointmentService {

	@Autowired
	private FishingAppointmentRepository fishingAppointmentRepository;
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private FishingReportsRepository fishingReportsRepository;

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
	
	@GetMapping(path = "/getReservationsForReports/{instructorsId}")
	public ResponseEntity<List<FishingAppointment>> getInstructorsReservationsHistory(@PathVariable("instructorsId") long id)
	{
		List<FishingAppointment> appointments = new ArrayList<FishingAppointment>();
		for(FishingAppointment appointment : fishingAppointmentRepository.findInstructorsReservationHistory(id))
		{
			if (appointment.client != null && appointment.appointmentStart.plusHours(appointment.duration).isBefore(LocalDateTime.now()) && !checkIfReportExists(appointment.id))
				appointments.add(appointment);
		}
		return new ResponseEntity<List<FishingAppointment>>(appointments,HttpStatus.OK);
	}
	
	private boolean checkIfReportExists(long id) {
		for(FishingAppointmentReport rep : fishingReportsRepository.findAll()) {
			if (rep.appointment.id == id) {
				return true;
			}
		}
		return false;
	}
	
	@GetMapping(path = "/getReservationsHistory/{instructorsId}")
	public ResponseEntity<List<FishingAppointment>> getInstructorsReservationsForReport(@PathVariable("instructorsId") long id)
	{
		List<FishingAppointment> appointments = new ArrayList<FishingAppointment>();
		for(FishingAppointment appointment : fishingAppointmentRepository.findInstructorsReservationHistory(id))
		{
			if (appointment.client != null)
				appointments.add(appointment);
		}
		return new ResponseEntity<List<FishingAppointment>>(appointments,HttpStatus.OK);
	}

	@PostMapping(path = "/addQuickAppointment")
    public ResponseEntity<List<FishingAppointment>> addAppointment(@RequestBody FishingAppointmentDto appointmentDTO)
	{	
		if(appointmentDTO != null) {
			FishingAppointment appointment = new FishingAppointment(appointmentDTO.formatDateFrom(), appointmentDTO.address, appointmentDTO.city, 
											 appointmentDTO.durationInHours(), appointmentDTO.maxAmountOfPeople, AppointmentType.quick, true, 0,
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
			if(appointment.appointmentType == AppointmentType.quick) {
				appointment.client = null;
				fishingAppointmentRepository.save(appointment);
			}else if(appointment.appointmentType == AppointmentType.regular) {
				fishingAppointmentRepository.delete(appointment);
			}
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
	
	@GetMapping(path = "/getFinishedAdventuresByClient/{clientId}")
	public ResponseEntity<List<FishingAdventure>> getFinishedBoatAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<FishingAppointment> appointments = new ArrayList<FishingAppointment>();
		List<FishingAdventure> adventures = new ArrayList<FishingAdventure>();
		for(FishingAppointment fishingAppointment : fishingAppointmentRepository.findAll())
		{
			if(fishingAppointment.client != null && fishingAppointment.client.id == id && fishingAppointment.appointmentStart.isBefore(LocalDateTime.now())) {
				appointments.add(fishingAppointment);	
				adventures.add(fishingAppointment.fishingAdventure);
			}
		}
		return new ResponseEntity<List<FishingAdventure>>(adventures,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getAllAdvAppointmentsByClient/{clientId}")
	public ResponseEntity<List<FishingAppointment>> getAllAdvAppointmentsByClient(@PathVariable("clientId") long id)
	{	
		List<FishingAppointment> adventures = fishingAppointmentRepository.findAllAppointmentsByClient(id);
		
		return new ResponseEntity<List<FishingAppointment>>(adventures,HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/getAllFreeAdventures")
	public ResponseEntity<List<FishingAdventure>> getAllFreeAdventures(@RequestBody DateReservationDto dateReservationDto)
	{	
		LocalDateTime datePick = dateReservationDto.datePick.atStartOfDay().plusHours(dateReservationDto.time); //.plusHours(dateReservationDto.time);
		
		boolean exist;
		List<FishingAdventure> adventures = new ArrayList<FishingAdventure>();
		for(FishingAdventure adventure: fishingAdventureRepository.findAll()) {
			exist = false;
			for(FishingAppointment fishingAppointment : fishingAppointmentRepository.findAll()) {
				LocalDateTime start = fishingAppointment.appointmentStart;
				LocalDateTime end = fishingAppointment.appointmentStart.plusHours(fishingAppointment.duration);
				LocalDateTime datePickEnd = datePick.plusHours(fishingAppointment.duration);
				if( (((datePick.isAfter(start) && datePick.isBefore(end)) || datePick.isEqual(start) || datePick.isEqual(end))		
						|| ((datePickEnd.isAfter(start) && datePickEnd.isBefore(end))) || datePickEnd.isEqual(start) || datePickEnd.isEqual(end))
						&& adventure.fishingInstructor.id == fishingAppointment.fishingAdventure.fishingInstructor.id) {
					exist = true;
					break;
				}				
			}
			if(!exist) {
				adventures.add(adventure);
			}	
		}	
		return new ResponseEntity<List<FishingAdventure>>(adventures,HttpStatus.OK);
	}
	
	@PostMapping(path = "/reserveAdventure")
	public boolean reserveAdventure(@RequestBody ReserveAdventureDto reserveAdventureDto)
	{	
		/*FishingAppointment fishingAppointment = new FishingAppointment();
		fishingAppointment.appointmentStart = reserveAdventureDto.datePick.atStartOfDay().plusHours(reserveAdventureDto.time);
		fishingAppointment.duration = 3;
		fishingAppointment.appointmentType = AppointmentType.regular;
		fishingAppointment.client = reserveAdventureDto.client;
		fishingAppointment.extraNotes = reserveAdventureDto.additionalPricingText;
		fishingAppointment.fishingAdventure = reserveAdventureDto.fishingAdventure;
		fishingAppointment.maxAmountOfPeople = 5;
		fishingAppointment.price = reserveAdventureDto.totalPrice;*/
		
		FishingAppointment appointment = new FishingAppointment(reserveAdventureDto.datePick.atStartOfDay().plusHours(reserveAdventureDto.time), reserveAdventureDto.fishingAdventure.address, reserveAdventureDto.fishingAdventure.city, 
				 3, 5, AppointmentType.regular, false, 0,reserveAdventureDto.additionalPricingText, reserveAdventureDto.totalPrice);
		appointment.client = reserveAdventureDto.client;
		appointment.fishingAdventure = reserveAdventureDto.fishingAdventure;

		fishingAppointmentRepository.save(appointment);
		
		return true;
	}
	
	
	@PostMapping(path = "/searchAdvAppointments")
	public ResponseEntity<List<FishingAppointment>> searchAdvAppointments(@RequestBody SearchAppointmentDto dto)
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
		

		List<FishingAppointment> appointments = fishingAppointmentRepository.findAllAppointmentsByClient(userId);

		if (name.equals("") && owner.equals(""))
			appointments = fishingAppointmentRepository.findAllAppointmentsByClient(userId);
		
		if (!name.equals("")) {
			appointments =  appointments.stream().filter(m -> m.fishingAdventure.name.toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!owner.equals("")) {
			appointments =  appointments.stream().filter(m -> (m.fishingAdventure.fishingInstructor.name + " " + m.fishingAdventure.fishingInstructor.surname).toLowerCase().contains(owner.toLowerCase()))
					.collect(Collectors.toList()); }
			
		if (nameAsc) {
			int n = appointments.size();
			FishingAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).fishingAdventure.name
							.compareTo(appointments.get(j).fishingAdventure.name) > 0) {
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
			FishingAppointment temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (appointments.get(j - 1).fishingAdventure.name
							.compareTo(appointments.get(j).fishingAdventure.name) < 0) {
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
			FishingAppointment temp = null;
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
			FishingAppointment temp = null;
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
			FishingAppointment temp = null;
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
			FishingAppointment temp = null;
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
			FishingAppointment temp = null;
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
			FishingAppointment temp = null;
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
		
		return new ResponseEntity<List<FishingAppointment>>(appointments,HttpStatus.OK);
	}

}
