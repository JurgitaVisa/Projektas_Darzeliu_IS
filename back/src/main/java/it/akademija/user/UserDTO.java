package it.akademija.user;

public class UserDTO {

	private String username;
	private String name;
	private String surname;	
	private String password;
	private String role;	
	
	public UserDTO() {
		
	}	

	public UserDTO(String username, String name, String surname, String password, String role) {
		super();
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	
}
