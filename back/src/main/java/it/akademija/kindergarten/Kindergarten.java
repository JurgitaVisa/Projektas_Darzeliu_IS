package it.akademija.kindergarten;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Kindergarten {

	@Id
	@Size(min = 7, max = 9, message = "Įstaigos kodas turi būti sudarytas iš 7-9 skaitmenų imtinai")
	private String id;

	@NotBlank(message = "Pavadinimas privalomas")
	@Column(name = "name", unique = true)
	private String name;

	@Column
	@NotBlank(message = "Adresas privalomas")
	private String address;

	@Column
	@NotBlank(message = "Seniūnija privaloma")
	private String elderate;

	@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0")
	private int capacityAgeGroup2to3;

	@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0")
	private int capacityAgeGroup3to6;

	public Kindergarten() {

	}

	public Kindergarten(String id, @NotBlank(message = "Pavadinimas privalomas") String name,
			@NotBlank(message = "Adresas privalomas") String address,
			@NotBlank(message = "Seniūnija privaloma") String elderate,
			@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0") int capacityAgeGroup2to3,
			@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0") int capacityAgeGroup3to6) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.elderate = elderate;
		this.capacityAgeGroup2to3 = capacityAgeGroup2to3;
		this.capacityAgeGroup3to6 = capacityAgeGroup3to6;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getElderate() {
		return elderate;
	}

	public void setElderate(String elderate) {
		this.elderate = elderate;
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

}
