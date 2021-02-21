package it.akademija.kindergartenchoise;

public class KindergartenChoiseDTO {

	private String kindergartenId1;
	
	private String kindergartenId2;
	
	private String kindergartenId3;
	
	private String kindergartenId4;
	
	private String kindergartenId5;

	public KindergartenChoiseDTO() {

	}

	public KindergartenChoiseDTO(String kindergartenId1, String kindergartenId2, String kindergartenId3,
			String kindergartenId4, String kindergartenId5) {
		
		this.kindergartenId1 = kindergartenId1;
		this.kindergartenId2 = kindergartenId2;
		this.kindergartenId3 = kindergartenId3;
		this.kindergartenId4 = kindergartenId4;
		this.kindergartenId5 = kindergartenId5;
	}
	
	public String getKindergartenId(int id) {
		String kindergartenId="";
		
		switch (id) {
		case 1:
			kindergartenId= kindergartenId1;
			break;
		case 2:
			kindergartenId= kindergartenId2;
			break;
		case 3:
			kindergartenId= kindergartenId3;
			break;
		case 4:
			kindergartenId= kindergartenId4;
			break;
		case 5:
			kindergartenId= kindergartenId5;
			break;
		}
		return kindergartenId;
		
	}

	public String getKindergartenId1() {
		return kindergartenId1;
	}

	public void setKindergartenId1(String kindergartenId1) {
		this.kindergartenId1 = kindergartenId1;
	}

	public String getKindergartenId2() {
		return kindergartenId2;
	}

	public void setKindergartenId2(String kindergartenId2) {
		this.kindergartenId2 = kindergartenId2;
	}

	public String getKindergartenId3() {
		return kindergartenId3;
	}

	public void setKindergartenId3(String kindergartenId3) {
		this.kindergartenId3 = kindergartenId3;
	}

	public String getKindergartenId4() {
		return kindergartenId4;
	}

	public void setKindergartenId4(String kindergartenId4) {
		this.kindergartenId4 = kindergartenId4;
	}

	public String getKindergartenId5() {
		return kindergartenId5;
	}

	public void setKindergartenId5(String kindergartenId5) {
		this.kindergartenId5 = kindergartenId5;
	}
	
	
	
	
}
