package com.BookingApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.BookingApp.dto.BoatAppointmentForClientDto;
import com.BookingApp.dto.FishingAppointmentDto;
import com.BookingApp.dto.InstructorsAppointmentForClientDto;
import com.BookingApp.dto.ReserveAdventureDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Client;
import com.BookingApp.model.CottageOwner;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.model.LoyaltyStatus;
import com.BookingApp.model.ShipOwner;
import com.BookingApp.repository.BoatReportsRepository;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageReportsRepository;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.FishingAppointmentRepository;
import com.BookingApp.repository.FishingReportsRepository;
import com.BookingApp.repository.LoyaltyProgramRepository;
import com.BookingApp.repository.SubscribeAdvRepository;
import com.BookingApp.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class FishingAppointmentService {

	
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
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean scheduleAdventureAppointment(long id, long userId) throws Exception
	{
		Optional<FishingAppointment> oldAppointment = fishingAppointmentRepository.findById(id);
		Client client = new Client();
		for(Client oldClient : clientRepository.findAll()) {
			if(oldClient.id == userId) {
				client = oldClient;
			}
		}
		int numOfPenalties = cottageReportsRepository.findAllByClient(userId).size() +  boatReportsRepository.findAllByClient(userId).size() +
				fishingReportsRepository.findAllByClient(userId).size();
	
		if(oldAppointment.get().client !=null)
			return false;
		//Thread.sleep(5000);
		
		if(numOfPenalties < 3) {
			if(oldAppointment.isPresent()) {
				FishingAppointment appointment = oldAppointment.get();
				appointment.client = client;
				double ownerCut = getOwnerProfit(appointment.fishingAdventure.fishingInstructor);
				appointment.instructorProfit = appointment.price*ownerCut/100;
				appointment.systemProfit = appointment.price - appointment.instructorProfit;
				fishingAppointmentRepository.save(appointment);
				addLoyaltyPoints(client, appointment.fishingAdventure.fishingInstructor);
				return true;
			}else return false;
		}else return false;
	}
	
	
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean reserveAdventure(ReserveAdventureDto reserveAdventureDto) throws Exception
	{	
		int numOfPenalties = cottageReportsRepository.findAllByClient(reserveAdventureDto.client.id).size() +  boatReportsRepository.findAllByClient(reserveAdventureDto.client.id).size() +
				fishingReportsRepository.findAllByClient(reserveAdventureDto.client.id).size();
	
		if(numOfPenalties < 3) {
			FishingAdventure adv = fishingAdventureRepository.findByIdPess(reserveAdventureDto.fishingAdventure.id);
			FishingAppointment appointment = new FishingAppointment(reserveAdventureDto.datePick.atStartOfDay().plusHours(reserveAdventureDto.time), reserveAdventureDto.fishingAdventure.address, reserveAdventureDto.fishingAdventure.city, 
					 3, reserveAdventureDto.fishingAdventure.maxAmountOfPeople, AppointmentType.regular, false, 0,reserveAdventureDto.additionalPricingText, reserveAdventureDto.totalPrice);
			appointment.client = reserveAdventureDto.client;
			appointment.fishingAdventure = adv;
			
			
			for(FishingAppointment fishingAppointment : fishingAppointmentRepository.findAll()) {
				LocalDateTime start = fishingAppointment.appointmentStart;
				LocalDateTime end = fishingAppointment.appointmentStart.plusHours(fishingAppointment.duration);
				LocalDateTime datePick = appointment.appointmentStart;
				LocalDateTime datePickEnd = datePick.plusHours(appointment.duration);
				if( ((((datePick.isAfter(start) && datePick.isBefore(end)) || datePick.isEqual(start) || datePick.isEqual(end))	
						|| ((datePickEnd.isAfter(start) && datePickEnd.isBefore(end)) || datePickEnd.isEqual(start) || datePickEnd.isEqual(end))
						|| (start.isAfter(datePick) && start.isBefore(datePickEnd))
						|| (end.isAfter(datePick) && end.isBefore(datePickEnd)))
						&& appointment.fishingAdventure.fishingInstructor.id == fishingAppointment.fishingAdventure.fishingInstructor.id)) {
					return false;
				}				
			}
			
			//Thread.sleep(5000);
			
			
			double ownerCut = getOwnerProfit(appointment.fishingAdventure.fishingInstructor);
			appointment.instructorProfit = appointment.price*ownerCut/100;
			appointment.systemProfit = appointment.price - appointment.instructorProfit;
			fishingAppointmentRepository.save(appointment);
			addLoyaltyPoints(appointment.client, appointment.fishingAdventure.fishingInstructor);
			return true;
		}else return false;
	}
	
	
	
	
	
	public double getOwnerProfit(FishingInstructor instructor) {
		LoyaltyProgram loyalty = loyaltyRepository.getLoyalty();
		if (instructor.loyaltyStatus == LoyaltyStatus.regular)
			return 80;
		else if (instructor.loyaltyStatus == LoyaltyStatus.bronze)
			return loyalty.bronzePrecentage;
		else if (instructor.loyaltyStatus == LoyaltyStatus.silver)
			return loyalty.silverPrecentage;
		else
			return loyalty.goldPrecentage;
	}
	public double getOwnerProfitCottage(CottageOwner owner) {
		LoyaltyProgram loyalty = loyaltyRepository.getLoyalty();
		if (owner.loyaltyStatus == LoyaltyStatus.regular)
			return 80;
		else if (owner.loyaltyStatus == LoyaltyStatus.bronze)
			return loyalty.bronzePrecentage;
		else if (owner.loyaltyStatus == LoyaltyStatus.silver)
			return loyalty.silverPrecentage;
		else
			return loyalty.goldPrecentage;
	}
	public double getOwnerProfitBoat(ShipOwner owner) {
		LoyaltyProgram loyalty = loyaltyRepository.getLoyalty();
		if (owner.loyaltyStatus == LoyaltyStatus.regular)
			return 80;
		else if (owner.loyaltyStatus == LoyaltyStatus.bronze)
			return loyalty.bronzePrecentage;
		else if (owner.loyaltyStatus == LoyaltyStatus.silver)
			return loyalty.silverPrecentage;
		else
			return loyalty.goldPrecentage;
	}
	
	public double calculateDiscountedPrice(double price, Client client) {
		LoyaltyProgram loyalty = loyaltyRepository.getLoyalty();
		if (client.loyaltyStatus == LoyaltyStatus.regular)
			return price;
		else if (client.loyaltyStatus == LoyaltyStatus.bronze)
			return price*(100 - loyalty.bronzeDiscount)/100;
		else if (client.loyaltyStatus == LoyaltyStatus.silver)
			return price*(100 - loyalty.silverDiscount)/100;
		else
			return price*(100 - loyalty.goldDiscount)/100;
	}
	
	public void addLoyaltyPoints(Client client, FishingInstructor instructor) {
		List <AppUser> users = new ArrayList<AppUser>();
		for(AppUser au: userRepository.findAll()) {
			if (au.id == client.id) {
				au.loyaltyPoints += calculateClientLoyalty(client);
			}
			else if (au.id == instructor.id) {
				au.loyaltyPoints += calculateInstructorLoyalty(instructor);
			}
			au.loyaltyStatus = updateLoyaltyStatus(au.loyaltyPoints);
		users.add(au);
		}
		userRepository.saveAll(users);
	}
	
	public void removeLoyaltyPoints(Client client, FishingInstructor instructor) {
		List <AppUser> users = new ArrayList<AppUser>();
		for(AppUser au: userRepository.findAll()) {
			if (au.id == client.id) {
				au.loyaltyPoints -= calculateClientLoyalty(client);
			}
			else if (au.id == instructor.id) {
				au.loyaltyPoints -= calculateInstructorLoyalty(instructor);
			}
			au.loyaltyStatus = updateLoyaltyStatus(au.loyaltyPoints);
		users.add(au);
		}
		userRepository.saveAll(users);
	}
	
	public double calculateClientLoyalty(Client client) {
		if (client.loyaltyStatus == LoyaltyStatus.silver)
			return loyaltyRepository.getLoyalty().silverClient;
		else if (client.loyaltyStatus == LoyaltyStatus.gold)
			return loyaltyRepository.getLoyalty().goldClient;
		else
			return loyaltyRepository.getLoyalty().bronzeClient;
	}
	
	public double calculateInstructorLoyalty(FishingInstructor instructor) {
		if (instructor.loyaltyStatus == LoyaltyStatus.silver)
			return loyaltyRepository.getLoyalty().silverClient;
		else if (instructor.loyaltyStatus == LoyaltyStatus.gold)
			return loyaltyRepository.getLoyalty().goldClient;
		else
			return loyaltyRepository.getLoyalty().bronzeClient;
	}
	
	public LoyaltyStatus updateLoyaltyStatus(double loyaltyPoints) {
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
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	    public boolean addAppointment(@RequestBody FishingAppointmentDto appointmentDTO) throws InterruptedException
		{	
			FishingAdventure adventure = fishingAdventureRepository.findById(appointmentDTO.adventureId).get();
			if(appointmentDTO != null) {
				FishingAppointment appointment = new FishingAppointment(appointmentDTO.formatDateFrom(), adventure.address, adventure.city, 
												 appointmentDTO.durationInHours(), adventure.maxAmountOfPeople, AppointmentType.quick, true, 0,
												 appointmentDTO.extraNotes, appointmentDTO.price);
				appointment.fishingAdventure = getAdventureById(appointmentDTO.adventureId);
				fishingAppointmentRepository.save(appointment);
					return true;
				}
				return false;
			}
		    
		    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
		    public FishingAppointment addClientAppointment(@RequestBody InstructorsAppointmentForClientDto appointmentDTO) throws InterruptedException
			{	
				FishingAdventure adventure = fishingAdventureRepository.findById(appointmentDTO.adventureId).get();
				if(appointmentDTO != null) {
					FishingAppointment appointment = new FishingAppointment(appointmentDTO.formatDateFrom(), adventure.address, adventure.city, 
													 appointmentDTO.durationInHours(), adventure.maxAmountOfPeople, AppointmentType.regular, false, 0,
													 appointmentDTO.extraNotes, appointmentDTO.price);
					appointment.fishingAdventure = getAdventureById(appointmentDTO.adventureId);
					appointment.client = clientRepository.findById(appointmentDTO.clientId).get();
					appointment.price = this.calculateDiscountedPrice(appointment.price, appointment.client);
					double ownerCut = this.getOwnerProfit(appointment.fishingAdventure.fishingInstructor);
					appointment.instructorProfit = appointment.price*ownerCut/100;
					appointment.systemProfit = appointment.price - appointment.instructorProfit;
					fishingAppointmentRepository.save(appointment);
					return appointment;
				}
				return null;
			}
		    private FishingAdventure getAdventureById(long id) {
				Optional<FishingAdventure> adventure = fishingAdventureRepository.findById(id);
				FishingAdventure ret = adventure.get(); 
				return ret;
			}
}
