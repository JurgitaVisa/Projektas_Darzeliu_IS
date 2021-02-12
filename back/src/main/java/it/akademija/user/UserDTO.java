package it.akademija.user;

public class UserDTO {

	private String role;
	private String name;
	private String surname;
	private String personalCode;
	private String address;
	private String phone;
	private String email;
	private String username;
	private String password;

	public UserDTO() {

	}

	public UserDTO(String role, String name, String surname, String email, String username, String password) {
		super();
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public UserDTO(String role, String name, String surname, String personalCode, String address, String phone,
			String email, String username, String password) {
		super();
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.personalCode = personalCode;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public UserDTO(String name, String surname, String personalCode, String address, String phone, String email,
			String username, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.personalCode = personalCode;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}