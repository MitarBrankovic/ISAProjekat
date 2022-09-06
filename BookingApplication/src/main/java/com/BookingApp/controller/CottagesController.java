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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.ChartInfoRatingDto;
import com.BookingApp.dto.EditCottageDto;
import com.BookingApp.dto.EntityAvailabilityDto;
import com.BookingApp.dto.NewCottageDto;
import com.BookingApp.dto.NewPhotoDto;
import com.BookingApp.dto.SearchDto;
import com.BookingApp.dto.SearchOwnerDto;
import com.BookingApp.model.Admin;
import com.BookingApp.model.AdventurePhoto;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.CottageOwner;
import com.BookingApp.model.CottagePhoto;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageOwnerRepository;
import com.BookingApp.repository.CottagePhotoRepository;
import com.BookingApp.repository.CottageRepository;

@CrossOrigin
@RestController
@RequestMapping("/cottages")
public class CottagesController {

	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private CottageAppointmentRepository cottageAppointmentRepository;
	@Autowired
	private CottageOwnerRepository cottageOwnerRepository;
	@Autowired
	private CottagePhotoRepository cottagePhotoRepository;
	
	@GetMapping(path = "/getAllCottages")
	public ResponseEntity<List<Cottage>> getAllCottage()
	{
		List<Cottage> cottages = new ArrayList<Cottage>();
		for(Cottage cottage : cottageRepository.findAll())
		{
			cottages.add(cottage);
		}
		return new ResponseEntity<List<Cottage>>(cottages,HttpStatus.OK);
	}
	@GetMapping(path = "/getAllCottagesByOwner/{ownerId}")
	public ResponseEntity<List<Cottage>> getAllCottagesByOwner(@PathVariable("ownerId") long id)
	{	
		List<Cottage> cottages = cottageRepository.getAllCottagesForOwner(id);
		
		return new ResponseEntity<List<Cottage>>(cottages,HttpStatus.OK);
	}
	
	@PostMapping(path = "/editCottagesAvailability")
    public Set<Cottage> editCottagesAvailability(@RequestBody EntityAvailabilityDto availabilityDTO)
	{	
		if(availabilityDTO != null) {
			List<Cottage> cottages = cottageRepository.findAll();
			for (Cottage c : cottages)
				if (c.id == availabilityDTO.entityId) {
					c.availableFrom = availabilityDTO.formatDateFrom();
					c.availableUntil = availabilityDTO.formatDateUntil();
					break;
				}
			cottageRepository.saveAll(cottages);
			return cottageRepository.findOwnersCottages(availabilityDTO.ownerId);
		}
		return null;
	}
	
	@GetMapping(path = "/getOwnersCottages/{ownersId}")
	public Set<Cottage> getOwnersCottages(@PathVariable("ownersId") long id)
	{	
		System.out.println(cottageRepository.findOwnersCottages(id));
		return cottageRepository.findOwnersCottages(id);
	}
	
	@PostMapping(path = "/removeCottage/{cottageId}")
    public Set<Cottage> removeCottage(@PathVariable("cottageId") long id)
	{	
		long ownerId = cottageRepository.findById(id).get().cottageOwner.id;
		cottageRepository.deleteById(id);
		return cottageRepository.findOwnersCottages(ownerId);
	}
	
