package com.BookingApp.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.dto.PricelistItemDto;
import com.BookingApp.dto.PricelistItemRemoveDto;
import com.BookingApp.model.PricelistItem;

import com.BookingApp.repository.PricelistRepository;
import com.BookingApp.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/pricelist")
public class PricelistController {

	@Autowired
	private PricelistRepository pricelistRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping(path = "/getInstructorsPricelist/{instructorsId}")
	public Set<PricelistItem> getInstructorsPricelist(@PathVariable("instructorsId") long id)
	{
		return pricelistRepository.findAllItemsByInstructorsId(id);
	}
	
	
	@PostMapping(path = "/addPricelistItem")
    public Set<PricelistItem> addPricelistItem(@RequestBody PricelistItemDto itemDTO)
	{	
		if(itemDTO != null) {
			PricelistItem  newItem = new PricelistItem(itemDTO.price, itemDTO.description, userRepository.findById(itemDTO.instructorsId).get());
			pricelistRepository.save(newItem);
			return pricelistRepository.findAllItemsByInstructorsId(itemDTO.instructorsId);
		}
		return null;
	}
	
	
	@PostMapping(path = "/deletePricelistItem")
    public Set<PricelistItem> deleteItem(@RequestBody PricelistItemRemoveDto itemDTO)
	{	
		System.out.println(pricelistRepository.findAll().size());
		pricelistRepository.deleteById(itemDTO.itemId);
		System.out.println(pricelistRepository.findAll().size());
		return pricelistRepository.findAllItemsByInstructorsId(itemDTO.instructorId);
	}
}
