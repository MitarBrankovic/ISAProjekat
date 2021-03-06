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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.EntityAvailabilityDto;
import com.BookingApp.dto.SearchDto;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageRepository;

@CrossOrigin
@RestController
@RequestMapping("/cottages")
public class CottagesController {

	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private CottageAppointmentRepository cottageAppointmentRepository;
	
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
	@GetMapping(path = "/getSelectedCottage/{cottageId}")
	public ResponseEntity<Cottage> getSelectedCottage(@PathVariable("cottageId") long id)
	{
		Optional<Cottage> cottage = cottageRepository.findById(id);
		Cottage cottageNew = cottage.get();
		
		return new ResponseEntity<Cottage>(cottageNew,HttpStatus.OK);
	}
}
