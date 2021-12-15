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

import com.BookingApp.model.Boat;
import com.BookingApp.repository.BoatRepository;


@RestController
@RequestMapping("/boats")
public class BoatService {

		@Autowired
		private BoatRepository boatRepository;
		
		@GetMapping(path = "/getAllCottages")
		public ResponseEntity<List<Boat>> getAllBoats()
		{
			List<Boat> boats = new ArrayList<Boat>();
			for(Boat boat : boatRepository.findAll())
			{
				boats.add(boat);
			}
			return new ResponseEntity<List<Boat>>(boats,HttpStatus.OK);
		}
		
		
		@GetMapping(path = "/getSelectedBoat/{boatId}")
		public ResponseEntity<Boat> getSelectedBoat(@PathVariable("boatId") long id)
		{
			Optional<Boat> boat = boatRepository.findById(id);
			Boat boatNew = boat.get();
			
			return new ResponseEntity<Boat>(boatNew,HttpStatus.OK);
		}
	}
