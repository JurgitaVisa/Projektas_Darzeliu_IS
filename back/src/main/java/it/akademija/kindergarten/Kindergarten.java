package it.akademija.kindergarten;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Kindergarten {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Pavadinimas privalomas")
	@Column(name="name", unique=true)
	private String name;

	@Column
	@NotBlank(message = "Adresas privalomas")
	private String address;

	@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0")
	private int capacityAgeGroup2to3 = 0;

	@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0")
	private int capacityAgeGroup3to6 = 0;

	public Kindergarten() {

	}

	public Kindergarten(@NotBlank(message = "Pavadinimas privalomas") String name,
			@NotBlank(message = "Adresas privalomas") String address) {		
		this.name = name;
		this.address = address;
	}

	public Kindergarten(@NotBlank(message = "Pavadinimas privalomas") String name,
			@NotBlank(message = "Adresas privalomas") String address,
			@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0") int capacityAgeGroup2to3,
			@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0") int capacityAgeGroup3to6) {
		super();
		this.name = name;
		this.address = address;
		this.capacityAgeGroup2to3 = capacityAgeGroup2to3;
		this.capacityAgeGroup3to6 = capacityAgeGroup3to6;
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

	public int getCapacityAgeGroup2to3() {
		return capacityAgeGroup2to3;
	}

	public void setCapacityAgeGroup2to3(int capacityAgeGroup2to3) {
		this.capacityAgeGroup2to3 = capacityAgeGroup2to3;
	}

	public int getCapacityAgeGroup3to6() {
		return capacityAgeGroup3to6;
	}

	public void setCapacityAgeGroup3to6(int capacityAgeGroup3to6) {
		this.capacityAgeGroup3to6 = capacityAgeGroup3to6;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kindergarten other = (Kindergarten) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	

	

}
