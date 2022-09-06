package com.BookingApp.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.BookingApp.dto.EditBoatDto;
import com.BookingApp.dto.EditCottageDto;
import com.BookingApp.dto.EntityAvailabilityDto;
import com.BookingApp.dto.RequestDeleteAccDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.Boat;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.AdminRepository;
import com.BookingApp.repository.BoatAppointmentRepository;
import com.BookingApp.repository.BoatPhotoRepository;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.ComplaintRepository;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageOwnerRepository;
import com.BookingApp.repository.CottagePhotoRepository;
import com.BookingApp.repository.CottageRepository;
import com.BookingApp.repository.RequestDeleteAccRepository;
import com.BookingApp.repository.RoleRepository;
import com.BookingApp.repository.ShipOwnerRepository;
import com.BookingApp.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class BoatService {

	@Autowired
	private BoatRepository boatRepository;
	@Autowired
	private BoatAppointmentRepository boatAppointmentRepository;
	@Autowired
	private ShipOwnerRepository shipOwnerRepository;
	@Autowired
	private BoatPhotoRepository boatPhotoRepository;
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean editBoat(@RequestBody EditBoatDto dto) throws InterruptedException{
		Boat b = boatRepository.findById(dto.id).get();
		System.out.println(b);
		b.name = dto.name;
		b.boatType = dto.boatType;
		b.length = dto.length;
		b.engineNumber = dto.engineNumber;
		b.enginePower = dto.enginePower;
		b.maxSpeed = dto.maxSpeed;
		b.navigationEquipment = dto.navigationEquipment;
		b.address = dto.address;
		b.description = dto.description;
		b.capacity = dto.capacity;
		b.rules = dto.rules;
		b.fishingEquipment = dto.fishingEquipment;
		b.priceList = dto.priceList;
		b.pricePerHour = dto.pricePerHour;
		b.maxAmountOfPeople = dto.maxAmountOfPeople;
		b.shipOwner = shipOwnerRepository.findById(dto.shipOwnerId).get();
		boatRepository.save(b);
	return true;
	}
	
}
