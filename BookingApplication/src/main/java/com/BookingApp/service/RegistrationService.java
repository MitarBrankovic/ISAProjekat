package com.BookingApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.AppUser;
import com.BookingApp.model.Client;
import com.BookingApp.model.CottageOwner;
import com.BookingApp.model.FishingInstructor;
import com.BookingApp.model.ShipOwner;
import com.BookingApp.model.UserType;
import com.BookingApp.repository.ClientRepository;
import com.BookingApp.repository.CottageOwnerRepository;
import com.BookingApp.repository.FishingInstructorRepository;
import com.BookingApp.repository.ShipOwnerRepository;
import com.BookingApp.repository.UserRepository;


@RestController
@RequestMapping("/registration")
public class RegistrationService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CottageOwnerRepository cottageOwnerRepository;
	@Autowired
	private FishingInstructorRepository fishingInstructorRepository;
	@Autowired
	private ShipOwnerRepository shipOwnerRepository;
	
	
	@PostMapping(path = "/registerUser")
	public boolean registerUser(@RequestBody AppUser appUser)
	{	
		Optional<AppUser> oldUser = Optional.ofNullable(userRepository.findByEmail(appUser.email)); // Mail -> Korisnik
		if(!oldUser.isPresent()) {
			
			/*String verificationCode = generateVerificationCode();
			if(!verification.containsKey(patient.getEmail()))
			{
				verification.put(patient.getEmail(), verificationCode);	
			}
			
			String body = "Hello,\nThank you for registering on our website. Below is your verification code.\n" 
							  + "Your Code is: " + verificationCode + 
							    "\nIf you have any trouble, write to our support : mrs.tim.sedam@gmail.com";
			
			String title = "Verification Code";
			
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmailService.sendEmail(patient.getEmail(),body,title);		
					}
				};
				t.start();*/
				
				//Dodajemo mu status neaktivnog i verifikacioni kod
				//patient.setAuthenticated("0");
				//patient.setVerificationCode(verificationCode);
			
			
				if(appUser.role == UserType.client) {
					Client client = new Client(appUser, "");
					clientRepository.save(client);
				}
				else if(appUser.role == UserType.cottageOwner) {
					CottageOwner cottageOwner = new CottageOwner(appUser, "");
					cottageOwnerRepository.save(cottageOwner);
				}
				else if(appUser.role == UserType.fishingInstructor) {
					FishingInstructor fishingInstructor = new FishingInstructor(appUser, "");
					fishingInstructorRepository.save(fishingInstructor);
				}
				else if(appUser.role == UserType.shipOwner) {
					ShipOwner shipOwner = new ShipOwner(appUser, "");
					shipOwnerRepository.save(shipOwner);
				}
				
				return true;
			/*} 
			catch (Exception e) 
			{
				return false;
			}*/
			
		}
		System.out.println("Korisnik sa ovim mailom postoji ili je nepostojeci mail.");
		return false;
	}
}
