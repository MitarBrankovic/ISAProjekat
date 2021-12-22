package com.BookingApp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.Cottage;
import com.BookingApp.dto.RatingAdvDto;
import com.BookingApp.dto.RatingCottDto;
import com.BookingApp.model.Boat;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.RatingFishingAdventure;
import com.BookingApp.model.RatingCottage;
import com.BookingApp.model.RatingBoat;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.CottageRepository;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.RatingBoatRepository;
import com.BookingApp.repository.RatingCottageRepository;
import com.BookingApp.repository.RatingFishingAdventureRepository;
import com.BookingApp.repository.UserRepository;



@RestController
@RequestMapping("/rating")
public class RatingService {

	@Autowired
	RatingCottageRepository ratingCottageRepository;
	@Autowired
	RatingBoatRepository ratingBoatRepository;
	@Autowired
	RatingFishingAdventureRepository ratingFishingAdventureRepository;
	@Autowired
	CottageRepository cottageRepository;
	@Autowired
	BoatRepository boatRepository;
	@Autowired
	FishingAdventureRepository fishingAdventureRepository;
	@Autowired
	UserRepository userRepository;
	
	
	@PostMapping(path="/rateAdventure")
	public boolean rateAdventure(@RequestBody RatingAdvDto dto)
	{
		for(RatingFishingAdventure r: ratingFishingAdventureRepository.findAll()) {	//optimizovati
			if(r.client.id == dto.client.id && r.fishingAdventure.id == dto.fishingAdventure.id)
				return false;
		}
		
		RatingFishingAdventure rate = new RatingFishingAdventure(); 
		rate.fishingAdventure = dto.fishingAdventure;
		rate.client = dto.client;
		rate.rating = dto.rating;
		rate.date = LocalDateTime.now();
		rate.revision = dto.revision;
		rate.isApproved = false;
		ratingFishingAdventureRepository.save(rate);
		formAdvRating();
		return true;
	}
	
	@GetMapping(path="/formAdvRating")
	public void formAdvRating()
	{
		for(FishingAdventure adventure : fishingAdventureRepository.findAll()){
			int counter = 0;
			int sum = 0;
			for (RatingFishingAdventure ratingAdventure : ratingFishingAdventureRepository.findAll()) {
				if(ratingAdventure.fishingAdventure.id == adventure.id){
					counter ++;
					sum += ratingAdventure.rating;
				}
			}
			if(counter != 0){
				adventure.rating = (double)sum/counter;
				fishingAdventureRepository.save(adventure);
			}
		}
	}
	
	@GetMapping(path="/getMyRatedAdventures/{email}")
	public ResponseEntity<List<RatingFishingAdventure>> getMyRatedAdventures(@PathVariable String email)
	{
		List<RatingFishingAdventure> ratedAdventures = new ArrayList<RatingFishingAdventure>();
		for(RatingFishingAdventure advRating : ratingFishingAdventureRepository.findAll())
		{
			if(advRating.client.email.equalsIgnoreCase(email))
			{
				ratedAdventures.add(advRating);
			}
		}
		return new ResponseEntity<List<RatingFishingAdventure>>(ratedAdventures,HttpStatus.OK);
		
	}
	
	
	
	@PostMapping(path="/rateCottage")
	public boolean rateCottage(@RequestBody RatingCottDto dto)
	{
		for(RatingCottage r: ratingCottageRepository.findAll()) {	//optimizovati
			if(r.client.id == dto.client.id && r.cottage.id == dto.cottage.id)
				return false;
		}
		
		RatingCottage rate = new RatingCottage(); 
		rate.cottage = dto.cottage;
		rate.client = dto.client;
		rate.rating = dto.rating;
		rate.date = LocalDateTime.now();
		rate.revision = dto.revision;
		rate.isApproved = false;
		ratingCottageRepository.save(rate);
		formCottRating();
		return true;
	}
	
	
	@GetMapping(path="/formCottRating")
	public void formCottRating()
	{
		for(Cottage cottage : cottageRepository.findAll()){
			int counter = 0;
			int sum = 0;
			for (RatingCottage ratingAdventure : ratingCottageRepository.findAll()) {
				if(ratingAdventure.cottage.id == cottage.id){
					counter ++;
					sum += ratingAdventure.rating;
				}
			}
			if(counter != 0){
				cottage.rating = (double)sum/counter;
				cottageRepository.save(cottage);
			}
		}
	}
	
	
	@GetMapping(path="/getMyRatedCottages/{email}")
	public ResponseEntity<List<RatingCottage>> getMyRatedCottages(@PathVariable String email)
	{
		List<RatingCottage> ratedCottages = new ArrayList<RatingCottage>();
		for(RatingCottage cottRating : ratingCottageRepository.findAll())
		{
			if(cottRating.client.email.equalsIgnoreCase(email))
			{
				ratedCottages.add(cottRating);
			}
		}
		return new ResponseEntity<List<RatingCottage>>(ratedCottages,HttpStatus.OK);
		
	}
	
}
