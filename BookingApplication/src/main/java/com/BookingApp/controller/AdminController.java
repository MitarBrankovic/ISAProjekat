package com.BookingApp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.type.descriptor.converter.AttributeConverterMutabilityPlanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.BookingApp.dto.AddAdminDto;
import com.BookingApp.dto.AnswerComplaintDto;
import com.BookingApp.dto.ChartInfoDto;
import com.BookingApp.dto.ComplaintDto;
import com.BookingApp.dto.RequestDeleteAccDto;
import com.BookingApp.model.Admin;
import com.BookingApp.model.AdminType;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Complaint;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.model.UserType;
import com.BookingApp.repository.AdminRepository;
import com.BookingApp.repository.BoatAppointmentRepository;
import com.BookingApp.repository.ComplaintRepository;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.FishingAppointmentRepository;
import com.BookingApp.repository.RequestDeleteAccRepository;
import com.BookingApp.repository.RoleRepository;
import com.BookingApp.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RequestDeleteAccRepository requestDeleteAccRepository;
	@Autowired
	private ComplaintRepository complaintRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private FishingAppointmentRepository fishingAppointmentRepository;
	@Autowired
	private CottageAppointmentRepository cottageAppointmentRepository;
	@Autowired
	private BoatAppointmentRepository boatAppointmentRepository;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path="/acceptRequest")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<List<RequestDeleteAccDto>> acceptRequest(@RequestBody RequestDeleteAccDto requestDTO) throws Exception, NullPointerException
	{	
		RequestDeleteAcc request = requestDeleteAccRepository.findByIdPess(requestDTO.id);
		Thread.sleep(3000);
		String title = "Request for account deletion";
		String body = "Hello,\nYour request for account deletion has been accepted.\n" 
				  + "\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		Optional<AppUser> oldUser = userRepository.findById(request.appUserId);
		AppUser appUser;
		if(oldUser.isPresent()) {
			appUser = oldUser.get();
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(appUser.email,body,title);		
					}
				};
				t.start();
				userRepository.deleteById(request.appUserId);
				requestDeleteAccRepository.deleteById(request.id);
				return new ResponseEntity<List<RequestDeleteAccDto>>(getRequests(),HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				return new ResponseEntity<List<RequestDeleteAccDto>>(new ArrayList<RequestDeleteAccDto>(), HttpStatus.CONFLICT);
			}
		}			
		else
			return null;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path="/declineRequest")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<List<RequestDeleteAccDto>> declineRequest(@RequestBody RequestDeleteAccDto requestDTO) throws Exception
	{	
		RequestDeleteAcc request = requestDeleteAccRepository.findByIdPess(requestDTO.id);
		Thread.sleep(3000);
		String title = "Request for account deletion";
		String body = "Hello,\nYour request for account deletion has been declined.\n" 
				  + "\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		Optional<AppUser> oldUser = userRepository.findById(request.appUserId);
		AppUser appUser;
		if(oldUser.isPresent()) {
			appUser = oldUser.get();
			try 
			{ 
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(appUser.email,body,title);		
					}
				};
				t.start();
			requestDeleteAccRepository.deleteById(request.id);
			return new ResponseEntity<List<RequestDeleteAccDto>>(getRequests(),HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				return new ResponseEntity<List<RequestDeleteAccDto>>(getRequests(), HttpStatus.CONFLICT);
			}
		}			
		else
			return null;
	}
	
	public List<RequestDeleteAccDto> getRequests()
	{	
		List <RequestDeleteAccDto> requestsDTOs = new ArrayList<RequestDeleteAccDto>();
		List <RequestDeleteAcc> requests = requestDeleteAccRepository.findAll();
		for(RequestDeleteAcc req : requests) {
			AppUser user = userRepository.findById(req.appUserId).get();
			requestsDTOs.add(new RequestDeleteAccDto(req.id, req.appUserId, req.isFinished, req.text, user.name + " " + user.surname, TranslateRole(user.role)));
		}
		return requestsDTOs;
	}
	
	private String TranslateRole(UserType role) {
		if (role == UserType.admin)
			return "Administrator";
		else if (role == UserType.client)
			return "Klijent";
		else if (role == UserType.cottage_owner)
			return "Vlasnik vikendice";
		else if (role == UserType.fishing_instructor)
			return "Instruktor pecanja";
		else
			return "Vlasnik broda";
	}
	
	@GetMapping(path = "/getProfit")
	public ResponseEntity<List<ChartInfoDto>> getChartInfo()
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<FishingAppointment> appointments = fishingAppointmentRepository.findAll();
		boolean dateExists = false;
		for (FishingAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		for (CottageAppointment app : cottageAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		for (BoatAppointment app : boatAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getProfitWeekCharts")
	public ResponseEntity<List<ChartInfoDto>> getWeekChartInfo()
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		List<FishingAppointment> appointments = fishingAppointmentRepository.findAll();
		boolean dateExists = false;
		for (FishingAppointment app : appointments) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(7)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		for (CottageAppointment app : cottageAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(7)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		for (BoatAppointment app : boatAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(7)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getProfitMonthCharts")
	public ResponseEntity<List<ChartInfoDto>> getMonthChartInfo()
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		boolean dateExists = false;
		for (FishingAppointment app : fishingAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(30)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		for (CottageAppointment app : cottageAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(30)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		for (BoatAppointment app : boatAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusDays(30)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getProfitYearCharts")
	public ResponseEntity<List<ChartInfoDto>> getYearChartInfo()
	{	
		List<ChartInfoDto> info = new ArrayList<ChartInfoDto>();
		boolean dateExists = false;
		for (FishingAppointment app : fishingAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusYears(1)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		for (CottageAppointment app : cottageAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusYears(1)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		for (BoatAppointment app : boatAppointmentRepository.findAll()) {
			dateExists = false;
			if (app.appointmentStart.plusHours(app.duration).isAfter(LocalDateTime.now().minusYears(1)) && app.appointmentStart.plusHours(app.duration).isBefore(LocalDateTime.now()) && app.client != null) {
				for (ChartInfoDto infoDto : info) {
					if (infoDto.dateAndTime.getDayOfMonth() == app.appointmentStart.getDayOfMonth() && 
						infoDto.dateAndTime.getYear() == app.appointmentStart.getYear() && 
						infoDto.dateAndTime.getMonth() == app.appointmentStart.getMonth()) {
						infoDto.price += app.systemProfit;
						dateExists = true;
						break;
					}
				}
				if (!dateExists)
					info.add(new ChartInfoDto(app.appointmentStart.plusHours(app.duration), app.systemProfit));
			}
		}
		Collections.sort(info);
		return new ResponseEntity<List<ChartInfoDto>>(info,HttpStatus.OK);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path="/answerComplaint")
	public ResponseEntity<List<Complaint>> answerComplaint(@RequestBody AnswerComplaintDto answerComplaintDto) throws Exception
	{	
		ComplaintDto complaint  = answerComplaintDto.complaint;
		String titleUser = "Answer on complaint";
		String bodyUser = "Hello,\nWe're sorry that you have complaints.\n" 
					+	"\nWe will try to fix that issue.\n" + answerComplaintDto.text
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		String titleOwner = "Complaint on your product";
		String bodyOwner = "Hello,\nYou have recived complaint on your product.\n" +  "Complaint: " + answerComplaintDto.complaint.text
					+	"\nPlease try to fix that issue.\n"  + answerComplaintDto.text
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		complaintRepository.deleteById(complaint.id);
		Thread.sleep(5000);
		Optional<AppUser> owner = userRepository.findById(complaint.ownerId);
		Optional<AppUser> user = userRepository.findById(complaint.client.id);
		AppUser appUser;
		AppUser appOwner;
		if(user.isPresent() && owner.isPresent()) {
			appUser = user.get();
			appOwner = owner.get();
			try
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(appUser.email,bodyUser,titleUser);	
						sendEmail(appOwner.email,bodyOwner,titleOwner);
					}
				};
				t.start();
			return new ResponseEntity<List<Complaint>>(complaintRepository.findAll(),HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				return new ResponseEntity<List<Complaint>>(complaintRepository.findAll(),HttpStatus.CONFLICT);
			}
		}			
		else
			return null;
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
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/editAdmin")
    public Admin editAdmin(@RequestBody Admin admin)
	{	
		
		if(admin != null) {
			List<Admin> admins = adminRepository.findAll();
			for (Admin a : admins)
				if (a.id == admin.id) {
					a.name = admin.name;
					a.surname = admin.surname;
					a.address = admin.address;
					a.city = admin.city;
					a.email = admin.email;
					a.password = admin.password;
					a.country = admin.country;
					a.phoneNumber = admin.phoneNumber;
				}
			//probaj samo da je fi = instructor posle
			adminRepository.saveAll(admins);
			return adminRepository.findById(admin.id).get();
		}
		return null;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/addAdmin")
    public boolean addAdmin(@RequestBody AddAdminDto adminDTO)
	{	
		if(adminDTO != null && checkEmailDuplicate(adminDTO.email)) {
			Admin newAdmin = new Admin(adminDTO.name, adminDTO.surname, adminDTO.email, adminDTO.password, adminDTO.address, 
					adminDTO.city, adminDTO.country, adminDTO.phoneNumber, UserType.admin, null, false, "", AdminType.added);
					newAdmin.setRoles(roleRepository.findByName("ADMIN"));
			adminRepository.save(newAdmin);
			return true;
		}
		return false;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/acceptNewAccount")
    public ResponseEntity<List<AppUser>> verifyOwner(@RequestBody AppUser appUser)
	{	
		String title = "New account request";
		String body = "Hello,\nYour request for account creation has been accepted.\n" 
					+	"\nNow you can log in and start using our website.\n"
					+   "\nWe hope you'll enjoy it !"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(appUser.email,body,title);	
					}
				};
				t.start();
				saveAccountStates(appUser.id);
				return new ResponseEntity<List<AppUser>>(userRepository.findAll(),HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				return null;
			}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/declineNewAccount")
    public ResponseEntity<List<AppUser>> declineOwner(@RequestBody AppUser appUser)
	{	
		String title = "New account request";
		String body = "Hello,\nYour request for account creation has NOT been accepted.\n" 
					+	"\nPlease, check validity of your information and try again.\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(appUser.email,body,title);	
					}
				};
				t.start();
				userRepository.deleteById(appUser.id);
				return new ResponseEntity<List<AppUser>>(userRepository.findAll(),HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				return null;
			}
	}

	@PutMapping(path = "/changePassword/{userId}/{password}")
	public boolean changeAdminPassword(@PathVariable("userId") long id, @PathVariable("password") String password)
	{
		List <AppUser> users = new ArrayList<AppUser>();
		for(AppUser au : userRepository.findAll()) {
			if (au.id == id) {
				au.password = password;
				au.verified = true;
				users.add(au);
			}
			else
				users.add(au);
		}
		userRepository.saveAll(users);
		return true;
	}
	
	private void saveAccountStates(long id) {
		List<AppUser> users = new ArrayList<AppUser>();
		for (AppUser au : userRepository.findAll()) {
			if (au.id == id) {
				au.verified = true;
				users.add(au);
			}
			else
				users.add(au);
		}
		userRepository.saveAll(users);
	}
	
	private boolean checkEmailDuplicate(String email) {
		List <AppUser> users = userRepository.findAll();
		for(AppUser user : users) {
			if (user.email.equalsIgnoreCase(email))
				return false;
		}
		return true;
	}
	
}
