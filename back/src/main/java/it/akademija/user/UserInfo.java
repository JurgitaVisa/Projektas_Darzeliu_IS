package it.akademija.user;

import java.time.LocalDate;

public class UserInfo {

	private String role;
	private String name;
	private String surname;
	private LocalDate birthdate;
	private String personalCode;
	private String address;
	private String phone;
	private String email;
	private String username;

	public UserInfo() {

	}

	public UserInfo(String role, String name, String surname, LocalDate birthdate, String personalCode, String address,
			String phone, String email, String username) {
		super();
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.address = address;
		this.personalCode = personalCode;
		this.phone = phone;
		this.email = email;
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(String personalCode) {
		this.personalCode = personalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}