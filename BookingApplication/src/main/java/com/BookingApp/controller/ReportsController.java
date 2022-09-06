package com.BookingApp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.AppointmentReportDto;
import com.BookingApp.dto.ReportsClientDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentRatingOptions;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.BoatAppointmentReport;
import com.BookingApp.model.FishingAppointmentReport;
import com.BookingApp.model.Client;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.CottageAppointmentReport;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.repository.BoatAppointmentRepository;
import com.BookingApp.repository.BoatReportsRepository;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageReportsRepository;
import com.BookingApp.repository.FishingAppointmentRepository;
import com.BookingApp.repository.FishingReportsRepository;
import com.BookingApp.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	private FishingReportsRepository fishingReportsRepository;
	@Autowired
	private BoatReportsRepository boatReportsRepository;
	@Autowired
	private CottageReportsRepository cottageReportsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private FishingAppointmentRepository fishingAppointmentRepository;
	@Autowired
	private CottageAppointmentRepository cottageAppointmentRepository;
	@Autowired
	private BoatAppointmentRepository boatAppointmentRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@GetMapping(path = "/getFishingReports")
	public Set<FishingAppointmentReport> getFishingReports()
	{
		return fishingReportsRepository.findUnapprovedPenalties(false);
	}
	
	@GetMapping(path = "/getBoatReports")
	public Set<BoatAppointmentReport> getBoatReports()
	{
		return boatReportsRepository.findUnapprovedPenalties(false);
	}
	
	@GetMapping(path = "/getCottageReports")
	public Set<CottageAppointmentReport> getCottageReports()
	{
		return cottageReportsRepository.findUnapprovedPenalties(false);
	}
	
	@GetMapping(path = "/findAllReportsByClient/{clientId}")
	public ReportsClientDto findAllReportsByClient(@PathVariable("clientId") long id)
	{
		return new ReportsClientDto(cottageReportsRepository.findAllByClient(id), boatReportsRepository.findAllByClient(id), fishingReportsRepository.findAllByClient(id));
	}
	
	@GetMapping(path = "/deleteAllReportsFirstInMonth")
	public boolean deleteAllReportsFirstInMonth()
	{
		LocalDateTime date = LocalDateTime.now();
		if(date.getDayOfMonth() == 1) {
			cottageReportsRepository.deleteAll();
			boatReportsRepository.deleteAll();
			fishingReportsRepository.deleteAll();
		}
		return true;
	}
	
	@PreAuthorize("hasAuthority('FISHINGINSTRUCTOR')")
	@PostMapping(path = "/sendFishingReport")
    public List<FishingAppointment> sendFishingReport(@RequestBody AppointmentReportDto reportDTO)
	{	
		if (reportDTO.type.equalsIgnoreCase("didnt_show")) {
			fishingReportsRepository.save(new FishingAppointmentReport(reportDTO.comment, AppointmentRatingOptions.didnt_show, userRepository.findById(reportDTO.clientId).get(), 
					userRepository.findById(reportDTO.ownerId).get(), fishingAppointmentRepository.findById(reportDTO.appointmentId).get(), true));
			penaliseClient(reportDTO.clientId);
			AppointmentReportDto newDto = reportDTO;
			newDto.type = "fishing";
			sendPenaltyEmail(newDto);
		}
		else if (reportDTO.type.equalsIgnoreCase("penalty"))
			fishingReportsRepository.save(new FishingAppointmentReport(reportDTO.comment, AppointmentRatingOptions.penalty, userRepository.findById(reportDTO.clientId).get(), 
					userRepository.findById(reportDTO.ownerId).get(), fishingAppointmentRepository.findById(reportDTO.appointmentId).get(), false));
		else
			fishingReportsRepository.save(new FishingAppointmentReport(reportDTO.comment, AppointmentRatingOptions.positive, userRepository.findById(reportDTO.clientId).get(), 
					userRepository.findById(reportDTO.ownerId).get(), fishingAppointmentRepository.findById(reportDTO.appointmentId).get(), true));
		return getReservationsForReports(reportDTO);
	}
	@PreAuthorize("hasAuthority('COTTAGEOWNER')")
	@PostMapping(path = "/sendCottageReport")
    public List<CottageAppointment> sendCottageReport(@RequestBody AppointmentReportDto reportDTO)
	{	
		if (reportDTO.type.equalsIgnoreCase("didnt_show")) {
			cottageReportsRepository.save(new CottageAppointmentReport(reportDTO.comment, AppointmentRatingOptions.didnt_show, userRepository.findById(reportDTO.clientId).get(), 
					userRepository.findById(reportDTO.ownerId).get(), cottageAppointmentRepository.findById(reportDTO.appointmentId).get(), true));
			penaliseClient(reportDTO.clientId);
			AppointmentReportDto newDto = reportDTO;
			newDto.type = "cottage";
			sendPenaltyEmail(newDto);
		}
		else if (reportDTO.type.equalsIgnoreCase("penalty"))
			cottageReportsRepository.save(new CottageAppointmentReport(reportDTO.comment, AppointmentRatingOptions.penalty, userRepository.findById(reportDTO.clientId).get(), 
					userRepository.findById(reportDTO.ownerId).get(), cottageAppointmentRepository.findById(reportDTO.appointmentId).get(), false));
		else
			cottageReportsRepository.save(new CottageAppointmentReport(reportDTO.comment, AppointmentRatingOptions.positive, userRepository.findById(reportDTO.clientId).get(), 
					userRepository.findById(reportDTO.ownerId).get(), cottageAppointmentRepository.findById(reportDTO.appointmentId).get(), true));
		return getReservationsForReportsCottage(reportDTO);
	}
	@PreAuthorize("hasAuthority('BOATOWNER')")
	@PostMapping(path = "/sendBoatReport")
    public List<BoatAppointment> sendBoatReport(@RequestBody AppointmentReportDto reportDTO)
	{	
		if (reportDTO.type.equalsIgnoreCase("didnt_show")) {
			boatReportsRepository.save(new BoatAppointmentReport(reportDTO.comment, AppointmentRatingOptions.didnt_show, userRepository.findById(reportDTO.clientId).get(), 
					userRepository.findById(reportDTO.ownerId).get(),boatAppointmentRepository.findById(reportDTO.appointmentId).get(), true));
			penaliseClient(reportDTO.clientId);
			AppointmentReportDto newDto = reportDTO;
			newDto.type = "boat";
			sendPenaltyEmail(newDto);
		}
		else if (reportDTO.type.equalsIgnoreCase("penalty"))
			boatReportsRepository.save(new BoatAppointmentReport(reportDTO.comment, AppointmentRatingOptions.penalty, userRepository.findById(reportDTO.clientId).get(), 
					userRepository.findById(reportDTO.ownerId).get(), boatAppointmentRepository.findById(reportDTO.appointmentId).get(), false));
		else
			boatReportsRepository.save(new BoatAppointmentReport(reportDTO.comment, AppointmentRatingOptions.positive, userRepository.findById(reportDTO.clientId).get(), 
					userRepository.findById(reportDTO.ownerId).get(), boatAppointmentRepository.findById(reportDTO.appointmentId).get(), true));
		return getReservationsForReportsBoat(reportDTO);
	}

	public List<FishingAppointment> getReservationsForReports(AppointmentReportDto reportDTO)
	{
		List<FishingAppointment> appointments = new ArrayList<FishingAppointment>();
		for(FishingAppointment appointment : fishingAppointmentRepository.findInstructorsReservationHistory(reportDTO.ownerId))
		{
			if (appointment.client != null && appointment.appointmentStart.plusHours(appointment.duration).isBefore(LocalDateTime.now()) && 
					!checkIfReportExists(appointment.id))
				appointments.add(appointment);
		}
		return appointments;
	}
	public List<CottageAppointment> getReservationsForReportsCottage(AppointmentReportDto reportDTO)
	{
		List<CottageAppointment> appointments = new ArrayList<CottageAppointment>();
		for(CottageAppointment appointment : cottageAppointmentRepository.findOwnersReservationHistory(reportDTO.ownerId))
		{
			if (appointment.client != null && appointment.appointmentStart.plusDays(appointment.duration).isBefore(LocalDateTime.now()) && 
					!checkIfReportExists(appointment.id))
				appointments.add(appointment);
		}
		return appointments;
	}
	public List<BoatAppointment> getReservationsForReportsBoat(AppointmentReportDto reportDTO)
	{
		List<BoatAppointment> appointments = new ArrayList<BoatAppointment>();
		for(BoatAppointment appointment : boatAppointmentRepository.findOwnersReservationHistory(reportDTO.ownerId))
		{
			if (appointment.client != null && appointment.appointmentStart.plusHours(appointment.duration).isBefore(LocalDateTime.now()) && 
					!checkIfReportExists(appointment.id))
				appointments.add(appointment);
		}
		return appointments;
	}
	
	private boolean checkIfReportExists(long id) {
		for(FishingAppointmentReport rep : fishingReportsRepository.findAll()) {
			if (rep.appointment.id == id) {
				return true;
			}
		}
		return false;
	}
	
	private void penaliseClient(long id) {
		List<Client> clientsToSave = new ArrayList<Client>();
		for(Client c : clientRepository.findAll()) {
			if (c.id == id)
				c.penalties += 1;
			clientsToSave.add(c);
		}
		clientRepository.saveAll(clientsToSave);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/declineFishingPenalty")
    public Set<FishingAppointmentReport> declineFishingPenalty(@RequestBody FishingAppointmentReport reportDTO)
	{	
		fishingReportsRepository.deleteById(reportDTO.id);
		return getFishingReports();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/declineBoatPenalty")
    public Set<BoatAppointmentReport> declineBoatPenalty(@RequestBody BoatAppointmentReport reportDTO)
	{	
		boatReportsRepository.deleteById(reportDTO.id);
		return getBoatReports();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/declineCottagePenalty")
    public Set<CottageAppointmentReport> declineCottagePenalty(@RequestBody CottageAppointmentReport reportDTO)
	{	
		cottageReportsRepository.deleteById(reportDTO.id);
		return getCottageReports();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/approveFishingPenalty")
    public Set<FishingAppointmentReport> approveFishingPenalty(@RequestBody FishingAppointmentReport reportDTO)
	{	
		List<FishingAppointmentReport> reports = new ArrayList<FishingAppointmentReport>();
		for(FishingAppointmentReport f : fishingReportsRepository.findAll()) {
			if (f.id == reportDTO.id)
				f.isApproved = true;
			reports.add(f);
		}
		fishingReportsRepository.saveAll(reports);
		penaliseClient(reportDTO.client.id);
		sendPenaltyEmail(new AppointmentReportDto("fishing", reportDTO.comment, reportDTO.appointment.id, reportDTO.owner.id, reportDTO.client.id));
		return getFishingReports();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/approveBoatPenalty")
    public Set<BoatAppointmentReport> approveBoatPenalty(@RequestBody BoatAppointmentReport reportDTO)
	{	
		List<BoatAppointmentReport> reports = new ArrayList<BoatAppointmentReport>();
		for(BoatAppointmentReport b : boatReportsRepository.findAll()) {
			if (b.id == reportDTO.id)
				b.isApproved = true;
			reports.add(b);
		}
		boatReportsRepository.saveAll(reports);
		penaliseClient(reportDTO.client.id);
		sendPenaltyEmail(new AppointmentReportDto("boat", reportDTO.comment, reportDTO.appointment.id, reportDTO.owner.id, reportDTO.client.id));
		return getBoatReports();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/approveCottagePenalty")
    public Set<CottageAppointmentReport> approveCottagePenalty(@RequestBody CottageAppointmentReport reportDTO)
	{	
		List<CottageAppointmentReport> reports = new ArrayList<CottageAppointmentReport>();
		for(CottageAppointmentReport c : cottageReportsRepository.findAll()) {
			if (c.id == reportDTO.id)
				c.isApproved = true;
			reports.add(c);
		}
		cottageReportsRepository.saveAll(reports);
		penaliseClient(reportDTO.client.id);
		sendPenaltyEmail(new AppointmentReportDto("cottage", reportDTO.comment, reportDTO.appointment.id, reportDTO.owner.id, reportDTO.client.id));
		return getCottageReports();
	}
	
	public boolean sendPenaltyEmail(AppointmentReportDto reportDTO)
	{	
		String entityName = "";
		String entityAddress = "";
		AppUser owner = userRepository.findById(reportDTO.ownerId).get();
		if (reportDTO.type.equalsIgnoreCase("fishing")) {
			FishingAdventure adventure = fishingAppointmentRepository.findById(reportDTO.appointmentId).get().fishingAdventure;
			entityName = adventure.name;
			entityAddress = adventure.address + ", " + adventure.city;
		}
		else if (reportDTO.type.equalsIgnoreCase("boat")) {
			Boat boat = boatAppointmentRepository.findById(reportDTO.appointmentId).get().boat;
			entityName = boat.name;
			entityAddress = boat.address;
		}
		else if (reportDTO.type.equalsIgnoreCase("cottage")){
			Cottage cottage = cottageAppointmentRepository.findById(reportDTO.appointmentId).get().cottage;
			entityName = cottage.name;
			entityAddress = cottage.address;
		}
		Client client = clientRepository.findById(reportDTO.clientId).get();
		String title = "New penalty notification";
		String body = "Hello,\nYou have had a negative report on your recent reservation. \n"
					+ "\nTherefore, you've been awarded a penalty.\n"
					+ "\nIf you hit 3 penalties during one month, you won't be able to make any more reservations.\n"
					+ "\nCurrently, you have " + client.penalties + " penalty/penalties.\n"
					+ "\nReport information:\n"
					+	"\nUser : " + owner.name + " " + owner.surname + "\n"
					+	"\nUser E-mail : " + owner.email +"\n"
					+	"\nEntity : " + entityName +"\n"
					+	"\nEntity address: " + entityAddress +"\n"
					+	"\nComment : " + reportDTO.comment +"\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(client.email,body,title);	
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
	
	public void sendEmail(String to, String body, String title)
	{

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(title);
		msg.setText(body);
		javaMailSender.send(msg);
		System.out.println("Email sent...");
	}
}
