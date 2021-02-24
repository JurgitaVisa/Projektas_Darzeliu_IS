package it.akademija.kindergarten;

public class KindergartenStatistics {
	
	private String id;
	
	private String name;
	
	private int availablePlaces;
	
	private long numberOfApplications;

	public KindergartenStatistics() {
		
	}

	public KindergartenStatistics(String id, String name, int availablePlaces, long numberOfApplications) {
		super();
		this.id = id;
		this.name = name;
		this.availablePlaces = availablePlaces;
		this.numberOfApplications = numberOfApplications;
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

	public int getAvailablePlaces() {
		return availablePlaces;
	}

	public void setAvailablePlaces(int availablePlaces) {
		this.availablePlaces = availablePlaces;
	}

	public long getNumberOfApplications() {
		return numberOfApplications;
	}

	public void setNumberOfApplications(long numberOfApplications) {
		this.numberOfApplications = numberOfApplications;
	}
	
	
	
	

}
