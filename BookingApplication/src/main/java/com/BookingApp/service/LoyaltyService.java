package com.BookingApp.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.EditAdventureDto;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.LoyaltyProgram;
import com.BookingApp.repository.LoyaltyProgramRepository;

@RestController
@RequestMapping("/loyalty")
public class LoyaltyService {

	@Autowired
	private LoyaltyProgramRepository loyaltyProgramRepository;
	
	@GetMapping(path = "/getLoyalty")
	public LoyaltyProgram getLoyalty()
	{
		return loyaltyProgramRepository.getLoyalty();
	}
	
	@PostMapping(path = "/editLoyalty")
    public LoyaltyProgram editLoyalty(@RequestBody LoyaltyProgram loyalty)
	{	
		if(loyalty != null) {
			List<LoyaltyProgram> editedLoyalty = new ArrayList<LoyaltyProgram>();
			editedLoyalty.add(loyalty);
			loyaltyProgramRepository.saveAll(editedLoyalty);
			return loyaltyProgramRepository.getLoyalty();
		}
		return null;
	}
}
