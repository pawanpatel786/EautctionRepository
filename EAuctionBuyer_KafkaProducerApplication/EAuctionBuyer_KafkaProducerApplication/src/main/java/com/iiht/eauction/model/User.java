package com.iiht.eauction.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
  @Id
  private String id;

//  @NotBlank
//  @Size(max = 20)
//  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @DBRef
  private Set<Role> roles = new HashSet<>();
  
  private String firstName;
	private String lastName;

	private String address;
	private String city;
	private String state;
	private long pin;
	private long phone;

  public User() {
  }


  public User(@Email String email,
		 String password, String firstName, String lastName, String address,
		String city, String state, long pin, long phone) {
//	this.username = username;
	this.email = email;
	this.password = password;
	this.roles = roles;
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.city = city;
	this.state = state;
	this.pin = pin;
	this.phone = phone;
}


public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public long getPin() {
	return pin;
}

public void setPin(long pin) {
	this.pin = pin;
}

public long getPhone() {
	return phone;
}

public void setPhone(long phone) {
	this.phone = phone;
}

public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

//  public String getUsername() {
//    return username;
//  }
//
//  public void setUsername(String username) {
//    this.username = username;
//  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
