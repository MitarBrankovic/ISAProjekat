package com.BookingApp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.BookingApp.dto.BoatAppointmentDto;
import com.BookingApp.dto.BoatAppointmentForClientDto;
import com.BookingApp.dto.ChartInfoDto;
import com.BookingApp.dto.ChartInfoReservationDto;
import com.BookingApp.dto.CottageAppointmentDto;
import com.BookingApp.dto.CottageAppointmentForClientDto;
import com.BookingApp.dto.DateReservationDto;
import com.BookingApp.dto.ReserveAdventureDto;
import com.BookingApp.dto.ReserveBoatDto;
import com.BookingApp.dto.ReservedBoatAppointmentDto;
import com.BookingApp.dto.SearchAppointmentDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.BoatAppointmentReport;
import com.BookingApp.model.Client;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.CottageAppointmentReport;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.model.LoyaltyStatus;
import com.BookingApp.model.ShipOwner;
import com.BookingApp.model.SubscribeBoat;
import com.BookingApp.model.SubscribeCottage;
import com.BookingApp.repository.BoatAppointmentRepository;
import com.BookingApp.repository.BoatReportsRepository;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageReportsRepository;
import com.BookingApp.repository.FishingReportsRepository;
import com.BookingApp.repository.LoyaltyProgramRepository;
import com.BookingApp.repository.SubscribeBoatRepository;
import com.BookingApp.repository.SubscribeCottageRepository;
import com.BookingApp.repository.UserRepository;
import com.BookingApp.service.BoatAppointmentService;
import com.BookingApp.service.FishingAppointmentService;


@CrossOrigin
@RestController
@RequestMapping("/boatAppointments")
public class BoatAppointmentController {
	@Autowired
	private BoatAppointmentRepository boatAppointmentRepository;
	@Autowired
	private BoatRepository boatRepository;
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
	private SubscribeBoatRepository subscribeBoatRepository;
	@Autowired
	private FishingAppointmentService fishingAppointmentService2;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private BoatAppointmentService boatAppointmentService2;
	
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
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean scheduleBoatAppointment(@PathVariable("boatId") long id, @PathVariable("userId") long userId) throws Exception
	{
		return boatAppointmentService2.scheduleBoatAppointment(id, userId);
	}
	
	@PutMapping(path = "/cancelBoatAppointment/{boatId}")
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean cancelBoatAppointment(@PathVariable("boatId") long id)
	{
		Optional<BoatAppointment> oldAppointment = boatAppointmentRepository.findById(id);

		if(oldAppointment.isPresent()) {
			BoatAppointment appointment = oldAppointment.get();
			boatAppointmentService2.removeLoyaltyPoints(appointment.client, appointment.boat.shipOwner);
			appointment.client = null;
			boatAppointmentRepository.save(appointment);
			return true;
		}
		return false;
	}
	