	@PostMapping(path = "/checkCottageRemoval/{cottageId}")
    public boolean sendRequest(@PathVariable("cottageId") long id)
	{	
		Set<CottageAppointment> appointments = cottageAppointmentRepository.findCottageAppointments(id);
		for (CottageAppointment ca : appointments) 
			if (ca.client != null && ca.appointmentStart.isAfter(LocalDateTime.now()))
				return false;
		return true;
	}
	
	
	@PostMapping(path = "/searchCottages")
	public ResponseEntity<List<Cottage>> searchCottages(@RequestBody SearchDto dto)
	{
		String name = dto.name;
		String address = dto.address;
		boolean nameAsc = dto.nameAsc;
		boolean nameDesc = dto.nameDesc;
		boolean addressAsc = dto.addressAsc;
		boolean addressDesc = dto.addressDesc;
		boolean rateAsc = dto.rateAsc;
		boolean rateDesc = dto.rateDesc;
		

		List<Cottage> cottages = cottageRepository.findAll();

		if (name.equals("") && address.equals(""))
			cottages = cottageRepository.findAll();
		
		if (!name.equals("")) {
			cottages =  cottages.stream().filter(m -> m.name.toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!address.equals("")) {
			cottages =  cottages.stream().filter(m -> m.address.toLowerCase().contains(address.toLowerCase()))
					.collect(Collectors.toList()); }
			
		if (nameAsc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).name
							.compareTo(cottages.get(j).name) > 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		if (nameDesc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).name
							.compareTo(cottages.get(j).name) < 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressAsc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).address
							.compareTo(cottages.get(j).address) > 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressDesc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).address
							.compareTo(cottages.get(j).address) < 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		if(rateAsc) {
			Collections.sort(cottages, new Comparator<Cottage>() {
			    @Override
				public int compare(Cottage o1, Cottage o2) {
			        //return o1.rating.compareTo(o2.rating);
			        return Double.compare(o1.rating, o2.rating);
			    }
			});
		}
		
		
		if(rateDesc) {
			Collections.sort(cottages, new Comparator<Cottage>() {
			    @Override
				public int compare(Cottage o1, Cottage o2) {
			    	return Double.compare(o1.rating, o2.rating);
			    }
			});
			Collections.reverse(cottages);
		}
		
		
		return new ResponseEntity<List<Cottage>>(cottages,HttpStatus.OK);
	}
	
	@PostMapping(path="/searchOwnerCottages")
	public ResponseEntity<List<Cottage>> searchOwnersCottages(@RequestBody SearchOwnerDto dto)
	{
		String name = dto.name;
		String address = dto.address;
		boolean nameAsc = dto.nameAsc;
		boolean nameDesc = dto.nameDesc;
		boolean addressAsc = dto.addressAsc;
		boolean addressDesc = dto.addressDesc;
		boolean rateAsc = dto.rateAsc;
		boolean rateDesc = dto.rateDesc;
		

		List<Cottage> cottages = cottageRepository.getAllCottagesForOwner(dto.ownerId);

		if (name.equals("") && address.equals(""))
			cottages = cottageRepository.getAllCottagesForOwner(dto.ownerId);
		
		if (!name.equals("")) {
			cottages =  cottages.stream().filter(m -> m.name.toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!address.equals("")) {
			cottages =  cottages.stream().filter(m -> m.address.toLowerCase().contains(address.toLowerCase()))
					.collect(Collectors.toList()); }
			
		if (nameAsc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).name
							.compareTo(cottages.get(j).name) > 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		if (nameDesc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).name
							.compareTo(cottages.get(j).name) < 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressAsc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).address
							.compareTo(cottages.get(j).address) > 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressDesc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).address
							.compareTo(cottages.get(j).address) < 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		if(rateAsc) {
			Collections.sort(cottages, new Comparator<Cottage>() {
			    @Override
				public int compare(Cottage o1, Cottage o2) {
			        //return o1.rating.compareTo(o2.rating);
			        return Double.compare(o1.rating, o2.rating);
			    }
			});
		}
		
		
		if(rateDesc) {
			Collections.sort(cottages, new Comparator<Cottage>() {
			    @Override
				public int compare(Cottage o1, Cottage o2) {
			    	return Double.compare(o1.rating, o2.rating);
			    }
			});
			Collections.reverse(cottages);
		}
		
		
		return new ResponseEntity<List<Cottage>>(cottages,HttpStatus.OK);
	}
	@GetMapping(path = "/getSelectedCottage/{cottageId}")
	public ResponseEntity<Cottage> getSelectedCottage(@PathVariable("cottageId") long id)
	{
		Optional<Cottage> cottage = cottageRepository.findById(id);
		Cottage cottageNew = cottage.get();
		
		return new ResponseEntity<Cottage>(cottageNew,HttpStatus.OK);
	}
	@PostMapping(path= "/addNewCottage")
	public ResponseEntity<List<Cottage>> addNewCottage(@RequestBody NewCottageDto dto){
		if(dto != null) {
			Cottage cottage = new Cottage(dto.name,dto.address,dto.description,dto.roomsNum,dto.bedsNum,dto.rules,dto.priceList,dto.pricePerHour,dto.maxAmountOfPeople);
			cottage.cottageOwner = cottageOwnerRepository.findById(dto.cottageOwnerId).get();
			cottageRepository.save(cottage);
			return new ResponseEntity<List<Cottage>>(cottageRepository.getAllCottagesForOwner(dto.cottageOwnerId),HttpStatus.OK);
		}
		return null;
	}
	@GetMapping(path="/getCottageById/{cottageId}")
	public Cottage getCottageById(@PathVariable("cottageId") long id){
		return cottageRepository.findById(id).get();
	}
	
	@PostMapping(path="/editCottage")
	public ResponseEntity<List<Cottage>> editCottage(@RequestBody EditCottageDto dto){
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
	return new ResponseEntity<List<Cottage>>(cottageRepository.getAllCottagesForOwner(dto.cottageOwnerId),HttpStatus.OK);
	}
	@PostMapping(path="/editOwner")
	public CottageOwner editOwner(@RequestBody CottageOwner owner)
	{	
		
		if(owner != null) {
			List<CottageOwner> owners = cottageOwnerRepository.findAll();
			for (CottageOwner a : owners)
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
			cottageOwnerRepository.saveAll(owners);
			return cottageOwnerRepository.findById(owner.id).get();
		}
		return null;
	}
	@GetMapping(path="/ratingforCottages/{ownerId}")
	public List<ChartInfoRatingDto> ratingForBoats(@PathVariable("ownerId") long id){
		List<ChartInfoRatingDto> info = new ArrayList<ChartInfoRatingDto>();
		for (Cottage cottage : cottageRepository.getAllCottagesForOwner(id)) {
			info.add(new ChartInfoRatingDto(cottage.name,cottage.rating));
		}
		return info;
	}
	@GetMapping(path = "/getCottagePhotos/{cottageId}")
	public Set<CottagePhoto> getAdventuresPhotos(@PathVariable("cottageId") long id)
	{	
		return cottagePhotoRepository.findCottagePhotos(id);
	}
	
	@PreAuthorize("hasAuthority('COTTAGEOWNER')")
	@PostMapping(path = "/addPhoto")
    public Set<CottagePhoto> addPhoto(@RequestBody NewPhotoDto photoDTO)
	{	
		if(photoDTO != null) {
			cottagePhotoRepository.save(new CottagePhoto(photoDTO.photo, cottageRepository.findById(photoDTO.entityId).get()));
			return cottagePhotoRepository.findCottagePhotos(photoDTO.entityId);
		}
		return null;
	}
	
	//@PreAuthorize("hasAuthority('FISHINGINSTRUCTOR')")
	@PostMapping(path = "/removePhoto/{cottageId}")
    public Set<CottagePhoto> removePhoto(@PathVariable("cottageId") long id)
	{	
		long maxId = 0;
		Set<CottagePhoto> photos = cottagePhotoRepository.findCottagePhotos(id);
		for (CottagePhoto ap : photos)
			if (ap.id > maxId)
					maxId = ap.id;
		if (maxId != 0)
			cottagePhotoRepository.deleteById(maxId);
		return cottagePhotoRepository.findCottagePhotos(id);
	}
}
