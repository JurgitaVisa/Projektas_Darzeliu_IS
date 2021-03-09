package it.akademija.kindergarten;

public class KindergartenStatistics {
	
	private String id;
	
	private String name;
	
	private int availablePlaces;	
	
	private long selectedAsChoise1;
	
	private long selectedAsChoise2;
	
	private long selectedAsChoise3;
	
	private long selectedAsChoise4;
	
	private long selectedAsChoise5;
	
	private int takenPlaces;

	public KindergartenStatistics() {
		
	}

	public KindergartenStatistics(String id, String name, int availablePlaces, long selectedAsChoise1,
			long selectedAsChoise2, long selectedAsChoise3, long selectedAsChoise4, long selectedAsChoise5,
			int takenPlaces) {
		super();
		this.id = id;
		this.name = name;
		this.availablePlaces = availablePlaces;
		this.selectedAsChoise1 = selectedAsChoise1;
		this.selectedAsChoise2 = selectedAsChoise2;
		this.selectedAsChoise3 = selectedAsChoise3;
		this.selectedAsChoise4 = selectedAsChoise4;
		this.selectedAsChoise5 = selectedAsChoise5;
		this.takenPlaces = takenPlaces;
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

	public long getSelectedAsChoise1() {
		return selectedAsChoise1;
	}

	public void setSelectedAsChoise1(long selectedAsChoise1) {
		this.selectedAsChoise1 = selectedAsChoise1;
	}

	public long getSelectedAsChoise2() {
		return selectedAsChoise2;
	}

	public void setSelectedAsChoise2(long selectedAsChoise2) {
		this.selectedAsChoise2 = selectedAsChoise2;
	}

	public long getSelectedAsChoise3() {
		return selectedAsChoise3;
	}

	public void setSelectedAsChoise3(long selectedAsChoise3) {
		this.selectedAsChoise3 = selectedAsChoise3;
	}

	public long getSelectedAsChoise4() {
		return selectedAsChoise4;
	}

	public void setSelectedAsChoise4(long selectedAsChoise4) {
		this.selectedAsChoise4 = selectedAsChoise4;
	}

	public long getSelectedAsChoise5() {
		return selectedAsChoise5;
	}

	public void setSelectedAsChoise5(long selectedAsChoise5) {
		this.selectedAsChoise5 = selectedAsChoise5;
	}

	public int getTakenPlaces() {
		return takenPlaces;
	}

	public void setTakenPlaces(int takenPlaces) {
		this.takenPlaces = takenPlaces;
	}	
	

}
