package com.BookingApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.Cottage;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.repository.FishingAdventureRepository;

@RestController
@RequestMapping("/fishingAdventures")
public class FishingAdventureService {
	
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	
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
}
