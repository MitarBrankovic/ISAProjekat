package com.BookingApp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.AddBoatDto;
import com.BookingApp.dto.ChartInfoDto;
import com.BookingApp.dto.ChartInfoRatingDto;
import com.BookingApp.dto.EditBoatDto;
import com.BookingApp.dto.EditCottageDto;
import com.BookingApp.dto.EntityAvailabilityDto;
import com.BookingApp.dto.FishingInstructorAvailabilityDto;
import com.BookingApp.dto.NewCottageDto;
import com.BookingApp.dto.NewPhotoDto;
import com.BookingApp.dto.SearchDto;
import com.BookingApp.dto.SearchOwnerDto;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.BoatPhoto;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageOwner;
import com.BookingApp.model.CottagePhoto;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.model.ShipOwner;
import com.BookingApp.repository.BoatAppointmentRepository;
import com.BookingApp.repository.BoatPhotoRepository;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.ShipOwnerRepository;

@CrossOrigin
@RestController
@RequestMapping("/boats")
public class BoatController {

		@Autowired
		private BoatRepository boatRepository;
		@Autowired
		private BoatAppointmentRepository boatAppointmentRepository;
		@Autowired
		private ShipOwnerRepository shipOwnerRepository;
		@Autowired
		private BoatPhotoRepository boatPhotoRepository;
		
		@GetMapping(path = "/getAllBoats")
		public ResponseEntity<List<Boat>> getAllBoats()
		{
			List<Boat> boats = new ArrayList<Boat>();
			for(Boat boat : boatRepository.findAll())
			{
				boats.add(boat);
			}
			return new ResponseEntity<List<Boat>>(boats,HttpStatus.OK);
		}
		
		@GetMapping(path = "/getOwnersBoats/{ownersId}")
		public Set<Boat> getOwnersBoats(@PathVariable("ownersId") long id)
		{	
			System.out.println(boatRepository.findOwnersBoats(id));
			return boatRepository.findOwnersBoats(id);
		}
		
		
		@GetMapping(path = "/getSelectedBoat/{boatId}")
		public ResponseEntity<Boat> getSelectedBoat(@PathVariable("boatId") long id)
		{
			Optional<Boat> boat = boatRepository.findById(id);
			Boat boatNew = boat.get();
			
			return new ResponseEntity<Boat>(boatNew,HttpStatus.OK);
		}
		
		@PostMapping(path = "/editBoatsAvailability")
	    public Set<Boat> editBoatsAvailability(@RequestBody EntityAvailabilityDto availabilityDTO)
		{	
			if(availabilityDTO != null) {
				List<Boat> boats = boatRepository.findAll();
				for (Boat b : boats)
					if (b.id == availabilityDTO.entityId) {
						b.availableFrom = availabilityDTO.formatDateFrom();
						b.availableUntil = availabilityDTO.formatDateUntil();
						break;
					}
				boatRepository.saveAll(boats);
				return boatRepository.findOwnersBoats(availabilityDTO.ownerId);
			}
			return null;
		}
		
		@PostMapping(path = "/checkBoatRemoval/{boatId}")
	    public boolean sendRequest(@PathVariable("boatId") long id)
		{	
			Set<BoatAppointment> appointments = boatAppointmentRepository.findBoatAppointments(id);
			for (BoatAppointment ba : appointments) 
				if (ba.client != null && ba.appointmentStart.isAfter(LocalDateTime.now()))
					return false;
			return true;
		}
		
		@PostMapping(path = "/removeBoat/{boatId}")
	    public Set<Boat> removeAdventure(@PathVariable("boatId") long id)
		{	
			long ownerId = boatRepository.findById(id).get().shipOwner.id;
			boatRepository.deleteById(id);
			return boatRepository.findOwnersBoats(ownerId);
		}
		
