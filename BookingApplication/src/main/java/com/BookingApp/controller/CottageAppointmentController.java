package com.BookingApp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.ChartInfoDto;
import com.BookingApp.dto.ChartInfoReservationDto;
import com.BookingApp.dto.CottageAppointmentDto;
import com.BookingApp.dto.CottageAppointmentForClientDto;
import com.BookingApp.dto.DateReservationDto;
import com.BookingApp.dto.FishingAppointmentDto;
import com.BookingApp.dto.InstructorsAppointmentForClientDto;
import com.BookingApp.dto.ReserveAdventureDto;
import com.BookingApp.dto.ReserveCottageDto;
import com.BookingApp.dto.ReservedCottageAppointmentDto;
import com.BookingApp.dto.SearchAppointmentDto;
import com.BookingApp.dto.SearchDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Client;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.CottageAppointmentReport;
import com.BookingApp.model.CottageOwner;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingAppointmentReport;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.repository.BoatReportsRepository;
import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.model.LoyaltyStatus;
import com.BookingApp.model.ShipOwner;
import com.BookingApp.model.SubscribeAdventure;
import com.BookingApp.model.SubscribeCottage;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageOwnerRepository;
import com.BookingApp.repository.CottageReportsRepository;
import com.BookingApp.repository.CottageRepository;
import com.BookingApp.repository.FishingReportsRepository;
import com.BookingApp.repository.LoyaltyProgramRepository;
import com.BookingApp.repository.SubscribeCottageRepository;
import com.BookingApp.repository.UserRepository;
import com.BookingApp.service.CottageAppointmentService;
import com.BookingApp.service.FishingAppointmentService;

@CrossOrigin
@RestController
@Controller
@RequestMapping("/cottageAppointments")
public class CottageAppointmentController {
	@Autowired
	private CottageAppointmentRepository cottageAppointmentRepository;
	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private FishingReportsRepository fishingReportsRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private BoatReportsRepository boatReportsRepository;
	@Autowired
	private CottageReportsRepository cottageReportsRepository;
	@Autowired
	private LoyaltyProgramRepository loyaltyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CottageAppointmentService cottageAppointmentService2;
	@Autowired
	private SubscribeCottageRepository subscribeCottageRepository;
	@Autowired
	private FishingAppointmentService fishingAppointmentService2;
	
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
	
