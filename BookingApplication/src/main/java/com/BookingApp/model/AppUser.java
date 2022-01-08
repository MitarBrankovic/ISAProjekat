package com.BookingApp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser implements UserDetails{

	@Id
	@SequenceGenerator(name = "myUserSeqGen", sequenceName = "myUserSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myUserSeqGen")
	@Column(name="id", unique=true, nullable=false)
	public long id;
	@Column
	public String name;
	@Column
	public String surname;
	@Column(nullable = false, unique = true)
	public String email;
	@Column
	public String password;
	@Column
	public String address;
	@Column
	public String city;
	@Column
	public String country;
	@Column
	public String phoneNumber;
	@Enumerated(value = EnumType.STRING)
	@Column
	public UserType role;
	@Column
	public String verificationCode;
	@Column
	public Boolean verified;
	@OneToOne
    private Role roles;
	
	public AppUser() {
		super();
	}

	
	public AppUser(long id, String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.verificationCode = verificationCode;
		this.verified = firstLogin;
	}
	
	public AppUser(long id, String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin, Role roles) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.verificationCode = verificationCode;
		this.verified = firstLogin;
		this.roles = roles;
	}
	
	public AppUser(String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.verificationCode = verificationCode;
		this.verified = firstLogin;
	}
	
    public void setRoles(Role roles) {
        this.roles = roles;
    }
    
    public Role getRoles() {
       return roles;
    }

    @JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<Role>(List.of(roles));
	}


	@Override
	public String getPassword() {
		return password;
	}


	@Override
	public String getUsername() {
		return email;
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
