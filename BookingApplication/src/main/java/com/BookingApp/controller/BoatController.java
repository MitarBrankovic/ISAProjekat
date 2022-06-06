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
import com.BookingApp.dto.FishingInstructorAvailabilityDto;
import com.BookingApp.dto.SearchDto;
import com.BookingApp.model.Boat;
import com.BookingApp.model.BoatAppointment;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.repository.BoatAppointmentRepository;
import com.BookingApp.repository.BoatRepository;

@CrossOrigin
@RestController
@RequestMapping("/boats")
public class BoatController {

		@Autowired
		private BoatRepository boatRepository;
		@Autowired
		private BoatAppointmentRepository boatAppointmentRepository;
		
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
	}
