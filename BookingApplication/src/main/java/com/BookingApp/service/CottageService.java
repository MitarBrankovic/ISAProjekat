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

import com.BookingApp.dto.EditCottageDto;
import com.BookingApp.dto.EntityAvailabilityDto;
import com.BookingApp.dto.RequestDeleteAccDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.AdminRepository;
import com.BookingApp.repository.ComplaintRepository;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageOwnerRepository;
import com.BookingApp.repository.CottagePhotoRepository;
import com.BookingApp.repository.CottageRepository;
import com.BookingApp.repository.RequestDeleteAccRepository;
import com.BookingApp.repository.RoleRepository;
import com.BookingApp.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class CottageService {

	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private CottageAppointmentRepository cottageAppointmentRepository;
	@Autowired
	private CottageOwnerRepository cottageOwnerRepository;
	@Autowired
	private CottagePhotoRepository cottagePhotoRepository;
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean editCottage(EditCottageDto dto){
	Cottage c = cottageRepository.findById(dto.id).get();
	System.out.println(c);
	c.name = dto.name;
	c.address = dto.address;
	c.description = dto.description;
	c.roomsNum = dto.roomsNum;
	c.bedsNum = dto.bedsNum;
	c.rules = dto.rules;
	c.priceList = dto.priceList;
	c.pricePerHour = dto.pricePerHour;
	c.maxAmountOfPeople = dto.maxAmountOfPeople;
	cottageRepository.save(c);
	return true;
	}
	
}
