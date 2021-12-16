package com.BookingApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.SearchAdventureDto;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.repository.FishingAdventureRepository;

@RestController
@RequestMapping("/fishingAdventures")
public class FishingAdventureService {
	
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	
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
	public ResponseEntity<List<FishingAdventure>> getInstructorsAdventures(@PathVariable("instructorsId") long id)
	{
		List<FishingAdventure> adventures = new ArrayList<FishingAdventure>();
		for(FishingAdventure adventure : fishingAdventureRepository.findAll())
		{
			if (adventure.fishingInstructor.id == id)
				adventures.add(adventure);
		}
		return new ResponseEntity<List<FishingAdventure>>(adventures,HttpStatus.OK);
	}
	@GetMapping(path = "/getSelectedAdventure/{adventureId}")
	public ResponseEntity<FishingAdventure> getSelectedCottage(@PathVariable("adventureId") long id)
	{
		Optional<FishingAdventure> adventure = fishingAdventureRepository.findById(id);
		FishingAdventure adventureNew = adventure.get();
		
		return new ResponseEntity<FishingAdventure>(adventureNew ,HttpStatus.OK);
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
		
		
		return new ResponseEntity<List<FishingAdventure>>(adventures,HttpStatus.OK);
	}
}
