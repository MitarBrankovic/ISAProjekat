package com.BookingApp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageOwner;
import com.BookingApp.dto.ClientRatingDto;
import com.BookingApp.dto.RatingAdvDto;
import com.BookingApp.dto.RatingBoatDto;
import com.BookingApp.dto.RatingCottDto;
import com.BookingApp.dto.RatingCottOwnerDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.Boat;
import com.BookingApp.model.FishingAdventure;
import com.BookingApp.model.RatingFishingAdventure;
import com.BookingApp.model.UserType;
import com.BookingApp.model.RatingCottage;
import com.BookingApp.model.RatingCottageOwner;
import com.BookingApp.model.RatingBoat;
import com.BookingApp.repository.BoatRepository;
import com.BookingApp.repository.CottageOwnerRepository;
import com.BookingApp.repository.CottageRepository;
import com.BookingApp.repository.FishingAdventureRepository;
import com.BookingApp.repository.RatingBoatRepository;
import com.BookingApp.repository.RatingCottageOwnerRepository;
import com.BookingApp.repository.RatingCottageRepository;
import com.BookingApp.repository.RatingFishingAdventureRepository;
import com.BookingApp.repository.UserRepository;
import com.BookingApp.service.RatingService;


@CrossOrigin
@RestController
@RequestMapping("rating")
public class RatingController {

	@Autowired
	private RatingCottageRepository ratingCottageRepository;
	@Autowired
	private RatingBoatRepository ratingBoatRepository;
	@Autowired
	private RatingFishingAdventureRepository ratingFishingAdventureRepository;
	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private BoatRepository boatRepository;
	@Autowired
	private FishingAdventureRepository fishingAdventureRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private RatingService ratingService;
	@Autowired
	private RatingCottageOwnerRepository ratingCottageOwnerRepository;
	@Autowired
	private CottageOwnerRepository cottageOwnerRepository;
	
	
	@PostMapping(path="/rateAdventure")
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean rateAdventure(@RequestBody RatingAdvDto dto)
	{
		return ratingService.rateAdventure(dto);
	}
	
	@GetMapping(path="/formAdvRating")
	public void formAdvRating()
	{
		ratingService.formAdvRating();
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
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean rateCottage(@RequestBody RatingCottDto dto) throws Exception
	{
		return ratingService.rateCottage(dto);
	}
	
	
	@GetMapping(path="/formCottRating")
	public void formCottRating()
	{
		ratingService.formCottRating();
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
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean rateBoat(@RequestBody RatingBoatDto dto)
	{
		return ratingService.rateBoat(dto);
	}
	
	@GetMapping(path="/formBoatRating")
	public void formBoatRating()
	{
		ratingService.formBoatRating();
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
	
	
	
	
	@PostMapping(path="/rateCottageOwner")
	@PreAuthorize("hasAuthority('CLIENT')")
	public boolean rateCottageOwner(@RequestBody RatingCottOwnerDto dto) throws Exception
	{
		CottageOwner cot = cottageOwnerRepository.findById(dto.cottageOwner.id).get();
		RatingCottageOwner rate = new RatingCottageOwner(cot, dto.rating, LocalDateTime.now(), dto.client, false, dto.revision); 
		
		for(RatingCottageOwner r: ratingCottageOwnerRepository.findAll()) {
			if(r.client.id == dto.client.id && r.cottageOwner.id == dto.cottageOwner.id)
				return false;
		}
		ratingCottageOwnerRepository.save(rate);
		formCottOwnerRating();
		return true;
	}
	
	
	@GetMapping(path="/formCottOwnerRating")
	public void formCottOwnerRating()
	{
		for(CottageOwner cottageOwner : cottageOwnerRepository.findAll()){
			int counter = 0;
			int sum = 0;
			for (RatingCottageOwner ratingAdventure : ratingCottageOwnerRepository.findAll()) {
				if(ratingAdventure.cottageOwner.id == cottageOwner.id){
					counter ++;
					sum += ratingAdventure.rating;
				}
			}
			if(counter != 0){
				cottageOwner.rating = (double)sum/counter;
				cottageOwnerRepository.save(cottageOwner);
			}
		}
	}
	
	
	@GetMapping(path="/getMyRatedCottOwners/{email}")
	public ResponseEntity<List<RatingCottageOwner>> getMyRatedCottOwners(@PathVariable String email)
	{
		List<RatingCottageOwner> ratedCottOwners = new ArrayList<RatingCottageOwner>();
		for(RatingCottageOwner cottRating : ratingCottageOwnerRepository.findAll())
		{
			if(cottRating.client.email.equalsIgnoreCase(email))
			{
				ratedCottOwners.add(cottRating);
			}
		}
		return new ResponseEntity<List<RatingCottageOwner>>(ratedCottOwners,HttpStatus.OK);
		
	}
	
	
	
	
	
	//ADMIN ########################################################################
	
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
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path="/declineRating")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public List<ClientRatingDto> declineRating(@RequestBody ClientRatingDto dto) throws Exception
	{
		AppUser user = userRepository.findByIdPess(dto.ownerId);
		Thread.sleep(3000);
		if (user.role == UserType.cottage_owner)
			ratingCottageRepository.deleteById(dto.ratingId);
		else if (user.role == UserType.fishing_instructor)
			ratingFishingAdventureRepository.deleteById(dto.ratingId);
		else
			ratingBoatRepository.deleteById(dto.ratingId);
		return getUnapprovedRatings();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@PostMapping(path="/approveRating")
	public List<ClientRatingDto> approveRating(@RequestBody ClientRatingDto dto) throws Exception
	{
		AppUser user = userRepository.findByIdPess(dto.ownerId);
		Thread.sleep(3000);
		if (user.role == UserType.cottage_owner) {
			updateCottageRatings(dto);
			ratingCottageRepository.deleteById(dto.ratingId);
		}
		else if (user.role == UserType.fishing_instructor) {
			updateFishingRatings(dto);
			ratingFishingAdventureRepository.deleteById(dto.ratingId);
		}
		else {
			updateBoatRatings(dto);
			ratingBoatRepository.deleteById(dto.ratingId);
		}
		sendRatingEmail(dto);
		return getUnapprovedRatings();
	}
	
	@Transactional(readOnly = false)
	private void updateCottageRatings(ClientRatingDto dto) {
		List <RatingCottage> cottageRatings = new ArrayList<RatingCottage>();
		for (RatingCottage rc: ratingCottageRepository.findAll()) {
			if (rc.id == dto.ratingId)
				rc.isApproved = true;
			cottageRatings.add(rc);
		}
		ratingCottageRepository.saveAll(cottageRatings);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	private void updateFishingRatings(ClientRatingDto dto) {
		List <RatingFishingAdventure> fishingRatings = new ArrayList<RatingFishingAdventure>();
		for (RatingFishingAdventure rf: ratingFishingAdventureRepository.findAll()) {
			if (rf.id == dto.ratingId)
				rf.isApproved = true;
			fishingRatings.add(rf);
		}
		ratingFishingAdventureRepository.saveAll(fishingRatings);
	}
	
	@Transactional(readOnly = false)
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