	@GetMapping(path = "/getReservationsHistory/{ownerId}")
	public ResponseEntity<List<CottageAppointment>> getInstructorsReservationsForReport(@PathVariable("ownerId") long id)
	{
		List<CottageAppointment> appointments = new ArrayList<CottageAppointment>();
		for(CottageAppointment appointment : cottageAppointmentRepository.findOwnersReservationHistory(id))
		{
			if (appointment.client != null)
				appointments.add(appointment);
		}
		System.out.println(appointments);
		return new ResponseEntity<List<CottageAppointment>>(appointments,HttpStatus.OK);
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
				dto.end = dto.appointment.appointmentStart.plusDays(dto.appointment.duration);
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
		LocalDateTime datePick = dateReservationDto.datePick.atStartOfDay().plusDays(dateReservationDto.time);
		
		boolean exist;
		List<Cottage> cottages = new ArrayList<Cottage>();
		for(Cottage cottage: cottageRepository.findAll()) {
			exist = false;
			for(CottageAppointment cottageAppointment : cottageAppointmentRepository.findAll()) {
				LocalDateTime start = cottageAppointment.appointmentStart;
				LocalDateTime end = cottageAppointment.appointmentStart.plusDays(cottageAppointment.duration);
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
	
	@PostMapping(path = "/checkOverlap")
    public boolean checkOverlap(@RequestBody CottageAppointmentForClientDto appointmentDTO)
	{	
		LocalDateTime start = appointmentDTO.formatDateFrom();
		LocalDateTime end = appointmentDTO.formatDateFrom().plusDays(appointmentDTO.durationInHours());
		Cottage cottage = cottageRepository.findById(appointmentDTO.cottageId).get();
		List <CottageAppointment> appointments = cottageAppointmentRepository.findCottageAppointmentsHistory(cottage.id);
		System.out.println(appointments.size());
		for (CottageAppointment app : appointments) {
			if ((app.appointmentStart.isBefore(start) && app.appointmentStart.plusDays(app.duration).isAfter(start)) ||
					(app.appointmentStart.isBefore(end) && app.appointmentStart.plusDays(app.duration).isAfter(end)) ||
					(app.appointmentStart.isAfter(start) && app.appointmentStart.plusDays(app.duration).isBefore(end)) ||
					app.appointmentStart.isEqual(start) || app.appointmentStart.plusDays(app.duration).isEqual(end)) {
				System.out.println("Fejkara");
				return false;
			}
		}
		return true;
	}
	@PostMapping(path = "/checkOverlapNow/{cottageId}")
    public boolean checkOverlapNow(@PathVariable("cottageId") long id)
	{	
		LocalDateTime start = LocalDateTime.now();
		Cottage c = cottageRepository.findById(id).get();
		List <CottageAppointment> appointments = cottageAppointmentRepository.findCottageAppointmentsHistory(c.id);
		System.out.println(appointments.size());
		for (CottageAppointment app : appointments) {
			if ((app.appointmentStart.isBefore(start) && app.appointmentStart.plusDays(app.duration).isAfter(start)) ||
					app.appointmentStart.isEqual(start)) {
				System.out.println("Fejkara");
				return false;
			}
		}
		return true;
	}
	
	@PostMapping(path = "/checkCottageAvailability")
    public boolean checkCottageAvailability(@RequestBody CottageAppointmentForClientDto appointmentDTO)
	{	
		Cottage cottage = cottageRepository.findById(appointmentDTO.cottageId).get();
		LocalDateTime timeFrom = appointmentDTO.formatDateFrom();
		List<CottageAppointment> appointments = cottageAppointmentRepository.findCottageAppointmentsHistory(appointmentDTO.cottageId);
		for (CottageAppointment cottageAppointment : appointments) {
			if (cottageAppointment.appointmentStart.isBefore(appointmentDTO.formatDateFrom()) && cottageAppointment.appointmentStart.plusDays(cottageAppointment.duration).isBefore(appointmentDTO.formatDateUntil())) {
				System.out.println("Prosao");
				return false;	
			}
		}
		
		return true;
	}
	@PostMapping(path = "/addOwnersAppointmentForClient")
    public ResponseEntity<List<CottageAppointment>> addClientAppointment(@RequestBody CottageAppointmentForClientDto appointmentDTO) throws Exception
	{	
		CottageAppointment app  = cottageAppointmentService2.addClientAppointment(appointmentDTO);
	if(app!=null) {
			sendEmailToClient(app);
			return getAllQuickAppointmentsForCottage(appointmentDTO.cottageId);
		}
		return null;
	}
	public boolean sendEmailToClient(CottageAppointment app)
	{	
		Cottage cottage = cottageRepository.findById(app.cottage.id).get();
		String title = "New reservation notification";
		String body = "Hello,\nYour reservation requested from the instructor has been successful - " + cottage.name + " - " + cottage.address + ", " + " . \n"
					+ "\nAction information:\n"
					+	"\nStart : " + app.appointmentStart + "\n"
					+	"\nDuration : " + app.duration + " hours \n"
					+	"\nCapacity : " + cottage.maxAmountOfPeople +" people \n"
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

	@PostMapping(path = "/addQuickAppointment")
    public ResponseEntity<List<CottageAppointment>> addQucikAppointment(@RequestBody CottageAppointmentDto appointmentDTO) throws Exception
	{	
			if(cottageAppointmentService2.addQucikAppointment(appointmentDTO)) { 
			sendEmailToSubscribers(appointmentDTO);
			return getAllQuickAppointmentsForCottage(appointmentDTO.cottageId);
			}
			return null;
		}

	public boolean sendEmailToSubscribers(CottageAppointmentForClientDto appDto)
	{	
		Cottage cottage = cottageRepository.findById(appDto.cottageId).get();
		String title = "New reservation notification";
		String body = "Hello,\nYour reservation requested from the instructor has been successful - " + cottage.name + " - " + cottage.address + ", "  + " . \n"
					+ "\nAction information:\n"
					+	"\nStart :" + appDto.dateFrom + " - " + appDto.timeFrom + "\n"
					+	"\nDuration : " + appDto.durationInHours() + " hours \n"
					+	"\nCapacity : " + cottage.maxAmountOfPeople +" people \n"
					+	"\nPrice : " + appDto.price +"\n"
					+	"\nExtras : " + appDto.extraNotes +"\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
			List<SubscribeCottage> subs =  subscribeCottageRepository.findAllByCottage(appDto.cottageId);
		for (SubscribeCottage sa : subs) {
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
	
	public boolean sendEmailToSubscribers(CottageAppointmentDto appDto)
	{	
		Cottage cottage = cottageRepository.findById(appDto.cottageId).get();
		String title = "New action notification";
		String body = "Hello,\nThere is a new action reservation available for " + cottage.name + " - " + cottage.address + ", " + " . \n"
					+ "\nAction information:\n"
					+	"\nStart :" + appDto.dateFrom + " - " + appDto.timeFrom + "\n"
					+	"\nDuration : " + appDto.durationInHours() + " hours \n"
					+	"\nCapacity : " + cottage.maxAmountOfPeople +" people \n"
					+	"\nPrice : " + appDto.price +"\n"
					+	"\nExtras : " + appDto.extraNotes +"\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		List<SubscribeCottage> subs =  subscribeCottageRepository.findAllByCottage(appDto.cottageId);
		for (SubscribeCottage sa : subs) {
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
	private Cottage getCottageById(long id) {
		Optional<Cottage> cottage = cottageRepository.findById(id);
		Cottage ret = cottage.get(); 
		return ret;
	}
	
	@GetMapping(path = "/isCottFree/{cottageId}")
	public boolean isCottageFree(@PathVariable("cottageId") long id) {
		
		Cottage c = cottageRepository.findById(id).get();
		Set<CottageAppointment> appointments = cottageAppointmentRepository.findCottageAppointments(id);
		LocalDateTime date = LocalDateTime.now();
		for (CottageAppointment cottageAppointment : appointments) {
			LocalDateTime start = cottageAppointment.appointmentStart;
			LocalDateTime end = cottageAppointment.appointmentStart.plusDays(cottageAppointment.duration);
			if(start.isBefore(date)&&end.isAfter(date) || start.equals(date) || end.equals(date)) {
				System.out.println("doso");
				return false;
			}
		}
		System.out.println("mozes");
		return true;
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
	@GetMapping(path = "/getCurrentAppointments/{ownerId}")
	public ResponseEntity<List<CottageAppointment>> getCurrentAppointments(@PathVariable("ownerId") long id)
	{
		System.out.println("a");
		List<CottageAppointment> appointments = new ArrayList<CottageAppointment>();
		List<Cottage> cottages = cottageRepository.getAllCottagesForOwner(id);
		for (Cottage cottage : cottages) {
			for(CottageAppointment appointment : cottageAppointmentRepository.findCottageAppointmentsHistory(cottage.id))
			{
				System.out.println(cottage.name + " pauza" + appointment.appointmentStart + appointment.appointmentStart.plusDays(appointment.duration));
				if (appointment.client != null && LocalDateTime.now().isAfter(appointment.appointmentStart) && LocalDateTime.now().isBefore(appointment.appointmentStart.plusDays(appointment.duration)))
					appointments.add(appointment);
			}
			System.out.println(appointments.size());
		}
		
		return new ResponseEntity<List<CottageAppointment>>(appointments,HttpStatus.OK);
	}
	@GetMapping(path = "/getReservationsForReports/{ownerId}")
	public ResponseEntity<List<CottageAppointment>> getInstructorsReservationsHistory(@PathVariable("ownerId") long id)
	{
		List<CottageAppointment> appointments = new ArrayList<CottageAppointment>();
		for(CottageAppointment appointment : cottageAppointmentRepository.findOwnersReservationHistory(id))
		{
			if (appointment.client != null && appointment.appointmentStart.plusDays(appointment.duration).isBefore(LocalDateTime.now()) && !checkIfReportExists(appointment.id))
				appointments.add(appointment);
		}
		return new ResponseEntity<List<CottageAppointment>>(appointments,HttpStatus.OK);
	}
	private boolean checkIfReportExists(long id) {
		for(CottageAppointmentReport rep : cottageReportsRepository.findAll()) {
			if (rep.appointment.id == id) {
				return true;
			}
		}
		return false;
	}
	@GetMapping(path = "/getCottageProfitCharts/{ownerId}")
	public ResponseEntity<List<ChartInfoDto>> getChartInfo(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<CottageAppointment> appointments = cottageAppointmentRepository.findOwnersReservationHistory(id);
		boolean dateExists = false;
		for (CottageAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
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
					info.add(new ChartInfoDto(app.appointmentStart.plusDays(app.duration), app.ownerProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getCottageProfitChartsWeek/{ownerId}")
	public ResponseEntity<List<ChartInfoDto>> getWeekChartInfo(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<CottageAppointment> appointments = cottageAppointmentRepository.findOwnersReservationHistory(id);
		boolean dateExists = false;
		for (CottageAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusDays(app.duration).isAfter(LocalDateTime.now().minusDays(7)) && app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
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
					info.add(new ChartInfoDto(app.appointmentStart.plusDays(app.duration), app.ownerProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getCottageProfitChartsMonth/{ownerId}")
	public ResponseEntity<List<ChartInfoDto>> getMonthChartInfo(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<CottageAppointment> appointments = cottageAppointmentRepository.findOwnersReservationHistory(id);
		boolean dateExists = false;
		for (CottageAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusDays(app.duration).isAfter(LocalDateTime.now().minusDays(30)) && app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
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
					info.add(new ChartInfoDto(app.appointmentStart.plusDays(app.duration), app.ownerProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getCottageProfitChartsYear/{ownerId}")
	public ResponseEntity<List<ChartInfoDto>> getYearChartInfo(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<CottageAppointment> appointments = cottageAppointmentRepository.findOwnersReservationHistory(id);
		boolean dateExists = false;
		for (CottageAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusDays(app.duration).isAfter(LocalDateTime.now().minusYears(1)) && app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
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
					info.add(new ChartInfoDto(app.appointmentStart.plusDays(app.duration), app.ownerProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	@GetMapping(path = "/getCottageReservationCharts/{ownerId}")
	public ResponseEntity<List<ChartInfoReservationDto>> getCottageReservationCharts(@PathVariable("ownerId") long id)
	{	
		List<ChartInfoReservationDto> info = new ArrayList<ChartInfoReservationDto>();
		List<Cottage> cottages = cottageRepository.getAllCottagesForOwner(id);
		for (Cottage cottage : cottages) {
			List<CottageAppointment> appointments = cottageAppointmentRepository.findCottageAppointmentsHistory(cottage.id);
			ChartInfoReservationDto dto = new ChartInfoReservationDto();
			dto.name = cottage.name;
			dto.amount = 0;
			for (CottageAppointment app : appointments) {
				if (app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
						dto.amount += 1;
						
				}
			}
			info.add(dto);
		}
		
		return new ResponseEntity<List<ChartInfoReservationDto>>(info,HttpStatus.OK);
	}

	@GetMapping(path = "/getCottageReservationChartsWeek/{ownerId}")
	public ResponseEntity<List<ChartInfoReservationDto>> getCottageReservationChartsWeek(@PathVariable("ownerId") long id)
	{
	List<ChartInfoReservationDto> info = new ArrayList<ChartInfoReservationDto>();
	List<Cottage> cottages = cottageRepository.getAllCottagesForOwner(id);
	for (Cottage cottage : cottages) {
		List<CottageAppointment> appointments = cottageAppointmentRepository.findCottageAppointmentsHistory(cottage.id);
		ChartInfoReservationDto dto = new ChartInfoReservationDto();
		dto.name = cottage.name;
		dto.amount = 0;
		for (CottageAppointment app : appointments) {
			if (app.appointmentStart.plusDays(app.duration).isAfter(LocalDateTime.now().minusDays(7)) && app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
					dto.amount += 1;
					
			}
		}
		info.add(dto);
	}
	
	return new ResponseEntity<List<ChartInfoReservationDto>>(info,HttpStatus.OK);
}

	@GetMapping(path = "/getCottageReservationChartsMonth/{ownerId}")
	public ResponseEntity<List<ChartInfoReservationDto>> getCottageReservationChartsMonth(@PathVariable("ownerId") long id)
	{
		List<ChartInfoReservationDto> info = new ArrayList<ChartInfoReservationDto>();
		List<Cottage> cottages = cottageRepository.getAllCottagesForOwner(id);
		for (Cottage cottage : cottages) {
			List<CottageAppointment> appointments = cottageAppointmentRepository.findCottageAppointmentsHistory(cottage.id);
			ChartInfoReservationDto dto = new ChartInfoReservationDto();
			dto.name = cottage.name;
			dto.amount = 0;
			for (CottageAppointment app : appointments) {
				if (app.appointmentStart.plusDays(app.duration).isAfter(LocalDateTime.now().minusDays(30)) && app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
						dto.amount += 1;
						
				}
			}
			info.add(dto);
		}
		
		return new ResponseEntity<List<ChartInfoReservationDto>>(info,HttpStatus.OK);
	}

	@GetMapping(path = "/getCottageReservationChartsYear/{ownerId}")
	public ResponseEntity<List<ChartInfoReservationDto>> getCottageReservationChartsYear(@PathVariable("ownerId") long id)
	{
		List<ChartInfoReservationDto> info = new ArrayList<ChartInfoReservationDto>();
		List<Cottage> cottages = cottageRepository.getAllCottagesForOwner(id);
		for (Cottage cottage : cottages) {
			List<CottageAppointment> appointments = cottageAppointmentRepository.findCottageAppointmentsHistory(cottage.id);
			ChartInfoReservationDto dto = new ChartInfoReservationDto();
			dto.name = cottage.name;
			dto.amount = 0;
			for (CottageAppointment app : appointments) {
				if (app.appointmentStart.plusDays(app.duration).isAfter(LocalDateTime.now().minusYears(1)) && app.appointmentStart.plusDays(app.duration).isBefore(LocalDateTime.now())) {
						dto.amount += 1;
						
				}
			}
			info.add(dto);
		}
		
		return new ResponseEntity<List<ChartInfoReservationDto>>(info,HttpStatus.OK);
	}
	
}
