package com.BookingApp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.Cottage;
import com.BookingApp.dto.ClientRatingDto;
import com.BookingApp.dto.RatingAdvDto;
import com.BookingApp.dto.RatingBoatDto;
import com.BookingApp.dto.RatingCottDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.Boat;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.RatingFishingAdventure;
import com.BookingApp.model.UserType;
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
	@Autowired
	private JavaMailSender javaMailSender;
	
	
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
	
	
	@PostMapping(path="/rateBoat")
	public boolean rateBoat(@RequestBody RatingBoatDto dto)
	{
		for(RatingBoat r: ratingBoatRepository.findAll()) {	//optimizovati
			if(r.client.id == dto.client.id && r.boat.id == dto.boat.id)
				return false;
		}
		
		RatingBoat rate = new RatingBoat(); 
		rate.boat = dto.boat;
		rate.client = dto.client;
		rate.rating = dto.rating;
		rate.date = LocalDateTime.now();
		rate.revision = dto.revision;
		rate.isApproved = false;
		ratingBoatRepository.save(rate);
		formBoatRating();
		return true;
	}
	
	@GetMapping(path="/formBoatRating")
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
	
	@GetMapping(path="/getMyRatedBoats/{email}")
	public ResponseEntity<List<RatingBoat>> getMyRatedBoats(@PathVariable String email)
	{
		List<RatingBoat> ratedBoats = new ArrayList<RatingBoat>();
		for(RatingBoat boatRating : ratingBoatRepository.findAll())
		{
			if(boatRating.client.email.equalsIgnoreCase(email))
			{
				ratedBoats.add(boatRating);
			}
		}
		return new ResponseEntity<List<RatingBoat>>(ratedBoats,HttpStatus.OK);
		
	}
	
	@GetMapping(path="/getRatings")
	public List<ClientRatingDto> getRatings()
	{
		return getUnapprovedRatings();
	}
	
	public List<ClientRatingDto> getUnapprovedRatings() {
		List <ClientRatingDto> allRatings = getCottageRatings();
		allRatings.addAll(getBoatsRatings());
		allRatings.addAll(getFishingRatings());
		return allRatings;
	}
	
	@PostMapping(path="/declineRating")
	public List<ClientRatingDto> declineRationg(@RequestBody ClientRatingDto dto)
	{
		if (userRepository.findById(dto.ownerId).get().role == UserType.cottage_owner)
			ratingCottageRepository.deleteById(dto.ratingId);
		else if (userRepository.findById(dto.ownerId).get().role == UserType.fishing_instructor)
			ratingFishingAdventureRepository.deleteById(dto.ratingId);
		else
			ratingBoatRepository.deleteById(dto.ratingId);
		return getUnapprovedRatings();
	}
	
	@PostMapping(path="/approveRating")
	public List<ClientRatingDto> approveRating(@RequestBody ClientRatingDto dto)
	{
		if (userRepository.findById(dto.ownerId).get().role == UserType.cottage_owner)
			updateCottageRatings(dto);
		else if (userRepository.findById(dto.ownerId).get().role == UserType.fishing_instructor)
			updateFishingRatings(dto);
		else
			updateBoatRatings(dto);
		sendRatingEmail(dto);
		return getUnapprovedRatings();
	}
	
	private void updateCottageRatings(ClientRatingDto dto) {
		List <RatingCottage> cottageRatings = new ArrayList<RatingCottage>();
		for (RatingCottage rc: ratingCottageRepository.findAll()) {
			if (rc.id == dto.ratingId)
				rc.isApproved = true;
			cottageRatings.add(rc);
		}
		ratingCottageRepository.saveAll(cottageRatings);
	}
	
	private void updateFishingRatings(ClientRatingDto dto) {
		List <RatingFishingAdventure> fishingRatings = new ArrayList<RatingFishingAdventure>();
		for (RatingFishingAdventure rf: ratingFishingAdventureRepository.findAll()) {
			if (rf.id == dto.ratingId)
				rf.isApproved = true;
			fishingRatings.add(rf);
		}
		ratingFishingAdventureRepository.saveAll(fishingRatings);
	}
	
	private void updateBoatRatings(ClientRatingDto dto) {
		List <RatingBoat> boatRatings = new ArrayList<RatingBoat>();
		for (RatingBoat rb: ratingBoatRepository.findAll()) {
			if (rb.id == dto.ratingId)
				rb.isApproved = true;
			boatRatings.add(rb);
		}
		ratingBoatRepository.saveAll(boatRatings);
	}
	
	public List<ClientRatingDto> getCottageRatings()
	{
		List <ClientRatingDto> cottageRatings = new ArrayList<ClientRatingDto>();
		for (RatingCottage rg : ratingCottageRepository.findAllUnapproved()) {
			cottageRatings.add(new ClientRatingDto(rg.id, rg.cottage.cottageOwner.id, rg.client.name + " " + rg.client.surname, rg.client.email, 
					rg.cottage.name, rg.cottage.address, rg.cottage.cottageOwner.name + " " + rg.cottage.cottageOwner.surname, rg.cottage.cottageOwner.email, rg.rating, rg.revision, rg.date.toString()));
		}
		return cottageRatings;
	}
	
	public List<ClientRatingDto> getBoatsRatings()
	{
		List <ClientRatingDto> boatRatings = new ArrayList<ClientRatingDto>();
		for (RatingBoat rb : ratingBoatRepository.findAllUnapproved()) {
			boatRatings.add(new ClientRatingDto(rb.id, rb.boat.shipOwner.id, rb.client.name + " " + rb.client.surname, rb.client.email, 
					rb.boat.name, rb.boat.address, rb.boat.shipOwner.name + " " + rb.boat.shipOwner.surname, rb.boat.shipOwner.email, rb.rating, rb.revision, rb.date.toString()));
		}
		return boatRatings;
	}
	
	public List<ClientRatingDto> getFishingRatings()
	{
		List <ClientRatingDto> fishingRatings = new ArrayList<ClientRatingDto>();
		for (RatingFishingAdventure rf : ratingFishingAdventureRepository.findAllUnapproved()) {
			fishingRatings.add(new ClientRatingDto(rf.id, rf.fishingAdventure.fishingInstructor.id, rf.client.name + " " + rf.client.surname, rf.client.email, 
					rf.fishingAdventure.name, rf.fishingAdventure.address, rf.fishingAdventure.fishingInstructor.name + " " + rf.fishingAdventure.fishingInstructor.surname, 
					rf.fishingAdventure.fishingInstructor.email, rf.rating, rf.revision, rf.date.toString()));
		}
		return fishingRatings;
	}
	
	public boolean sendRatingEmail(@RequestBody ClientRatingDto rating)
	{	
		String title = "New rating notification";
		String body = "Hello,\nOne of your entites has been rated recently.\n" 
					+	"\nUser : " + rating.clientName +"\n"
					+	"\nUser E-mail : " + rating.clientEmail +"\n"
					+	"\nEntity : " + rating.entityName +"\n"
					+	"\nEntity address: " + rating.entityAddress +"\n"
					+	"\nRating : " + rating.rating + "\n"
					+	"\nComment : " + rating.comment +"\n"
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(rating.ownerEmail,body,title);	
					}
				};
				t.start();
				return true;
			} 
			catch (Exception e) 
			{
				return false;
			}
	}
	
	public void sendEmail(String to, String body, String title)
	{

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(title);
		msg.setText(body);
		javaMailSender.send(msg);
		System.out.println("Email sent...");
	}
}
