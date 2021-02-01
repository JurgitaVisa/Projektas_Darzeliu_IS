package it.akademija.kindergarten;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;

@Entity
public class Kindergarten {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	@NotBlank(message = "Pavadinimas privalomas")	
	private String name;

	@Column
	@NotBlank(message = "Pavadinimas privalomas")
	private String address;

	public Kindergarten() {

	}

	public Kindergarten(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	
}
