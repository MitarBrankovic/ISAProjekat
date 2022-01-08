package com.BookingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

import javax.persistence.*;

// POJO koji implementira Spring Security GrantedAuthority kojim se mogu definisati role u aplikaciji
@Entity
@Table(name="ROLES")
public class Role implements GrantedAuthority, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="name")
    public String name;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }
    


}
