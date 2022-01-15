package com.BookingApp.service2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.BookingApp.dto.ReserveBoatDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Client;
import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.model.LoyaltyStatus;
import com.BookingApp.model.ShipOwner;
import com.BookingApp.repository.BoatAppointmentRepository;
import com.BookingApp.repository.BoatReportsRepository;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageReportsRepository;
import com.BookingApp.repository.FishingReportsRepository;
import com.BookingApp.repository.LoyaltyProgramRepository;
import com.BookingApp.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class BoatAppointmentService2 {

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
	
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean reserveBoat(@RequestBody ReserveBoatDto reserveBoatDto) throws Exception
	{	
		int numOfPenalties = cottageReportsRepository.findAllByClient(reserveBoatDto.client.id).size() +  boatReportsRepository.findAllByClient(reserveBoatDto.client.id).size() +
				fishingReportsRepository.findAllByClient(reserveBoatDto.client.id).size();
	
		if(numOfPenalties < 3) {
			Boat bot = boatRepository.findByIdPess(reserveBoatDto.boat.id);
			BoatAppointment appointment = new BoatAppointment(reserveBoatDto.datePick.atStartOfDay().plusHours(reserveBoatDto.time), 24*reserveBoatDto.day, reserveBoatDto.boat.maxAmountOfPeople, AppointmentType.regular, 
					reserveBoatDto.additionalPricingText, reserveBoatDto.totalPrice, bot, reserveBoatDto.client);
			
			for(BoatAppointment boatAppointment : boatAppointmentRepository.findAll()) {
				LocalDateTime start = boatAppointment.appointmentStart;
				LocalDateTime end = boatAppointment.appointmentStart.plusHours(boatAppointment.duration);
				LocalDateTime datePick = appointment.appointmentStart;
				LocalDateTime datePickEnd = datePick.plusHours(appointment.duration);
				if( ((((datePick.isAfter(start) && datePick.isBefore(end)) || datePick.isEqual(start) || datePick.isEqual(end))	
						|| ((datePickEnd.isAfter(start) && datePickEnd.isBefore(end)) || datePickEnd.isEqual(start) || datePickEnd.isEqual(end))
						|| (start.isAfter(datePick) && start.isBefore(datePickEnd))
						|| (end.isAfter(datePick) && end.isBefore(datePickEnd)))
						&& appointment.boat.id == boatAppointment.boat.id)) {
					return false;
				}				
			}
			Thread.sleep(5000);
			
			double ownerCut = getOwnerProfit(appointment.boat.shipOwner);
			appointment.ownerProfit = appointment.price*ownerCut/100;
			appointment.systemProfit = appointment.price - appointment.ownerProfit;
			addLoyaltyPoints(appointment.client, appointment.boat.shipOwner);
			boatAppointmentRepository.save(appointment);
			return true;
		}else return false;
	}
	
	
	
	public double getOwnerProfit(ShipOwner owner) {
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
	
	public void addLoyaltyPoints(Client client, ShipOwner owner) {
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
	
	public void removeLoyaltyPoints(Client client, ShipOwner owner) {
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
	
	public double calculateClientLoyalty(Client client) {
		if (client.loyaltyStatus == LoyaltyStatus.silver)
			return loyaltyRepository.getLoyalty().silverClient;
		else if (client.loyaltyStatus == LoyaltyStatus.gold)
			return loyaltyRepository.getLoyalty().goldClient;
		else
			return loyaltyRepository.getLoyalty().bronzeClient;
	}
	
	public double calculateOwnerLoyalty(ShipOwner owner) {
		if (owner.loyaltyStatus == LoyaltyStatus.silver)
			return loyaltyRepository.getLoyalty().silverClient;
		else if (owner.loyaltyStatus == LoyaltyStatus.gold)
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
}
