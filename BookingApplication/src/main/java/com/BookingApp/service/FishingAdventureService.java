package com.BookingApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.SearchDto;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.repository.FishingAdventureRepository;

@RestController
@RequestMapping("/adventure")
public class FishingAdventureService {
	
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	
	@GetMapping(path = "/getAllAdventures")
	public ResponseEntity<List<FishingAdventure>> getAllAdventures()
	{
		List<FishingAdventure> adventures = new ArrayList<FishingAdventure>();
		for(FishingAdventure boat : fishingAdventureRepository.findAll())
		{
			adventures.add(boat);
		}
		return new ResponseEntity<List<FishingAdventure>>(adventures,HttpStatus.OK);
	}
	
	@PostMapping(path = "/searchAdventures")
	public ResponseEntity<List<FishingAdventure>> searchAdventures(@RequestBody SearchDto dto)
	{
		String name = dto.name;
		String address = dto.address;
		boolean nameAsc = dto.nameAsc;
		boolean nameDesc = dto.nameDesc;
		boolean addressAsc = dto.addressAsc;
		boolean addressDesc = dto.addressDesc;
		

		List<FishingAdventure> cottages = fishingAdventureRepository.findAll();

		if (name.equals("") && address.equals(""))
			cottages = fishingAdventureRepository.findAll();
		
		if (!name.equals("")) {
			cottages =  cottages.stream().filter(m -> m.name.toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!address.equals("")) {
			cottages =  cottages.stream().filter(m -> m.address.toLowerCase().contains(address.toLowerCase()))
					.collect(Collectors.toList()); }
			
		if (nameAsc) {
			int n = cottages.size();
			FishingAdventure temp = null;
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
			FishingAdventure temp = null;
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
			FishingAdventure temp = null;
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
			FishingAdventure temp = null;
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
		
		
		return new ResponseEntity<List<FishingAdventure>>(cottages,HttpStatus.OK);
	}

}
