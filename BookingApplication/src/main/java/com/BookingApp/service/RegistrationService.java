package com.BookingApp.service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
			String body = "Hello,\nThank you for registering on our website. Below is your verification code.\n" 
							  + "Your Code is: " + verificationCode + 
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
					
				if(appUser.role == UserType.client) {
					Client client = new Client(appUser, "");
					clientRepository.save(client);
				}
				else if(appUser.role == UserType.cottage_owner) {
					CottageOwner cottageOwner = new CottageOwner(appUser, "");
					cottageOwnerRepository.save(cottageOwner);
				}
				else if(appUser.role == UserType.fishing_instructor) {
					FishingInstructor fishingInstructor = new FishingInstructor(appUser, "");
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
    public boolean verify(@RequestBody String get)
	{	
		String[] tokens = get.split("%3B"); // Ascii ;
		String code = tokens[0];
		String badEmail = tokens[1];
		
		String emailParts[] = badEmail.split("%40"); // Ascii @
		String email = emailParts[0] + "@" + emailParts[1].substring(0, emailParts[1].length() - 1);
		
		AppUser user = userRepository.findByEmail(email);
		if(user != null)
		{
			if(user.verificationCode.equalsIgnoreCase(code))
			{
				user.verified = true;
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
			else
				return null;
		}
	}
}
