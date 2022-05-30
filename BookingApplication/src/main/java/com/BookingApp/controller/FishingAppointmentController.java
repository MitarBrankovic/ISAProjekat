package com.BookingApp.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.ReservedFishingAppointmentDto;
import com.BookingApp.dto.SearchAppointmentDto;
import com.BookingApp.dto.AppointmentReportDto;
import com.BookingApp.dto.ChartInfoDto;
import com.BookingApp.dto.DateReservationDto;
import com.BookingApp.dto.FishingAppointmentDto;
import com.BookingApp.dto.InstructorsAppointmentForClientDto;
import com.BookingApp.dto.ReserveAdventureDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Client;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingAppointmentReport;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.model.LoyaltyStatus;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.model.SubscribeAdventure;
import com.BookingApp.repository.BoatReportsRepository;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageReportsRepository;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.FishingAppointmentRepository;
import com.BookingApp.repository.FishingInstructorRepository;
import com.BookingApp.repository.FishingReportsRepository;
import com.BookingApp.repository.LoyaltyProgramRepository;
import com.BookingApp.repository.SubscribeAdvRepository;
import com.BookingApp.repository.UserRepository;
import com.BookingApp.service.FishingAppointmentService;

import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/fishingAppointments")
public class FishingAppointmentController {

	@Autowired
	private FishingAppointmentRepository fishingAppointmentRepository;
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private FishingReportsRepository fishingReportsRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private SubscribeAdvRepository subscribeFishingRepository;
	@Autowired
	private BoatReportsRepository boatReportsRepository;
	@Autowired
	private CottageReportsRepository cottageReportsRepository;
	@Autowired
	private LoyaltyProgramRepository loyaltyRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FishingAppointmentService fishingAppointmentService2;
	

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
	
	@GetMapping(path = "/getCurrentAppointments/{instructorsId}")
	public ResponseEntity<List<FishingAppointment>> getCurrentAppointments(@PathVariable("instructorsId") long id)
	{
		List<FishingAppointment> appointments = new ArrayList<FishingAppointment>();
		for(FishingAppointment appointment : fishingAppointmentRepository.findInstructorsReservationHistory(id))
		{
			if (appointment.client != null && LocalDateTime.now().isAfter(appointment.appointmentStart) && LocalDateTime.now().isBefore(appointment.appointmentStart.plusHours(appointment.duration)))
				appointments.add(appointment);
		}
		return new ResponseEntity<List<FishingAppointment>>(appointments,HttpStatus.OK);
	}

	@PostMapping(path = "/addQuickAppointment")
    public ResponseEntity<List<FishingAppointment>> addAppointment(@RequestBody FishingAppointmentDto appointmentDTO)
	{	
		FishingAdventure adventure = fishingAdventureRepository.findById(appointmentDTO.adventureId).get();
		if(appointmentDTO != null) {
			FishingAppointment appointment = new FishingAppointment(appointmentDTO.formatDateFrom(), adventure.address, adventure.city, 
											 appointmentDTO.durationInHours(), adventure.maxAmountOfPeople, AppointmentType.quick, true, 0,
											 appointmentDTO.extraNotes, appointmentDTO.price);
			appointment.fishingAdventure = getAdventureById(appointmentDTO.adventureId);
			fishingAppointmentRepository.save(appointment);
			sendEmailToSubscribers(appointmentDTO);
			return getAdventureQuickAppointments(appointmentDTO.adventureId);
		}
		return null;
	}
	
	@PostMapping(path = "/addInstructorsAppointmentForClient")
    public ResponseEntity<List<FishingAppointment>> addClientAppointment(@RequestBody InstructorsAppointmentForClientDto appointmentDTO)
	{	
		FishingAdventure adventure = fishingAdventureRepository.findById(appointmentDTO.adventureId).get();
		if(appointmentDTO != null) {
			FishingAppointment appointment = new FishingAppointment(appointmentDTO.formatDateFrom(), adventure.address, adventure.city, 
											 appointmentDTO.durationInHours(), adventure.maxAmountOfPeople, AppointmentType.regular, false, 0,
											 appointmentDTO.extraNotes, appointmentDTO.price);
			appointment.fishingAdventure = getAdventureById(appointmentDTO.adventureId);
			appointment.client = clientRepository.findById(appointmentDTO.clientId).get();
			appointment.price = fishingAppointmentService2.calculateDiscountedPrice(appointment.price, appointment.client);
			double ownerCut = fishingAppointmentService2.getOwnerProfit(appointment.fishingAdventure.fishingInstructor);
			appointment.instructorProfit = appointment.price*ownerCut/100;
			appointment.systemProfit = appointment.price - appointment.instructorProfit;
			fishingAppointmentRepository.save(appointment);
			sendEmailToClient(appointment);
			return getAdventureQuickAppointments(appointmentDTO.adventureId);
		}
		return null;
	}
	
