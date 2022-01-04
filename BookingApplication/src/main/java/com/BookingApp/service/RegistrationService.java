package com.BookingApp.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static HashMap<String, String> verification = new HashMap<String, String>();
	
	
	@PostMapping(path = "/registerUser")
	public boolean registerUser(@RequestBody AppUser appUser)
	{	
		
		Optional<AppUser> oldUser = Optional.ofNullable(userRepository.findByEmail(appUser.email)); // Mail -> Korisnik
		if(!oldUser.isPresent()) {
			
			String verificationCode = generateVerificationCode();
			if(!verification.containsKey(appUser.email))
			{
				verification.put(appUser.email, verificationCode);	
			}
			String link = "http://localhost:8090/#/emailVerification?userCode=" + verificationCode;
			
			String body = "Hello,\nThank you for registering on our website. Below is your verification code.\n" 
							  + "Your Code is: " + verificationCode + "\n Or you can verify your account when you click on this link:"
							  + "<a href=" + link + ">ACTIVATE ACCOUNT</a>" +
							    "\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
			String title = "Verification Code";
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(appUser.email,body,title);		
					}
				};
				t.start();	
				appUser.verified = false;
				appUser.verificationCode = verificationCode;
				
				if(!isNumber(appUser.phoneNumber))
					return false;
					
				if(appUser.role == UserType.client) {
					Client client = new Client(appUser, "");
					clientRepository.save(client);
				}
				else if(appUser.role == UserType.cottage_owner) {
					CottageOwner cottageOwner = new CottageOwner(appUser, "");
					cottageOwnerRepository.save(cottageOwner);
				}
				else if(appUser.role == UserType.fishing_instructor) {
					FishingInstructor fishingInstructor = new FishingInstructor(appUser, "", "Biografijica", LocalDateTime.now(), LocalDateTime.now().plusDays(14));
					fishingInstructorRepository.save(fishingInstructor);
				}
				else if(appUser.role == UserType.ship_owner) {
					ShipOwner shipOwner = new ShipOwner(appUser, "");
					shipOwnerRepository.save(shipOwner);
				}				
				return true;
			} 
			catch (Exception e) 
			{
				return false;
			}
		}
		System.out.println("Korisnik sa ovim mailom postoji ili je nepostojeci mail.");
		return false;
	}
	

	@PostMapping(path = "/emailVerification")
    public boolean verify(@RequestBody String userCode)
	{	
		String codeTokens = userCode;
		String code = codeTokens.split("=")[0];
		AppUser user = userRepository.findByVerificationCode(code);
		if(user != null)
		{
			if(user.verificationCode.equalsIgnoreCase(code))
			{
				user.verified = true;
				user.verificationCode = null;
				userRepository.save(user);
				return true;
			}
		}
		return false;
	}
	
	private String generateVerificationCode()
	{
		Random rand = new Random();
		String verificationCode = "";
		for(int i = 0 ; i < 6 ; i++)
		{
			verificationCode += String.valueOf(rand.nextInt(10));
		}
		return verificationCode;
	}
	
	public void sendEmail(String to, String body, String topic)
	{
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(topic);
		msg.setText(body);
		javaMailSender.send(msg);
		System.out.println("Email sent...");
	}
	
	
	@PostMapping(path = "/login/{email}/{password}")
	public AppUser loginUser(@PathVariable("email") String email, @PathVariable("password") String password)
	{	
		Optional<AppUser> user = Optional.ofNullable(userRepository.findByEmail(email));
		AppUser appUser;
		if(!user.isPresent())
		{
			return null;
		}
		else
		{
			appUser = user.get();
			if(appUser.password.equals(password) && appUser.verified)
				return appUser;
			else if(appUser.password.equals(password) && !appUser.verified && appUser.role == UserType.admin)
				return appUser;
			else
				return null;
		}
	}
	
	public boolean isNumber(String st) {
		try {
			Integer.parseInt(st);
			return true;
		}catch(NumberFormatException ex){
			return false;
		}
	}
	
	@PostMapping(path = "/registerOwner")
	public boolean registerOwner(@RequestBody AppUser appUser)
	{	
		Optional<AppUser> oldUser = Optional.ofNullable(userRepository.findByEmail(appUser.email)); // Mail -> Korisnik
		if(!oldUser.isPresent()) {
				if(!isNumber(appUser.phoneNumber))
					return false;
					
				if(appUser.role == UserType.cottage_owner) {
					CottageOwner cottageOwner = new CottageOwner(appUser, "");
					cottageOwner.verified = false;
					cottageOwnerRepository.save(cottageOwner);
				}
				else if(appUser.role == UserType.fishing_instructor) {
					FishingInstructor fishingInstructor = new FishingInstructor(appUser, "", "Biografijica", LocalDateTime.now(), LocalDateTime.now().plusDays(14));
					fishingInstructor.verified = false;
					fishingInstructorRepository.save(fishingInstructor);
				}
				else if(appUser.role == UserType.ship_owner) {
					ShipOwner shipOwner = new ShipOwner(appUser, "");
					shipOwner.verified = false;
					shipOwnerRepository.save(shipOwner);
				}				
				return true;
		}
		System.out.println("Korisnik sa ovim mailom postoji ili je nepostojeci mail.");
		return false;
	}
}