		@PostMapping(path = "/searchBoats")
		public ResponseEntity<List<Boat>> searchBoats(@RequestBody SearchDto dto)
		{
			String name = dto.name;
			String address = dto.address;
			boolean nameAsc = dto.nameAsc;
			boolean nameDesc = dto.nameDesc;
			boolean addressAsc = dto.addressAsc;
			boolean addressDesc = dto.addressDesc;
			boolean rateAsc = dto.rateAsc;
			boolean rateDesc = dto.rateDesc;

			List<Boat> boats = boatRepository.findAll();

			if (name.equals("") && address.equals(""))
				boats = boatRepository.findAll();
			
			if (!name.equals("")) {
				boats =  boats.stream().filter(m -> m.name.toLowerCase().contains(name.toLowerCase()))
						.collect(Collectors.toList()); }
			
			if (!address.equals("")) {
				boats =  boats.stream().filter(m -> m.address.toLowerCase().contains(address.toLowerCase()))
						.collect(Collectors.toList()); }
				
			if (nameAsc) {
				int n = boats.size();
				Boat temp = null;
				for (int i = 0; i < n; i++) {
					for (int j = 1; j < (n - i); j++) {
						if (boats.get(j - 1).name
								.compareTo(boats.get(j).name) > 0) {
							// swap elements
							temp = boats.get(j - 1);
							boats.set(j - 1, boats.get(j));
							boats.set(j, temp);
						}

					}
				}
			}
			
			
			if (nameDesc) {
				int n = boats.size();
				Boat temp = null;
				for (int i = 0; i < n; i++) {
					for (int j = 1; j < (n - i); j++) {
						if (boats.get(j - 1).name
								.compareTo(boats.get(j).name) < 0) {
							// swap elements
							temp = boats.get(j - 1);
							boats.set(j - 1, boats.get(j));
							boats.set(j, temp);
						}

					}
				}
			}
			
			
			if (addressAsc) {
				int n = boats.size();
				Boat temp = null;
				for (int i = 0; i < n; i++) {
					for (int j = 1; j < (n - i); j++) {
						if (boats.get(j - 1).address
								.compareTo(boats.get(j).address) > 0) {
							// swap elements
							temp = boats.get(j - 1);
							boats.set(j - 1, boats.get(j));
							boats.set(j, temp);
						}

					}
				}
			}
			
			
			if (addressDesc) {
				int n = boats.size();
				Boat temp = null;
				for (int i = 0; i < n; i++) {
					for (int j = 1; j < (n - i); j++) {
						if (boats.get(j - 1).address
								.compareTo(boats.get(j).address) < 0) {
							// swap elements
							temp = boats.get(j - 1);
							boats.set(j - 1, boats.get(j));
							boats.set(j, temp);
						}

					}
				}
			}
			
			if(rateAsc) {
				Collections.sort(boats, new Comparator<Boat>() {
				    @Override
					public int compare(Boat o1, Boat o2) {
				        //return o1.rating.compareTo(o2.rating);
				        return Double.compare(o1.rating, o2.rating);
				    }
				});
			}
			
			
			if(rateDesc) {
				Collections.sort(boats, new Comparator<Boat>() {
				    @Override
					public int compare(Boat o1, Boat o2) {
				    	return Double.compare(o1.rating, o2.rating);
				    }
				});
				Collections.reverse(boats);
			}
			
			
			return new ResponseEntity<List<Boat>>(boats,HttpStatus.OK);
		}
		@PostMapping(path="/editOwner")
		public ShipOwner editOwner(@RequestBody ShipOwner owner)
		{	
			
			if(owner != null) {
				List<ShipOwner> owners = shipOwnerRepository.findAll();
				for (ShipOwner a : owners)
					if (a.id == owner.id) {
						a.name = owner.name;
						a.surname = owner.surname;
						a.address = owner.address;
						a.city = owner.city;
						a.email = owner.email;
						a.password = owner.password;
						a.country = owner.country;
						a.phoneNumber = owner.phoneNumber;
					}
				//probaj samo da je fi = instructor posle
				shipOwnerRepository.saveAll(owners);
				return shipOwnerRepository.findById(owner.id).get();
			}
			return null;
		}
		@PostMapping(path= "/addNewBoat")
		public ResponseEntity<List<Boat>> addNewBoat(@RequestBody AddBoatDto dto){
			if(dto != null) {
				Boat boat = new Boat(dto.name,dto.boatType,dto.length,dto.engineNumber,dto.enginePower,
						dto.maxSpeed,dto.navigationEquipment,dto.address,dto.description,
						dto.capacity,dto.rules,dto.fishingEquipment,dto.priceList,dto.pricePerHour,dto.maxAmountOfPeople);
				boat.shipOwner = shipOwnerRepository.findById(dto.shipOwnerId).get();
				boatRepository.save(boat);
				return new ResponseEntity<List<Boat>>(boatRepository.getOwnersBoats(dto.shipOwnerId),HttpStatus.OK);
			}
			return null;
		}
		@GetMapping(path="/getBoatById/{cottageId}")
		public Boat getBoatById(@PathVariable("cottageId") long id){
			return boatRepository.findById(id).get();
		}
		@PostMapping(path="/editBoat")
		public ResponseEntity<List<Boat>> editCottage(@RequestBody EditBoatDto dto){
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
		return new ResponseEntity<List<Boat>>(boatRepository.getOwnersBoats(dto.shipOwnerId),HttpStatus.OK);
		}
		
