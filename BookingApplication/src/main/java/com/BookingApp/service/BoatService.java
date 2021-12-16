package com.BookingApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.SearchDto;
import com.BookingApp.model.Boat;
import com.BookingApp.repository.BoatRepository;


@RestController
@RequestMapping("/boats")
public class BoatService {

		@Autowired
		private BoatRepository boatRepository;
		
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
		
		
		@GetMapping(path = "/getSelectedBoat/{boatId}")
		public ResponseEntity<Boat> getSelectedBoat(@PathVariable("boatId") long id)
		{
			Optional<Boat> boat = boatRepository.findById(id);
			Boat boatNew = boat.get();
			
			return new ResponseEntity<Boat>(boatNew,HttpStatus.OK);
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
			
			
			return new ResponseEntity<List<Boat>>(boats,HttpStatus.OK);
		}
	}
