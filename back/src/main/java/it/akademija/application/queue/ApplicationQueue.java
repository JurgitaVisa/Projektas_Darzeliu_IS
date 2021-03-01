package it.akademija.application.queue;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import it.akademija.application.Application;
import it.akademija.kindergarten.Kindergarten;


@Entity
public class ApplicationQueue {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	
	private String childPersonalCode;
	
	private String childName;
	
	private String childSurname;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kindergarten_id")
	private Kindergarten kindergarten;
	
	private int numberInWaitingList;		
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH }, fetch = FetchType.LAZY)	
	private Application application;

	public ApplicationQueue() {
		
	}

	public ApplicationQueue( String childPersonalCode, String childName, String childSurname) {
		super();		
		this.childPersonalCode = childPersonalCode;
		this.childName = childName;
		this.childSurname = childSurname;	
		
	}

	public Long getId() {
		return id;
	}	

	public String getChildPersonalCode() {
		return childPersonalCode;
	}

	public void setChildPersonalCode(String childPersonalCode) {
		this.childPersonalCode = childPersonalCode;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String chilName) {
		this.childName = chilName;
	}

	public String getChildSurname() {
		return childSurname;
	}

	public void setChildSurname(String childSurname) {
		this.childSurname = childSurname;
	}

	public Kindergarten getKindergarten() {
		return kindergarten;
	}

	public void setKindergarten(Kindergarten kindergarten) {
		this.kindergarten = kindergarten;
	}

	public int getNumberInWaitingList() {
		return numberInWaitingList;
	}

	public void setNumberInWaitingList(int numberInWaitingList) {
		this.numberInWaitingList = numberInWaitingList;
	}	

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		application.setApplicationQueue(this);
		this.application = application;
	}
	
	public void detachApplication() {
		application.setApplicationQueue(null);
		this.application=null;
	}

	

}