	public boolean sendEmailToClient(FishingAppointment app)
	{	
		FishingAdventure adventure = fishingAdventureRepository.findById(app.fishingAdventure.id).get();
		String title = "New reservation notification";
		String body = "Hello,\nYour reservation requested from the instructor has been successful - " + adventure.name + " - " + adventure.address + ", " + adventure.city + " . \n"
					+ "\nAction information:\n"
					+	"\nStart : " + app.appointmentStart + "\n"
					+	"\nDuration : " + app.duration + " hours \n"
					+	"\nCapacity : " + adventure.maxAmountOfPeople +" people \n"
					+	"\nPrice : " + app.price +" din\n"
					+	"\nExtras : " + app.extraNotes +"\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(app.client.email,body,title);	
					}
				};
				t.start();
				return true;
			} 
			catch (Exception e) 
			{
				return false;
			}
	}
	
	public boolean sendEmailToSubscribers(InstructorsAppointmentForClientDto appDto)
	{	
		FishingAdventure adventure = fishingAdventureRepository.findById(appDto.adventureId).get();
		String title = "New reservation notification";
		String body = "Hello,\nYour reservation requested from the instructor has been successful - " + adventure.name + " - " + adventure.address + ", " + adventure.city + " . \n"
					+ "\nAction information:\n"
					+	"\nStart :" + appDto.dateFrom + " - " + appDto.timeFrom + "\n"
					+	"\nDuration : " + appDto.durationInHours() + " hours \n"
					+	"\nCapacity : " + adventure.maxAmountOfPeople +" people \n"
					+	"\nPrice : " + appDto.price +"\n"
					+	"\nExtras : " + appDto.extraNotes +"\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		List <SubscribeAdventure> subs =  subscribeFishingRepository.findAllByAdventure(appDto.adventureId);
		for (SubscribeAdventure sa : subs) {
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(sa.client.email,body,title);	
					}
				};
				t.start();
			} 
			catch (Exception e) 
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean sendEmailToSubscribers(FishingAppointmentDto appDto)
	{	
		FishingAdventure adventure = fishingAdventureRepository.findById(appDto.adventureId).get();
		String title = "New action notification";
		String body = "Hello,\nThere is a new action reservation available for " + adventure.name + " - " + adventure.address + ", " + adventure.city + " . \n"
					+ "\nAction information:\n"
					+	"\nStart :" + appDto.dateFrom + " - " + appDto.timeFrom + "\n"
					+	"\nDuration : " + appDto.durationInHours() + " hours \n"
					+	"\nCapacity : " + adventure.maxAmountOfPeople +" people \n"
					+	"\nPrice : " + appDto.price +"\n"
					+	"\nExtras : " + appDto.extraNotes +"\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		List <SubscribeAdventure> subs =  subscribeFishingRepository.findAllByAdventure(appDto.adventureId);
		for (SubscribeAdventure sa : subs) {
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(sa.client.email,body,title);	
					}
				};
				t.start();
			} 
			catch (Exception e) 
			{
				return false;
			}
		}
		return true;
	}
	
	public void sendEmail(String to, String body, String title)
	{
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(title);
		msg.setText(body);
		javaMailSender.send(msg);
		System.out.println("Email sent...");
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
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean scheduleAdventureAppointment(@PathVariable("adventureId") long id, @PathVariable("userId") long userId) throws Exception
	{
		return fishingAppointmentService2.scheduleAdventureAppointment(id, userId);
	}
	
	
	@PutMapping(path = "/cancelAdventureAppointment/{adventureId}")
	@PreAuthorize("hasAuthority('CLIENT')")
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
			fishingAppointmentService2.removeLoyaltyPoints(appointment.client, appointment.fishingAdventure.fishingInstructor);
			return true;
		}
		return false;
	}
	
	
	
	@PostMapping(path = "/checkInstructorsAvailability")
    public boolean checkInstructorsAvailability(@RequestBody InstructorsAppointmentForClientDto appointmentDTO)
	{	
		FishingInstructor instructor = fishingAdventureRepository.findById(appointmentDTO.adventureId).get().fishingInstructor;
		LocalDateTime timeFrom = appointmentDTO.formatDateFrom();
		if (timeFrom.isAfter(instructor.availableFrom) && timeFrom.isBefore(instructor.availableUntil) && timeFrom.plusHours(appointmentDTO.durationInHours()).isBefore(instructor.availableUntil))
			return true;
		return false;
	}
	
	@PostMapping(path = "/checkOverlap")
    public boolean checkOverlap(@RequestBody InstructorsAppointmentForClientDto appointmentDTO)
	{	
		LocalDateTime start = appointmentDTO.formatDateFrom();
		LocalDateTime end = appointmentDTO.formatDateFrom().plusHours(appointmentDTO.durationInHours());
		FishingAdventure adventure = fishingAdventureRepository.findById(appointmentDTO.adventureId).get();
		List <FishingAppointment> appointments = fishingAppointmentRepository.findInstructorsReservationHistory(adventure.fishingInstructor.id);
		System.out.println(appointments.size());
		for (FishingAppointment app : appointments) {
			if ((app.appointmentStart.isBefore(start) && app.appointmentStart.plusHours(app.duration).isAfter(start)) ||
					(app.appointmentStart.isBefore(end) && app.appointmentStart.plusHours(app.duration).isAfter(end)) ||
					(app.appointmentStart.isAfter(start) && app.appointmentStart.plusHours(app.duration).isBefore(end)) ||
					app.appointmentStart.isEqual(start) || app.appointmentStart.plusHours(app.duration).isEqual(end)) {
				System.out.println("Fejkara");
				return false;
			}
		}
		return true;
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
				dto.end = dto.appointment.appointmentStart.plusHours(dto.appointment.duration);
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
	
	@GetMapping(path = "/getFishingInstructorsCharts/{instructorId}")
	public ResponseEntity<List<ChartInfoDto>> getChartInfo(@PathVariable("instructorId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<FishingAppointment> appointments = fishingAppointmentRepository.findInstructorsReservationHistory(id);
		boolean dateExists = false;
		for (FishingAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now())) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.instructorProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.instructorProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getFishingInstructorsWeekCharts/{instructorId}")
	public ResponseEntity<List<ChartInfoDto>> getWeekChartInfo(@PathVariable("instructorId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<FishingAppointment> appointments = fishingAppointmentRepository.findInstructorsReservationHistory(id);
		boolean dateExists = false;
		for (FishingAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(7)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now())) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.instructorProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.instructorProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getFishingInstructorsMonthCharts/{instructorId}")
	public ResponseEntity<List<ChartInfoDto>> getMonthChartInfo(@PathVariable("instructorId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<FishingAppointment> appointments = fishingAppointmentRepository.findInstructorsReservationHistory(id);
		boolean dateExists = false;
		for (FishingAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(30)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now())) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.instructorProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.instructorProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getFishingInstructorsYearCharts/{instructorId}")
	public ResponseEntity<List<ChartInfoDto>> getYearChartInfo(@PathVariable("instructorId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<FishingAppointment> appointments = fishingAppointmentRepository.findInstructorsReservationHistory(id);
		boolean dateExists = false;
		for (FishingAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusYears(1)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now())) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.instructorProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.instructorProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/getAllFreeAdventures")
	public ResponseEntity<List<FishingAdventure>> getAllFreeAdventures(@RequestBody DateReservationDto dateReservationDto)
	{	
		LocalDateTime datePick = dateReservationDto.datePick.atStartOfDay().plusHours(dateReservationDto.time);
		
		boolean exist;
		List<FishingAdventure> adventures = new ArrayList<FishingAdventure>();
		for(FishingAdventure adventure: fishingAdventureRepository.findAll()) {
			exist = false;
			for(FishingAppointment fishingAppointment : fishingAppointmentRepository.findAll()) {
				LocalDateTime start = fishingAppointment.appointmentStart;
				LocalDateTime end = fishingAppointment.appointmentStart.plusHours(fishingAppointment.duration);
				LocalDateTime datePickEnd = datePick.plusHours(3);
				if( ((((datePick.isAfter(start) && datePick.isBefore(end)) || datePick.isEqual(start) || datePick.isEqual(end))		
						|| ((datePickEnd.isAfter(start) && datePickEnd.isBefore(end)) || datePickEnd.isEqual(start) || datePickEnd.isEqual(end))
						|| (start.isAfter(datePick) && start.isBefore(datePickEnd))
						|| (end.isAfter(datePick) && end.isBefore(datePickEnd)))
						&& adventure.fishingInstructor.id == fishingAppointment.fishingAdventure.fishingInstructor.id)
						|| adventure.maxAmountOfPeople <= dateReservationDto.num) {
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
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean reserveAdventure(@RequestBody ReserveAdventureDto reserveAdventureDto) throws Exception
	{	
		return fishingAppointmentService2.reserveAdventure(reserveAdventureDto);
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