		@GetMapping(path="/ratingForBoats/{ownerId}")
		public List<ChartInfoRatingDto> ratingForBoats(@PathVariable("ownerId") long id){
			List<ChartInfoRatingDto> info = new ArrayList<ChartInfoRatingDto>();
			for (Boat boat : boatRepository.getOwnersBoats(id)) {
				info.add(new ChartInfoRatingDto(boat.name,boat.rating));
			}
			return info;
		}
		@PostMapping(path="/searchOwnerBoats")
		public ResponseEntity<List<Boat>> searchOwnersBoats(@RequestBody SearchOwnerDto dto)
		{
			String name = dto.name;
			String address = dto.address;
			boolean nameAsc = dto.nameAsc;
			boolean nameDesc = dto.nameDesc;
			boolean addressAsc = dto.addressAsc;
			boolean addressDesc = dto.addressDesc;
			boolean rateAsc = dto.rateAsc;
			boolean rateDesc = dto.rateDesc;
			

			List<Boat> boats = boatRepository.getOwnersBoats(dto.ownerId);

			if (name.equals("") && address.equals(""))
				boats =  boatRepository.getOwnersBoats(dto.ownerId);
			
			if (!name.equals("")) {
				boats =  boats.stream().filter(m -> m.name.toLowerCase().contains(name.toLowerCase()))
						.collect(Collectors.toList()); }
			
			if (!address.equals("")) {
				boats =  boats.stream().filter(m -> m.address.toLowerCase().contains(address.toLowerCase()))
						.collect(Collectors.toList()); }
				
			if (nameAsc) {
				int n = boats.size();
				Boat temp = null;
				for (int i = 0; i < n; i++) {
					for (int j = 1; j < (n - i); j++) {
						if (boats.get(j - 1).name
								.compareTo(boats.get(j).name) > 0) {
							// swap elements
							temp = boats.get(j - 1);
							boats.set(j - 1, boats.get(j));
							boats.set(j, temp);
						}

					}
				}
			}
			
			
			if (nameDesc) {
				int n = boats.size();
				Boat temp = null;
				for (int i = 0; i < n; i++) {
					for (int j = 1; j < (n - i); j++) {
						if (boats.get(j - 1).name
								.compareTo(boats.get(j).name) < 0) {
							// swap elements
							temp = boats.get(j - 1);
							boats.set(j - 1, boats.get(j));
							boats.set(j, temp);
						}

					}
				}
			}
			
			
			if (addressAsc) {
				int n = boats.size();
				Boat temp = null;
				for (int i = 0; i < n; i++) {
					for (int j = 1; j < (n - i); j++) {
						if (boats.get(j - 1).address
								.compareTo(boats.get(j).address) > 0) {
							// swap elements
							temp = boats.get(j - 1);
							boats.set(j - 1, boats.get(j));
							boats.set(j, temp);
						}

					}
				}
			}
			
			
			if (addressDesc) {
				int n = boats.size();
				Boat temp = null;
				for (int i = 0; i < n; i++) {
					for (int j = 1; j < (n - i); j++) {
						if (boats.get(j - 1).address
								.compareTo(boats.get(j).address) < 0) {
							// swap elements
							temp = boats.get(j - 1);
							boats.set(j - 1, boats.get(j));
							boats.set(j, temp);
						}

					}
				}
			}
			
			if(rateAsc) {
				Collections.sort(boats, new Comparator<Boat>() {
				    @Override
					public int compare(Boat o1, Boat o2) {
				        //return o1.rating.compareTo(o2.rating);
				        return Double.compare(o1.rating, o2.rating);
				    }
				});
			}
			
			
			if(rateDesc) {
				Collections.sort(boats, new Comparator<Boat>() {
				    @Override
					public int compare(Boat o1, Boat o2) {
				    	return Double.compare(o1.rating, o2.rating);
				    }
				});
				Collections.reverse(boats);
			}
			
			
			return new ResponseEntity<List<Boat>>(boats,HttpStatus.OK);
		}
		@GetMapping(path = "/getBoatPhotos/{boatId}")
		public Set<BoatPhoto> getAdventuresPhotos(@PathVariable("boatId") long id)
		{	
			return boatPhotoRepository.findBoatPhotos(id);
		}
		
		@PreAuthorize("hasAuthority('BOATOWNER')")
		@PostMapping(path = "/addPhoto")
	    public Set<BoatPhoto> addPhoto(@RequestBody NewPhotoDto photoDTO)
		{	
			if(photoDTO != null) {
				boatPhotoRepository.save(new BoatPhoto(photoDTO.photo, boatRepository.findById(photoDTO.entityId).get()));
				return boatPhotoRepository.findBoatPhotos(photoDTO.entityId);
			}
			return null;
		}
		
		//@PreAuthorize("hasAuthority('FISHINGINSTRUCTOR')")
		@PostMapping(path = "/removePhoto/{boatId}")
	    public Set<BoatPhoto> removePhoto(@PathVariable("boatId") long id)
		{	
			long maxId = 0;
			Set<BoatPhoto> photos = boatPhotoRepository.findBoatPhotos(id);
			for (BoatPhoto ap : photos)
				if (ap.id > maxId)
						maxId = ap.id;
			if (maxId != 0)
				boatPhotoRepository.deleteById(maxId);
			return boatPhotoRepository.findBoatPhotos(id);
		}
	}
