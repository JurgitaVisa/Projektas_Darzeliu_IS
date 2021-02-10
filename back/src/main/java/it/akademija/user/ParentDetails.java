package it.akademija.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "parentDetails")
public class ParentDetails {

	@Id
	@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{11}$|")
	@Column(name = "id")
	private String personalCode;

	@OneToOne(mappedBy = "parentDetails")
	private User user;

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

	@Column
	private String address;

	@Pattern(regexp = "^370(?!\\s*$)[0-9\\s]{8}$|")
	@Column
	private String phone;

	public ParentDetails() {

	}

	public ParentDetails(@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{11}$|") String personalCode, User user,
			@NotEmpty(message = "Vardas privalomas!") @Size(min = 2, max = 70) @Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$") String name,
			@NotEmpty(message = "Pavardė privaloma!") @Size(min = 2, max = 70) @Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$") String surname,
			@Email @NotEmpty(message = "El. paštas privalomas!") String email, String address,
			@Pattern(regexp = "^370(?!\\s*$)[0-9\\s]{8}$|") String phone) {
		super();
		this.personalCode = personalCode;
		this.user = user;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}

	public String getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(String personalCode) {
		this.personalCode = personalCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

}