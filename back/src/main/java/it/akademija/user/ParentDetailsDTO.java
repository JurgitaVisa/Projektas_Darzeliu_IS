package it.akademija.user;

public class ParentDetailsDTO {
	
	private String personalCode;	

	private String name;

	private String surname;

	private String email;

	private String address;

	private String phone;

	public ParentDetailsDTO() {
		
	}

	public ParentDetailsDTO(String personalCode, String name, String surname, String email, String address,
			String phone) {
		super();
		this.personalCode = personalCode;
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
