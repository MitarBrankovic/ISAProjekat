package com.BookingApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BookingApp.dto.RequestDeleteAccDto;
import com.BookingApp.model.AppUser;
import com.BookingApp.model.RequestDeleteAcc;
import com.BookingApp.repository.AdminRepository;
import com.BookingApp.repository.ComplaintRepository;
import com.BookingApp.repository.RequestDeleteAccRepository;
import com.BookingApp.repository.RoleRepository;
import com.BookingApp.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class AdminService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RequestDeleteAccRepository requestDeleteAccRepository;
	@Autowired
	private ComplaintRepository complaintRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	
	
}
