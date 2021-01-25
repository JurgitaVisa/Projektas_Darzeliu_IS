package it.akademija.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import it.akademija.role.Role;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long UserId;	
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;
	
//	private String city;
//	
//	private String address;
//	
//	private String email;
//	
//	private String personId;
//	
//	private String phoneNumber;
//	
	@Column(unique = true)
	private String username;
	
	private String password;	
	
	@Enumerated(EnumType.STRING)
	private Role role;

	public User() {
	}

	public User(String username, String name, String surname, String password, Role role) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	

	

}
