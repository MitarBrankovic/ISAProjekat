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

import com.BookingApp.dto.AnswerComplaintDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.Complaint;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.ComplaintRepository;
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
	private ComplaintRepository complaintRepository;
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
	
	
	@PostMapping(path="/answerComplaint")
	public ResponseEntity<List<Complaint>> answerComplaint(@RequestBody AnswerComplaintDto answerComplaintDto)
	{	
		Complaint complaint  = answerComplaintDto.complaint;
		String titleUser = "Answer on complaint";
		String bodyUser = "Hello,\nWe're sorry that you have complaints.\n" 
					+	"\nWe will try to fix that issue.\n" + answerComplaintDto.text
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		String titleOwner = "Complaint on your product";
		String bodyOwner = "Hello,\nYou have recived complaint on your product.\n" 
					+	"\nPlease try to fix that issue.\n"  + answerComplaintDto.text
				  + "\n\nIf you have any trouble, write to our support : isa.projekat.tester@gmail.com";
		Optional<AppUser> user = userRepository.findById(complaint.appUser.id);
		Optional<AppUser> owner = userRepository.findById(complaint.cottage.cottageOwner.id);
		AppUser appUser;
		AppUser appOwner;
		if(user.isPresent() && owner.isPresent()) {
			appUser = user.get();
			appOwner = owner.get();
			try 
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmail(appUser.email,bodyUser,titleUser);	
						sendEmail(appOwner.email,bodyOwner,titleOwner);
					}
				};
				t.start();
				complaintRepository.delete(complaint);
			return new ResponseEntity<List<Complaint>>(complaintRepository.findAll(),HttpStatus.OK);
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