	@GetMapping(path = "/getReservationsHistory/{ownerId}")
	public ResponseEntity<List<BoatAppointment>> getInstructorsReservationsForReport(@PathVariable("ownerId") long id)
	{
		List<BoatAppointment> appointments = new ArrayList<BoatAppointment>();
		for(BoatAppointment appointment : boatAppointmentRepository.findOwnersReservationHistory(id))
		{
			if (appointment.client != null)
				appointments.add(appointment);
		}
		System.out.println(appointments);
		return new ResponseEntity<List<BoatAppointment>>(appointments,HttpStatus.OK);
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
				dto.end = dto.appointment.appointmentStart.plusHours(dto.appointment.duration);
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
				LocalDateTime datePickEnd = datePick.plusDays(dateReservationDto.day);
				if( ((((datePick.isAfter(start) && datePick.isBefore(end)) || datePick.isEqual(start) || datePick.isEqual(end))		
						|| ((datePickEnd.isAfter(start) && datePickEnd.isBefore(end)) || datePickEnd.isEqual(start) || datePickEnd.isEqual(end))
						|| (start.isAfter(datePick) && start.isBefore(datePickEnd))
						|| (end.isAfter(datePick) && end.isBefore(datePickEnd)))
						&& boat.id == fishingAppointment.boat.id)
						|| boat.maxAmountOfPeople <= dateReservationDto.num) {
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
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean reserveBoat(@RequestBody ReserveBoatDto reserveBoatDto) throws Exception
	{	
		return boatAppointmentService2.reserveBoat(reserveBoatDto);
	}
	
	@PostMapping(path = "/checkOverlap")
    public boolean checkOverlap(@RequestBody BoatAppointmentForClientDto appointmentDTO)
	{	
		LocalDateTime start = appointmentDTO.formatDateFrom();
		LocalDateTime end = appointmentDTO.formatDateFrom().plusHours(appointmentDTO.durationInHours());
		Boat boat = boatRepository.findById(appointmentDTO.boatId).get();
		List <BoatAppointment> appointments = boatAppointmentRepository.findBoatAppointmentsHistory(boat.id);
		System.out.println(appointments.size());
		for (BoatAppointment app : appointments) {
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
	
	@PostMapping(path = "/addQuickAppointment")
    public ResponseEntity<List<BoatAppointment>> addAppointment(@RequestBody BoatAppointmentDto appointmentDTO)
	{	
		Boat boat = boatRepository.findById(appointmentDTO.boatId).get();
		if(appointmentDTO != null) {
			BoatAppointment appointment = new BoatAppointment(appointmentDTO.formatDateFrom(), 
											 appointmentDTO.durationInHours(), boat.maxAmountOfPeople,AppointmentType.quick,
											 appointmentDTO.extraNotes, appointmentDTO.price,appointmentDTO.price/5,appointmentDTO.price/5*4);
			appointment.boat = getBoatById(appointmentDTO.boatId);
			boatAppointmentRepository.save(appointment);
			sendEmailToSubscribers(appointmentDTO);
			return getAllQuickAppointmentsForBoat(appointmentDTO.boatId);
		}
		return null;
	}
	private Boat getBoatById(long id) {
		Optional<Boat> boat = boatRepository.findById(id);
		Boat ret = boat.get(); 
		return ret;
	}
	public boolean sendEmailToClient(BoatAppointment app)
	{	
		Boat boat = boatRepository.findById(app.boat.id).get();
		String title = "New reservation notification";
		String body = "Hello,\nYour reservation requested from the instructor has been successful - " + boat.name + " - " + boat.address + ", " + " . \n"
					+ "\nAction information:\n"
					+	"\nStart : " + app.appointmentStart + "\n"
					+	"\nDuration : " + app.duration + " hours \n"
					+	"\nCapacity : " + boat.maxAmountOfPeople +" people \n"
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

	
	public boolean sendEmailToSubscribers(BoatAppointmentForClientDto appDto)
	{	
		Boat boat = boatRepository.findById(appDto.boatId).get();
		String title = "New reservation notification";
		String body = "Hello,\nYour reservation requested from the instructor has been successful - " + boat.name + " - " + boat.address + ", "  + " . \n"
					+ "\nAction information:\n"
					+	"\nStart :" + appDto.dateFrom + " - " + appDto.timeFrom + "\n"
					+	"\nDuration : " + appDto.durationInHours() + " hours \n"
					+	"\nCapacity : " + boat.maxAmountOfPeople +" people \n"
					+	"\nPrice : " + appDto.price +"\n"
					+	"\nExtras : " + appDto.extraNotes +"\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
			List<SubscribeBoat> subs =  subscribeBoatRepository.findAllByBoat(appDto.boatId);
		for (SubscribeBoat sa : subs) {
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
	
	public boolean sendEmailToSubscribers(BoatAppointmentDto appDto)
	{	
		Boat boat = boatRepository.findById(appDto.boatId).get();
		String title = "New action notification";
		String body = "Hello,\nThere is a new action reservation available for " + boat.name + " - " + boat.address + ", " + " . \n"
					+ "\nAction information:\n"
					+	"\nStart :" + appDto.dateFrom + " - " + appDto.timeFrom + "\n"
					+	"\nDuration : " + appDto.durationInHours() + " hours \n"
					+	"\nCapacity : " + boat.maxAmountOfPeople +" people \n"
					+	"\nPrice : " + appDto.price +"\n"
					+	"\nExtras : " + appDto.extraNotes +"\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		List<SubscribeBoat> subs =  subscribeBoatRepository.findAllByBoat(appDto.boatId);
		for (SubscribeBoat sa : subs) {
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
	@GetMapping(path = "/getCurrentAppointments/{ownerId}")
	public ResponseEntity<List<BoatAppointment>> getCurrentAppointments(@PathVariable("ownerId") long id)
	{
		System.out.println("a");
		List<BoatAppointment> appointments = new ArrayList<BoatAppointment>();
		List<Boat> boats = boatRepository.getOwnersBoats(id);
		for (Boat boat : boats) {
			for(BoatAppointment appointment : boatAppointmentRepository.findBoatAppointmentsHistory(boat.id))
			{
				if (appointment.client != null && LocalDateTime.now().isAfter(appointment.appointmentStart) && LocalDateTime.now().isBefore(appointment.appointmentStart.plusHours(appointment.duration)))
					appointments.add(appointment);
			}
			System.out.println(appointments.size());
		}
		
		return new ResponseEntity<List<BoatAppointment>>(appointments,HttpStatus.OK);
	}
	@PostMapping(path = "/addOwnersAppointmentForClient")
    public ResponseEntity<List<BoatAppointment>> addClientAppointment(@RequestBody BoatAppointmentForClientDto appointmentDTO)
	{	
		Boat boat = boatRepository.findById(appointmentDTO.boatId).get();
		if(appointmentDTO != null) {
			BoatAppointment appointment = new BoatAppointment(appointmentDTO.formatDateFrom(), 
					 appointmentDTO.durationInHours(), boat.maxAmountOfPeople,AppointmentType.quick,
					 appointmentDTO.extraNotes, appointmentDTO.price,appointmentDTO.price/5,appointmentDTO.price/5*4);
			appointment.boat = getBoatById(appointmentDTO.boatId);
			appointment.client = clientRepository.findById(appointmentDTO.clientId).get();
			appointment.price = fishingAppointmentService2.calculateDiscountedPrice(appointment.price, appointment.client);
			double ownerCut = fishingAppointmentService2.getOwnerProfitBoat(appointment.boat.shipOwner);
			appointment.ownerProfit = appointment.price*ownerCut/100;
			appointment.systemProfit = appointment.price - appointment.ownerProfit;
			boatAppointmentRepository.save(appointment);
			sendEmailToClient(appointment);
			return getAllQuickAppointmentsForBoat(appointmentDTO.boatId);
		}
		return null;
	}
	@GetMapping(path = "/getReservationsForReports/{ownerId}")
	public ResponseEntity<List<BoatAppointment>> getInstructorsReservationsHistory(@PathVariable("ownerId") long id)
	{
		List<BoatAppointment> appointments = new ArrayList<BoatAppointment>();
		for(BoatAppointment appointment : boatAppointmentRepository.findOwnersReservationHistory(id))
		{
			if (appointment.client != null && appointment.appointmentStart.plusHours(appointment.duration).isBefore(LocalDateTime.now()) && !checkIfReportExists(appointment.id))
				appointments.add(appointment);
		}
		return new ResponseEntity<List<BoatAppointment>>(appointments,HttpStatus.OK);
	}
	private boolean checkIfReportExists(long id) {
		for(BoatAppointmentReport rep : boatReportsRepository.findAll()) {
			if (rep.appointment.id == id) {
				return true;
			}
		}
		return false;
	}
	@GetMapping(path = "/getBoatProfitCharts/{ownerId}")
	public ResponseEntity<List<ChartInfoDto>> getChartInfo(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<BoatAppointment> appointments = boatAppointmentRepository.findOwnersReservationHistory(id);
		boolean dateExists = false;
		for (BoatAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now())) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.ownerProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.ownerProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getBoatProfitChartsWeek/{ownerId}")
	public ResponseEntity<List<ChartInfoDto>> getWeekChartInfo(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<BoatAppointment> appointments = boatAppointmentRepository.findOwnersReservationHistory(id);
		boolean dateExists = false;
		for (BoatAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(7)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now())) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.ownerProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.ownerProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getBoatProfitChartsMonth/{ownerId}")
	public ResponseEntity<List<ChartInfoDto>> getMonthChartInfo(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<BoatAppointment> appointments = boatAppointmentRepository.findOwnersReservationHistory(id);
		boolean dateExists = false;
		for (BoatAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(30)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now())) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.ownerProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.ownerProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getBoatProfitChartsYear/{ownerId}")
	public ResponseEntity<List<ChartInfoDto>> getYearChartInfo(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<BoatAppointment> appointments = boatAppointmentRepository.findOwnersReservationHistory(id);
		boolean dateExists = false;
		for (BoatAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusYears(1)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now())) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.ownerProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.ownerProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	@PostMapping(path = "/checkOverlapNow/{boatId}")
    public boolean checkOverlapNow(@PathVariable("boatId") long id)
	{	
		LocalDateTime start = LocalDateTime.now();
		Boat b = boatRepository.findById(id).get();
		List <BoatAppointment> appointments = boatAppointmentRepository.findBoatAppointmentsHistory(b.id);
		System.out.println(appointments.size());
		for (BoatAppointment app : appointments) {
			if ((app.appointmentStart.isBefore(start) && app.appointmentStart.plusDays(app.duration).isAfter(start)) ||
					app.appointmentStart.isEqual(start)) {
				System.out.println("Fejkara");
				return false;
			}
		}
		return true;
	}
	@GetMapping(path = "/getBoatReservationCharts/{ownerId}")
	public ResponseEntity<List<ChartInfoReservationDto>> getCottageReservationCharts(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoReservationDto> info = new ArrayList<ChartInfoReservationDto>();
		List<Boat> boats = boatRepository.getOwnersBoats(id);
		for (Boat boat : boats) {
			List<BoatAppointment> appointments = boatAppointmentRepository.findBoatAppointmentsHistory(boat.id);
			ChartInfoReservationDto dto = new ChartInfoReservationDto();
			dto.name = boat.name;
			dto.amount = 0;
			for (BoatAppointment app : appointments) {
				if (app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
						dto.amount += 1;
						
				}
			}
			info.add(dto);
		}
		
		return new ResponseEntity<List<ChartInfoReservationDto>>(info,HttpStatus.OK);
	}

	@GetMapping(path = "/getBoatReservationChartsWeek/{ownerId}")
	public ResponseEntity<List<ChartInfoReservationDto>> getCottageReservationChartsWeek(@PathVariable("ownerId") long id)
	{
		List<ChartInfoReservationDto> info = new ArrayList<ChartInfoReservationDto>();
		List<Boat> boats = boatRepository.getOwnersBoats(id);
		for (Boat boat : boats) {
			List<BoatAppointment> appointments = boatAppointmentRepository.findBoatAppointmentsHistory(boat.id);
			ChartInfoReservationDto dto = new ChartInfoReservationDto();
			dto.name = boat.name;
			dto.amount = 0;
			for (BoatAppointment app : appointments) {
			if (app.appointmentStart.plusDays(app.duration).isAfter(LocalDateTime.now().minusDays(7)) && app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
					dto.amount += 1;
					
			}
		}
		info.add(dto);
	}
	
	return new ResponseEntity<List<ChartInfoReservationDto>>(info,HttpStatus.OK);
}

	@GetMapping(path = "/getBoatReservationChartsMonth/{ownerId}")
	public ResponseEntity<List<ChartInfoReservationDto>> getCottageReservationChartsMonth(@PathVariable("ownerId") long id)
	{
		List<ChartInfoReservationDto> info = new ArrayList<ChartInfoReservationDto>();
		List<Boat> boats = boatRepository.getOwnersBoats(id);
		for (Boat boat : boats) {
			List<BoatAppointment> appointments = boatAppointmentRepository.findBoatAppointmentsHistory(boat.id);
			ChartInfoReservationDto dto = new ChartInfoReservationDto();
			dto.name = boat.name;
			dto.amount = 0;
			for (BoatAppointment app : appointments) {
				if (app.appointmentStart.plusDays(app.duration).isAfter(LocalDateTime.now().minusDays(30)) && app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
						dto.amount += 1;
						
				}
			}
			info.add(dto);
		}
		
		return new ResponseEntity<List<ChartInfoReservationDto>>(info,HttpStatus.OK);
	}

	@GetMapping(path = "/getBoatReservationChartsYear/{ownerId}")
	public ResponseEntity<List<ChartInfoReservationDto>> getCottageReservationChartsYear(@PathVariable("ownerId") long id)
	{
		List<ChartInfoReservationDto> info = new ArrayList<ChartInfoReservationDto>();
		List<Boat> boats = boatRepository.getOwnersBoats(id);
		for (Boat boat : boats) {
			List<BoatAppointment> appointments = boatAppointmentRepository.findBoatAppointmentsHistory(boat.id);
			ChartInfoReservationDto dto = new ChartInfoReservationDto();
			dto.name = boat.name;
			dto.amount = 0;
			for (BoatAppointment app : appointments) {
				if (app.appointmentStart.plusDays(app.duration).isAfter(LocalDateTime.now().minusYears(1)) && app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
						dto.amount += 1;
						
				}
			}
			info.add(dto);
		}
		
		return new ResponseEntity<List<ChartInfoReservationDto>>(info,HttpStatus.OK);
	}
}
