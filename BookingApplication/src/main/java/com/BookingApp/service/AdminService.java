package com.BookingApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.AppUser;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.RequestDeleteAccRepository;
import com.BookingApp.repository.UserRepository;

@RestController
@RequestMapping("/admin")
public class AdminService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RequestDeleteAccRepository requestDeleteAccRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@PostMapping(path="/acceptRequest")
	public ResponseEntity<List<RequestDeleteAcc>> acceptRequest(@RequestBody RequestDeleteAcc request)
	{	
		String title = "Request for account deletion";
		String body = "Hello,\nYour request for account deletion has been accepted.\n" 
				  + "\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		Optional<AppUser> oldUser = userRepository.findById(request.appUserId);
		AppUser appUser;
		if(oldUser.isPresent()) {
			appUser = oldUser.get();
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(appUser.email,body,title);		
					}
				};
				t.start();
				userRepository.deleteById(request.appUserId);
				requestDeleteAccRepository.delete(request);
				return new ResponseEntity<List<RequestDeleteAcc>>(requestDeleteAccRepository.findAll(),HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				return null;
			}
		}			
		else
			return null;
	}
	
	
	@PostMapping(path="/declineRequest")
	public ResponseEntity<List<RequestDeleteAcc>> declineRequest(@RequestBody RequestDeleteAcc request)
	{	
		String title = "Request for account deletion";
		String body = "Hello,\nYour request for account deletion has been declined.\n" 
				  + "\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		Optional<AppUser> oldUser = userRepository.findById(request.appUserId);
		AppUser appUser;
		if(oldUser.isPresent()) {
			appUser = oldUser.get();
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(appUser.email,body,title);		
					}
				};
				t.start();
			requestDeleteAccRepository.delete(request);
			return new ResponseEntity<List<RequestDeleteAcc>>(requestDeleteAccRepository.findAll(),HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				return null;
			}
		}			
		else
			return null;
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
