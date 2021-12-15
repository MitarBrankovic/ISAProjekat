package com.BookingApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingApp.model.AppointmentType;
import com.BookingApp.model.Boat;
import com.BookingApp.model.Cottage;
import com.BookingApp.model.CottageAppointment;
import com.BookingApp.repository.CottageAppointmentRepository;
import com.BookingApp.repository.CottageRepository;


@RestController
@RequestMapping("/cottageAppointments")
public class CottageAppointmentService {
	@Autowired
	private CottageAppointmentRepository cottageAppointmentRepository;
	@Autowired
	private CottageRepository cottageRepository;
	
	@GetMapping(path = "/getAllQuickAppointments/{cottageId}")
	public ResponseEntity<List<CottageAppointment>> getAllQuickAppointmentsForCottage(@PathVariable("cottageId") long id)
	{	
		List<CottageAppointment> appointments = new ArrayList<CottageAppointment>();
		for(CottageAppointment ca : cottageAppointmentRepository.findAll())
		{
			if(ca.cottage.id == id && ca.appointmentStart.isAfter(LocalDateTime.now()) && 
					 ca.appointmentType == AppointmentType.quick) {
				appointments.add(ca);
			}
		}
		return new ResponseEntity<List<CottageAppointment>>(appointments,HttpStatus.OK);
	}
}
