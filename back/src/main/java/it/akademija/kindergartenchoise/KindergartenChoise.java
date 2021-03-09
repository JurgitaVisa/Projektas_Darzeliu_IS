package it.akademija.kindergartenchoise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import it.akademija.application.Application;
import it.akademija.kindergarten.Kindergarten;

@Entity
public class KindergartenChoise {

	@Id
	@Column(name="choise_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long choiseId;
	
	@ManyToOne
	@JoinColumn(name = "kindergarten_id")
	private Kindergarten kindergarten;

	@ManyToOne
	@JoinColumn(name = "application_id")
	private Application application;
	
	@Min(value = 1)
	@Max(value = 5)
	private int kindergartenChoisePriority;

	public KindergartenChoise() {

	}

	public KindergartenChoise(Kindergarten kindergarten, Application application,
			@Min(1) @Max(5) int kindergartenChoisePriority) {
		
		this.kindergarten = kindergarten;
		this.application = application;
		this.kindergartenChoisePriority = kindergartenChoisePriority;
	}

	public Kindergarten getKindergarten() {
		return kindergarten;
	}

	public void setKindergarten(Kindergarten kindergarten) {
		this.kindergarten = kindergarten;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public int getKindergartenChoisePriority() {
		return kindergartenChoisePriority;
	}

	public void setKindergartenChoisePriority(int kindergartenChoisePriority) {
		this.kindergartenChoisePriority = kindergartenChoisePriority;
	}

	public Long getId() {
		return choiseId;
	}

}
