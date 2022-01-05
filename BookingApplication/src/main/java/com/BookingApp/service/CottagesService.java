package com.BookingApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.SearchDto;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.FishingAppointment;
import com.BookingApp.repository.CottageRepository;


@RestController
@RequestMapping("/cottages")
public class CottagesService {

	@Autowired
	private CottageRepository cottageRepository;
	
	@GetMapping(path = "/getAllCottages")
	public ResponseEntity<List<Cottage>> getAllCottage()
	{
		List<Cottage> cottages = new ArrayList<Cottage>();
		for(Cottage cottage : cottageRepository.findAll())
		{
			cottages.add(cottage);
		}
		return new ResponseEntity<List<Cottage>>(cottages,HttpStatus.OK);
	}
	@GetMapping(path = "/getAllCottagesByOwner/{ownerId}")
	public ResponseEntity<List<Cottage>> getAllCottagesByOwner(@PathVariable("ownerId") long id)
	{	
		List<Cottage> cottages = cottageRepository.getAllCottagesForOwner(id);
		
		return new ResponseEntity<List<Cottage>>(cottages,HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/searchCottages")
	public ResponseEntity<List<Cottage>> searchCottages(@RequestBody SearchDto dto)
	{
		String name = dto.name;
		String address = dto.address;
		boolean nameAsc = dto.nameAsc;
		boolean nameDesc = dto.nameDesc;
		boolean addressAsc = dto.addressAsc;
		boolean addressDesc = dto.addressDesc;
		

		List<Cottage> cottages = cottageRepository.findAll();

		if (name.equals("") && address.equals(""))
			cottages = cottageRepository.findAll();
		
		if (!name.equals("")) {
			cottages =  cottages.stream().filter(m -> m.name.toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList()); }
		
		if (!address.equals("")) {
			cottages =  cottages.stream().filter(m -> m.address.toLowerCase().contains(address.toLowerCase()))
					.collect(Collectors.toList()); }
			
		if (nameAsc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).name
							.compareTo(cottages.get(j).name) > 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		if (nameDesc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).name
							.compareTo(cottages.get(j).name) < 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressAsc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).address
							.compareTo(cottages.get(j).address) > 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		if (addressDesc) {
			int n = cottages.size();
			Cottage temp = null;
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {
					if (cottages.get(j - 1).address
							.compareTo(cottages.get(j).address) < 0) {
						// swap elements
						temp = cottages.get(j - 1);
						cottages.set(j - 1, cottages.get(j));
						cottages.set(j, temp);
					}

				}
			}
		}
		
		
		return new ResponseEntity<List<Cottage>>(cottages,HttpStatus.OK);
	}
	@GetMapping(path = "/getSelectedCottage/{cottageId}")
	public ResponseEntity<Cottage> getSelectedCottage(@PathVariable("cottageId") long id)
	{
		Optional<Cottage> cottage = cottageRepository.findById(id);
		Cottage cottageNew = cottage.get();
		
		return new ResponseEntity<Cottage>(cottageNew,HttpStatus.OK);
	}
}
