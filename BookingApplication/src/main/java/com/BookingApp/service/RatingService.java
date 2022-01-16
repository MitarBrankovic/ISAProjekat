package com.BookingApp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.BookingApp.dto.RatingAdvDto;
import com.BookingApp.dto.RatingBoatDto;
import com.BookingApp.dto.RatingCottDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.Boat;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.RatingBoat;
import com.BookingApp.model.RatingCottage;
import com.BookingApp.model.RatingFishingAdventure;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.CottageRepository;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.RatingBoatRepository;
import com.BookingApp.repository.RatingCottageRepository;
import com.BookingApp.repository.RatingFishingAdventureRepository;
import com.BookingApp.repository.UserRepository;

@Service
@Transactional(readOnly = true)
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
	
	
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean rateAdventure(RatingAdvDto dto)
	{
		for(RatingFishingAdventure r: ratingFishingAdventureRepository.findAll()) {
			if(r.client.id == dto.client.id && r.fishingAdventure.id == dto.fishingAdventure.id)
				return false;
		}
		FishingAdventure adv = fishingAdventureRepository.findByIdPess(dto.fishingAdventure.id);
		RatingFishingAdventure rate = new RatingFishingAdventure(adv, dto.rating, LocalDateTime.now(), dto.client, false, dto.revision);
		ratingFishingAdventureRepository.save(rate);
		formAdvRating();
		return true;
	}
	
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
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean rateCottage(RatingCottDto dto) throws InterruptedException
	{
		Cottage cot = cottageRepository.findByIdPess(dto.cottage.id);
		RatingCottage rate = new RatingCottage(cot, dto.rating, LocalDateTime.now(), dto.client, false, dto.revision); 
		
		for(RatingCottage r: ratingCottageRepository.findAll()) {
			if(r.client.id == dto.client.id && r.cottage.id == dto.cottage.id)
				return false;
		}
		//Thread.sleep(5000);
		ratingCottageRepository.save(rate);
		formCottRating();
		return true;
	}
	
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
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean rateBoat(RatingBoatDto dto)
	{
		for(RatingBoat r: ratingBoatRepository.findAll()) {
			if(r.client.id == dto.client.id && r.boat.id == dto.boat.id)
				return false;
		}
		Boat bot = boatRepository.findByIdPess(dto.boat.id);
		RatingBoat rate = new RatingBoat(bot, dto.rating, LocalDateTime.now(),  dto.client, false, dto.revision); 
		ratingBoatRepository.save(rate);
		formBoatRating();
		return true;
	}
	
	public void formBoatRating()
	{
		for(Boat boat : boatRepository.findAll()){
			int counter = 0;
			int sum = 0;
			for (RatingBoat ratingAdventure : ratingBoatRepository.findAll()) {
				if(ratingAdventure.boat.id == boat.id){
					counter ++;
					sum += ratingAdventure.rating;
				}
			}
			if(counter != 0){
				boat.rating = (double)sum/counter;
				boatRepository.save(boat);
			}
		}
	}
	
}
