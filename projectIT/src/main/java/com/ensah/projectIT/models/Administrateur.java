package com.ensah.projectIT.models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Administrateur extends Personnel implements UserDetails{

private String password;

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}


//@OneToMany(mappedBy = "administrateur", cascade = { CascadeType.PERSIST, CascadeType.MERGE, })
@OneToMany(mappedBy = "administrateur",cascade = { CascadeType.PERSIST, CascadeType.MERGE })
private List<Surveillance> ListeSurveillance = new ArrayList<>();


public List<Surveillance> getListeSurveillance() {
	return ListeSurveillance;
}

public void setListeSurveillance(List<Surveillance> listeSurveillance) {
	ListeSurveillance = listeSurveillance;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
}

@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return this.getEmail();
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