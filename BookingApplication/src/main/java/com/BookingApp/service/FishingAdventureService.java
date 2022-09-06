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

import com.BookingApp.dto.BoatAppointmentDto;
import com.BookingApp.dto.BoatAppointmentForClientDto;
import com.BookingApp.dto.EditAdventureDto;
import com.BookingApp.dto.EditBoatDto;
import com.BookingApp.dto.EditCottageDto;
import com.BookingApp.dto.EntityAvailabilityDto;
import com.BookingApp.dto.FishingAppointmentDto;
import com.BookingApp.dto.RequestDeleteAccDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
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
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.RequestDeleteAccRepository;
import com.BookingApp.repository.RoleRepository;
import com.BookingApp.repository.ShipOwnerRepository;
import com.BookingApp.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class FishingAdventureService {

	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean editAdventure(@RequestBody EditAdventureDto adventureDTO) throws InterruptedException 
	{	
		//byte[] decodedPhoto = Base64.getMimeDecoder().decode(adventureDTO.photo);
		if(adventureDTO != null) {
			long instructorsId = fishingAdventureRepository.findById(adventureDTO.adventureId).get().fishingInstructor.id;
			List<FishingAdventure> allAdventures = fishingAdventureRepository.findAll();
			for (FishingAdventure adventure : allAdventures)
				if (adventure.id == adventureDTO.adventureId) {
					adventure.name = adventureDTO.name;
					adventure.address = adventureDTO.address;
					adventure.city = adventureDTO.city;
					adventure.description = adventureDTO.description;
					adventure.photo = adventureDTO.photo;
					adventure.maxAmountOfPeople = adventureDTO.maxAmountOfPeople;
					adventure.behaviourRules = adventureDTO.behaviourRules;
					adventure.equipment = adventureDTO.equipment;
					adventure.pricePerHour = adventureDTO.pricePerHour;
					adventure.cancellingPrecentage = adventureDTO.cancellingPrecentage;
				}
			fishingAdventureRepository.saveAll(allAdventures); 
			return true;
			
			
	}else
		return false;
	}
   
}
