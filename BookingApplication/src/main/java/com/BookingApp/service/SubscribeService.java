package com.BookingApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.SubscribeBoat;
import com.BookingApp.model.SubscribeCottage;
import com.BookingApp.repository.SubscribeAdvRepository;
import com.BookingApp.repository.SubscribeBoatRepository;
import com.BookingApp.repository.SubscribeCottageRepository;

@RestController
@RequestMapping("/subscribe")
public class SubscribeService {
	
	@Autowired
	private SubscribeCottageRepository subscribeCottageRepository;
	@Autowired
	private SubscribeBoatRepository subscribeBoatRepository;
	@Autowired
	private SubscribeAdvRepository subscribeAdvRepository;
	
	
	@PostMapping(path = "/subscribeCottage")
	public boolean getSelectedCottage(@RequestBody SubscribeCottage sub)
	{
		SubscribeCottage subscribeCottage = new SubscribeCottage(sub.cottage, sub.client);
		SubscribeCottage exist = subscribeCottageRepository.findByCottage(sub.cottage.id);
		
		if(exist != null && exist.client == sub.client)
			return false;
		
		subscribeCottageRepository.save(subscribeCottage);
		return true;
	}
	
	@PostMapping(path = "/unsubscribeCottage")
	public boolean unsubscribeCottage(@RequestBody SubscribeCottage sub)
	{
		SubscribeCottage exist = subscribeCottageRepository.findByCottage(sub.cottage.id);
		
		if(exist != null && exist.client == sub.client)
			subscribeCottageRepository.deleteById(exist.id);
			return true;
	}
	
	@GetMapping(path="/getAllSubscibedCottages")
	public ResponseEntity<List<SubscribeCottage>> getAllSubscibedCottages()
	{	
		return new ResponseEntity<List<SubscribeCottage>>(subscribeCottageRepository.findAll(),HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/subscribeBoat")
	public boolean subscribeBoat(@RequestBody SubscribeBoat sub)
	{
		SubscribeBoat subscribeBoat = new SubscribeBoat(sub.boat, sub.client);
		SubscribeBoat exist = subscribeBoatRepository.findByBoat(sub.boat.id);
		
		if(exist != null && exist.client == sub.client)
			return false;
		
		subscribeBoatRepository.save(subscribeBoat);
		return true;
	}
	
	@PostMapping(path = "/unsubscribeBoat")
	public boolean unsubscribeBoat(@RequestBody SubscribeBoat sub)
	{
		SubscribeBoat exist = subscribeBoatRepository.findByBoat(sub.boat.id);
		
		if(exist != null && exist.client == sub.client)
			subscribeBoatRepository.deleteById(exist.id);
			return true;
	}
	
	@GetMapping(path="/getAllSubscibedBoats")
	public ResponseEntity<List<SubscribeBoat>> getAllSubscibedBoats()
	{	
		return new ResponseEntity<List<SubscribeBoat>>(subscribeBoatRepository.findAll(),HttpStatus.OK);
	}
	
	
	
	
	
}
