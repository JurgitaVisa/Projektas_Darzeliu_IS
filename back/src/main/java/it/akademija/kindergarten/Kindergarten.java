package it.akademija.kindergarten;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import it.akademija.kindergartenchoise.KindergartenChoise;

@Entity
public class Kindergarten {

	@Id
	@Column(name = "kindergarten_id")
	@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{9}$|", message = "Įstaigos kodas turi būti sudarytas iš 9 skaitmenų")
	private String id;

	@NotBlank(message = "Pavadinimas privalomas")
	@Pattern(regexp = "\\S[\\s\\S]{2,49}")
	@Column(name = "name", unique = true)
	private String name;

	@Column
	@NotBlank(message = "Adresas privalomas")
	private String address;

	@Column
	@Pattern(regexp = "^[\\p{L}\\s]{3,20}$")
	@NotBlank(message = "Seniūnija privaloma")
	private String elderate;

	@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0")
	private int capacityAgeGroup2to3;

	@Min(value = 0, message = "Laisvų vietų skaičius negali būti mažesnis už 0")
	private int capacityAgeGroup3to6;

	@OneToMany(mappedBy = "kindergarten", cascade = CascadeType.ALL)
	private Set<KindergartenChoise> kindergartenChoises;

	public Kindergarten() {

	}

	public Kindergarten(String id, String name, String address, String elderate, int capacityAgeGroup2to3,
			int capacityAgeGroup3to6) {
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

	public Set<KindergartenChoise> getKindergartenChoises() {
		return kindergartenChoises;
	}

	public void setKindergartenChoises(Set<KindergartenChoise> kindergartenChoises) {
		this.kindergartenChoises = kindergartenChoises;
	}

}
