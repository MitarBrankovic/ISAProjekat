package com.BookingApp.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PreRemove;

import java.util.Optional;
import java.util.Set;

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

import com.BookingApp.dto.EditAdventureDto;
import com.BookingApp.dto.FishingAppointmentDto;
import com.BookingApp.dto.NewAdventureDto;
import com.BookingApp.dto.NewPhotoDto;
import com.BookingApp.dto.PricelistItemRemoveDto;
import com.BookingApp.dto.SearchAdventureDto;
import com.BookingApp.dto.SearchInstructorsAdventuresDto;
import com.BookingApp.model.AdventurePhoto;
import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.PricelistItem;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.AdventurePhotoRepository;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.FishingAppointmentRepository;
import com.BookingApp.repository.FishingInstructorRepository;

@CrossOrigin
@RestController
@RequestMapping("/fishingAdventures")
public class FishingAdventureController {
	
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	@Autowired
	private FishingInstructorRepository fishingInstructorRepository;
	@Autowired
	private FishingAppointmentRepository fishingAppointmentRepository;
	@Autowired
	private AdventurePhotoRepository adventurePhotoRepository;
	
	@GetMapping(path = "/getAllAdventures")
	public ResponseEntity<List<FishingAdventure>> getAllAdventures()
	{
		List<FishingAdventure> adventures = new ArrayList<FishingAdventure>();
		for(FishingAdventure adventure : fishingAdventureRepository.findAll())
		{
			adventures.add(adventure);

		}
		return new ResponseEntity<List<FishingAdventure>>(adventures,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getFishingInstructorsAdventures/{instructorsId}")
	public Set<FishingAdventure> getInstructorsAdventures(@PathVariable("instructorsId") long id)
	{
		return fishingAdventureRepository.findInstructorsAdventures(id);
	}
	
	@GetMapping(path = "/getSelectedAdventure/{adventureId}")
	public ResponseEntity<FishingAdventure> getSelectedAdventure(@PathVariable("adventureId") long id)
	{
		Optional<FishingAdventure> adventure = fishingAdventureRepository.findById(id);
		FishingAdventure adventureNew = adventure.get();
		
		return new ResponseEntity<FishingAdventure>(adventureNew ,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getAdventuresPhotos/{adventureId}")
	public Set<AdventurePhoto> getAdventuresPhotos(@PathVariable("adventureId") long id)
	{	
		return adventurePhotoRepository.findAdventuresPhotos(id);
	}
	
	@PreAuthorize("hasAuthority('FISHINGINSTRUCTOR')")
	@PostMapping(path = "/addPhoto")
    public Set<AdventurePhoto> addPhoto(@RequestBody NewPhotoDto photoDTO)
	{	
		if(photoDTO != null) {
			adventurePhotoRepository.save(new AdventurePhoto(photoDTO.photo, fishingAdventureRepository.findById(photoDTO.entityId).get()));
			return adventurePhotoRepository.findAdventuresPhotos(photoDTO.entityId);
		}
		return null;
	}
	
	@PreAuthorize("hasAuthority('FISHINGINSTRUCTOR')")
	@PostMapping(path = "/removePhoto/{adventureId}")
    public Set<AdventurePhoto> removePhoto(@PathVariable("adventureId") long id)
	{	
		long maxId = 0;
		Set<AdventurePhoto> photos = adventurePhotoRepository.findAdventuresPhotos(id);
		for (AdventurePhoto ap : photos)
			if (ap.id > maxId)
					maxId = ap.id;
		if (maxId != 0)
			adventurePhotoRepository.deleteById(maxId);
		return adventurePhotoRepository.findAdventuresPhotos(id);
	}
	
	@PreAuthorize("hasAuthority('FISHINGINSTRUCTOR')")
	@PostMapping(path = "/addNewAdventure")
    public Set<FishingAdventure> addAdventure(@RequestBody NewAdventureDto adventureDTO)
	{	
		//byte[] decodedPhoto = Base64.getMimeDecoder().decode(adventureDTO.photo);
		if(adventureDTO != null) {
			FishingAdventure adventure = new FishingAdventure(adventureDTO.name, adventureDTO.address, adventureDTO.city, adventureDTO.description, 
					adventureDTO.photo, adventureDTO.maxAmountOfPeople, adventureDTO.behaviourRules, adventureDTO.equipment, 
					adventureDTO.pricePerHour, 0, adventureDTO.cancellingPrecentage);
			adventure.fishingInstructor = fishingInstructorRepository.findById(adventureDTO.instructorsId).get();
			fishingAdventureRepository.save(adventure);
			return fishingAdventureRepository.findInstructorsAdventures(adventureDTO.instructorsId);
		}
		return null;
	}
	
	@PreAuthorize("hasAuthority('FISHINGINSTRUCTOR')")
	@PostMapping(path = "/editAdventure")
    public Set<FishingAdventure> editAdventure(@RequestBody EditAdventureDto adventureDTO)
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
			return fishingAdventureRepository.findInstructorsAdventures(instructorsId);
		}
		return null;
	}
	
	//@PreAuthorize("hasAuthority('FISHINGINSTRUCTOR')")
	@PostMapping(path = "/checkAdventureRemoval/{adventureId}")
    public boolean sendRequest(@PathVariable("adventureId") long id)
	{	
		Set<FishingAppointment> appointments = fishingAppointmentRepository.findAdventuresAppointments(id);
		for (FishingAppointment fa : appointments) 
			if (fa.client != null)
				return false;
		return true;
	}
	
	//@PreAuthorize("hasRole('FISHINGINSTRUCTOR') or hasRole('ADMIN')")
	@PostMapping(path = "/removeAdventure/{adventureId}")
    public Set<FishingAdventure> removeAdventure(@PathVariable("adventureId") long id)
	{	
		long instructorsId = fishingAdventureRepository.findById(id).get().fishingInstructor.id;
		fishingAdventureRepository.deleteById(id);
		return fishingAdventureRepository.findInstructorsAdventures(instructorsId);
	}
	
	@PostMapping(path = "/searchAdventures")
	public ResponseEntity<List<FishingAdventure>> searchAdventures(@RequestBody SearchAdventureDto dto)
	{
		String name = dto.name;
		String address = dto.address;
		String instructor = dto.instructor;
		boolean nameAsc = dto.nameAsc;
		boolean nameDesc = dto.nameDesc;
		boolean addressAsc = dto.addressAsc;
		boolean addressDesc = dto.addressDesc;
		boolean rateAsc = dto.rateAsc;
		boolean rateDesc = dto.rateDesc;
		

		List<FishingAdventure> adventures = fishingAdventureRepository.findAll();

		if (name.equals("") && address.equals("") && instructor.equals(""))
			adventures = fishingAdventureRepository.findAll();
		
		if (!name.equals("")) {
			adventures =  adventures.stream().filter(m -> m.name.toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!address.equals("")) {
			adventures =  adventures.stream().filter(m -> m.address.toLowerCase().contains(address.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!instructor.equals("")) {
			adventures =  adventures.stream().filter(m -> (m.fishingInstructor.name + " " + m.fishingInstructor.surname).toLowerCase().contains(instructor.toLowerCase()))
					.collect(Collectors.toList()); }
			
		if (nameAsc) {
			int n = adventures.size();
			FishingAdventure temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (adventures.get(j - 1).name
							.compareTo(adventures.get(j).name) > 0) {
						// swap elements
						temp = adventures.get(j - 1);
						adventures.set(j - 1, adventures.get(j));
						adventures.set(j, temp);
					}

				}
			}
		}
		
		
		if (nameDesc) {
			int n = adventures.size();
			FishingAdventure temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (adventures.get(j - 1).name
							.compareTo(adventures.get(j).name) < 0) {
						// swap elements
						temp = adventures.get(j - 1);
						adventures.set(j - 1, adventures.get(j));
						adventures.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressAsc) {
			int n = adventures.size();
			FishingAdventure temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (adventures.get(j - 1).address
							.compareTo(adventures.get(j).address) > 0) {
						// swap elements
						temp = adventures.get(j - 1);
						adventures.set(j - 1, adventures.get(j));
						adventures.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressDesc) {
			int n = adventures.size();
			FishingAdventure temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (adventures.get(j - 1).address
							.compareTo(adventures.get(j).address) < 0) {
						// swap elements
						temp = adventures.get(j - 1);
						adventures.set(j - 1, adventures.get(j));
						adventures.set(j, temp);
					}

				}
			}
		}
		
		if(rateAsc) {
			Collections.sort(adventures, new Comparator<FishingAdventure>() {
			    @Override
				public int compare(FishingAdventure o1, FishingAdventure o2) {
			        //return o1.rating.compareTo(o2.rating);
			        return Double.compare(o1.rating, o2.rating);
			    }
			});
		}
		
		
		if(rateDesc) {
			Collections.sort(adventures, new Comparator<FishingAdventure>() {
			    @Override
				public int compare(FishingAdventure o1, FishingAdventure o2) {
			    	return Double.compare(o1.rating, o2.rating);
			    }
			});
			Collections.reverse(adventures);
		}
		
		
		return new ResponseEntity<List<FishingAdventure>>(adventures,HttpStatus.OK);
	}
	
	public List<FishingAdventure> getInstructorsSearchedAdventures(long id)
	{
		List<FishingAdventure> adventures = new ArrayList<FishingAdventure>();
		for(FishingAdventure adventure : fishingAdventureRepository.findAll())
		{
			if (adventure.fishingInstructor.id == id)
				adventures.add(adventure);
		}
		return adventures;
	}
	
	
	@PostMapping(path = "/searchInstructorsAdventures")
	public ResponseEntity<List<FishingAdventure>> searchInstructorsAdventures(@RequestBody SearchInstructorsAdventuresDto dto)
	{
		String name = dto.name;
		String address = dto.address;
		boolean nameAsc = dto.nameAsc;
		boolean nameDesc = dto.nameDesc;
		boolean addressAsc = dto.addressAsc;
		boolean addressDesc = dto.addressDesc;
		

		List<FishingAdventure> adventures = getInstructorsSearchedAdventures(dto.instructorsId);

		if (name.equals("") && address.equals(""))
			adventures = getInstructorsSearchedAdventures(dto.instructorsId);
		
		if (!name.equals("")) {
			adventures =  adventures.stream().filter(m -> m.name.toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!address.equals("")) {
			adventures =  adventures.stream().filter(m -> m.address.toLowerCase().contains(address.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (nameAsc) {
			int n = adventures.size();
			FishingAdventure temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (adventures.get(j - 1).name
							.compareTo(adventures.get(j).name) > 0) {
						// swap elements
						temp = adventures.get(j - 1);
						adventures.set(j - 1, adventures.get(j));
						adventures.set(j, temp);
					}

				}
			}
		}
		
		
		if (nameDesc) {
			int n = adventures.size();
			FishingAdventure temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (adventures.get(j - 1).name
							.compareTo(adventures.get(j).name) < 0) {
						// swap elements
						temp = adventures.get(j - 1);
						adventures.set(j - 1, adventures.get(j));
						adventures.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressAsc) {
			int n = adventures.size();
			FishingAdventure temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (adventures.get(j - 1).address
							.compareTo(adventures.get(j).address) > 0) {
						// swap elements
						temp = adventures.get(j - 1);
						adventures.set(j - 1, adventures.get(j));
						adventures.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressDesc) {
			int n = adventures.size();
			FishingAdventure temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (adventures.get(j - 1).address
							.compareTo(adventures.get(j).address) < 0) {
						// swap elements
						temp = adventures.get(j - 1);
						adventures.set(j - 1, adventures.get(j));
						adventures.set(j, temp);
					}

				}
			}
		}
		
		
		return new ResponseEntity<List<FishingAdventure>>(adventures,HttpStatus.OK);
	}
}
