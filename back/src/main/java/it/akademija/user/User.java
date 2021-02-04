package it.akademija.user;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import it.akademija.role.Role;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@Enumerated(EnumType.STRING)
	private Role role;

	@NotEmpty(message = "Vardas privalomas!")
	@Size(min = 2, max = 70)
	@Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$")
	@Column
	private String name;

	@NotEmpty(message = "Pavardė privaloma!")
	@Size(min = 2, max = 70)
	@Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$")
	@Column
	private String surname;

	@Email
	@NotEmpty(message = "El. paštas privalomas!")
	@Column
	private String email;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column
	private LocalDate birthdate;

	@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{11}$|")
	@Column
	private String personalCode;

	@Column
	private String address;

	@Pattern(regexp = "^\\+(?!\\s*$)[0-9\\s]{11}$|")
	@Column
	private String phone;

	@NotEmpty
	@Email
	@Column
	private String username;

	@NotEmpty
	@Column
	private String password;

	public User() {
	}

	public User(Role role, String name, String surname, String email, String username, String password) {
		super();
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
	}

	public User(Role role, String name, String surname, String email, LocalDate birthdate, String personalCode,
			String address, String phone, String username, String password) {
		super();
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.personalCode = personalCode;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}