package com.BookingApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.BookingApp.dto.CottageAppointmentDto;
import com.BookingApp.dto.CottageAppointmentForClientDto;
import com.BookingApp.dto.ReserveCottageDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Client;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.CottageOwner;
import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.model.LoyaltyStatus;
import com.BookingApp.repository.BoatReportsRepository;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageReportsRepository;
import com.BookingApp.repository.CottageRepository;
import com.BookingApp.repository.FishingReportsRepository;
import com.BookingApp.repository.LoyaltyProgramRepository;
import com.BookingApp.repository.UserRepository;

@Service
@Transactional(readOnly = true)
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
	private FishingAppointmentService fishingAppointmentService2;
	
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean scheduleIt(long id, long userId) throws InterruptedException
	{
		Optional<CottageAppointment> oldAppointment = cottageAppointmentRepository.findById(id);
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
				CottageAppointment appointment = oldAppointment.get();
				appointment.client = client;
				cottageAppointmentRepository.save(appointment);
				return true;
			}else return false;
		}else return false;
	}
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addQucikAppointment(CottageAppointmentDto appointmentDTO) throws InterruptedException
	{	
		Cottage cottage = cottageRepository.findById(appointmentDTO.cottageId).get();
		if(appointmentDTO != null) {
			CottageAppointment appointment = new CottageAppointment(appointmentDTO.formatDateFrom(), 
											 appointmentDTO.durationInHours(), cottage.maxAmountOfPeople,AppointmentType.quick,
											 appointmentDTO.extraNotes, appointmentDTO.price,appointmentDTO.price/5,appointmentDTO.price/5*4);
			appointment.cottage = getCottageById(appointmentDTO.cottageId);
			cottageAppointmentRepository.save(appointment);
			return true;
		}
		return false;
	}
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public CottageAppointment addClientAppointment(CottageAppointmentForClientDto appointmentDTO) throws InterruptedException
	{	
		Cottage cottage = cottageRepository.findById(appointmentDTO.cottageId).get();
		if(appointmentDTO != null) {
			CottageAppointment appointment = new CottageAppointment(appointmentDTO.formatDateFrom(), 
					 appointmentDTO.durationInHours(), cottage.maxAmountOfPeople,AppointmentType.quick,
					 appointmentDTO.extraNotes, appointmentDTO.price,appointmentDTO.price/5,appointmentDTO.price/5*4);
			appointment.cottage = getCottageById(appointmentDTO.cottageId);
			appointment.client = clientRepository.findById(appointmentDTO.clientId).get();
			appointment.price = fishingAppointmentService2.calculateDiscountedPrice(appointment.price, appointment.client);
			double ownerCut = fishingAppointmentService2.getOwnerProfitCottage(appointment.cottage.cottageOwner);
			appointment.ownerProfit = appointment.price*ownerCut/100;
			appointment.systemProfit = appointment.price - appointment.ownerProfit;
			cottageAppointmentRepository.save(appointment);
			return appointment;
		}
		return null;
	}
    private Cottage getCottageById(long id) {
		Optional<Cottage> cottage = cottageRepository.findById(id);
		Cottage ret = cottage.get(); 
		return ret;
	}
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean reserveCottage(ReserveCottageDto reserveCottageDto) throws Exception
	{	
		int numOfPenalties = cottageReportsRepository.findAllByClient(reserveCottageDto.client.id).size() +  boatReportsRepository.findAllByClient(reserveCottageDto.client.id).size() +
				fishingReportsRepository.findAllByClient(reserveCottageDto.client.id).size();
	
		if(numOfPenalties < 3) {
			Cottage cot = cottageRepository.findByIdPess(reserveCottageDto.cottage.id);
			CottageAppointment appointment = new CottageAppointment(reserveCottageDto.datePick.atStartOfDay().plusHours(reserveCottageDto.time), 24*reserveCottageDto.day, reserveCottageDto.cottage.maxAmountOfPeople,
					AppointmentType.regular, reserveCottageDto.additionalPricingText, reserveCottageDto.totalPrice, cot, reserveCottageDto.client);
			
			for(CottageAppointment cottageAppointment : cottageAppointmentRepository.findAll()) {
				LocalDateTime start = cottageAppointment.appointmentStart;
				LocalDateTime end = cottageAppointment.appointmentStart.plusHours(cottageAppointment.duration);
				LocalDateTime datePick = appointment.appointmentStart;
				LocalDateTime datePickEnd = datePick.plusHours(appointment.duration);
				if( ((((datePick.isAfter(start) && datePick.isBefore(end)) || datePick.isEqual(start) || datePick.isEqual(end))	
						|| ((datePickEnd.isAfter(start) && datePickEnd.isBefore(end)) || datePickEnd.isEqual(start) || datePickEnd.isEqual(end))
						|| (start.isAfter(datePick) && start.isBefore(datePickEnd))
						|| (end.isAfter(datePick) && end.isBefore(datePickEnd)))
						&& appointment.cottage.id == cottageAppointment.cottage.id)) {
					return false;
				}				
			}
			
			//Thread.sleep(5000);

			double ownerCut = getOwnerProfit(appointment.cottage.cottageOwner);
			appointment.ownerProfit = appointment.price*ownerCut/100;
			appointment.systemProfit = appointment.price - appointment.ownerProfit;
			cottageAppointmentRepository.save(appointment);
			addLoyaltyPoints(appointment.client, appointment.cottage.cottageOwner);
			return true;
		}else return false;
	}
    
    public double getOwnerProfit(CottageOwner owner) {
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
	
    public void addLoyaltyPoints(Client client, CottageOwner owner) {
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
	
    public void removeLoyaltyPoints(Client client, CottageOwner owner) {
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
	
    public double calculateOwnerLoyalty(CottageOwner owner) {
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
