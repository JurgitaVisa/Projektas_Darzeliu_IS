package it.akademija.application.priorities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import it.akademija.application.Application;

@Entity
public class Priorities {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long priorityId;

	@Column
	private boolean livesInVilnius;

	@Column
	private boolean childIsAdopted;

	@Column
	private boolean familyHasThreeOrMoreChildrenInSchools;

	@Column
	private boolean guardianInSchool;

	@Column
	private boolean guardianDisability;

	@OneToOne(mappedBy = "priorities")

	private Application application;

	public Priorities() {

	}

	public Priorities(boolean livesInVilnius, boolean childIsAdopted, boolean familyHasThreeOrMoreChildrenInSchools,
			boolean guardianInSchool, boolean guardianDisability) {

		this.livesInVilnius = livesInVilnius;
		this.childIsAdopted = childIsAdopted;
		this.familyHasThreeOrMoreChildrenInSchools = familyHasThreeOrMoreChildrenInSchools;
		this.guardianInSchool = guardianInSchool;
		this.guardianDisability = guardianDisability;
	}

	public Integer getScore() {
		Integer score = 0;

		if (this.livesInVilnius) {
			score += 10;
		}

		if (this.childIsAdopted) {
			score += 1;
		}

		if (this.familyHasThreeOrMoreChildrenInSchools) {
			score += 1;
		}

		if (this.guardianInSchool) {
			score += 1;
		}

		if (this.guardianDisability) {
			score += 1;
		}
		return score;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Long getPriorityId() {
		return priorityId;
	}

	public boolean isLivesInVilnius() {
		return livesInVilnius;
	}

	public void setLivesInVilnius(boolean livesInVilnius) {
		this.livesInVilnius = livesInVilnius;
	}

	public boolean isChildIsAdopted() {
		return childIsAdopted;
	}

	public void setChildIsAdopted(boolean childIsAdopted) {
		this.childIsAdopted = childIsAdopted;
	}

	public boolean isFamilyHasThreeOrMoreChildrenInSchools() {
		return familyHasThreeOrMoreChildrenInSchools;
	}

	public void setFamilyHasThreeOrMoreChildrenInSchools(boolean familyHasThreeOrMoreChildrenInSchools) {
		this.familyHasThreeOrMoreChildrenInSchools = familyHasThreeOrMoreChildrenInSchools;
	}

	public boolean isGuardianInSchool() {
		return guardianInSchool;
	}

	public void setGuardianInSchool(boolean guardianInSchool) {
		this.guardianInSchool = guardianInSchool;
	}

	public boolean isGuardianDisability() {
		return guardianDisability;
	}

	public void setGuardianDisability(boolean guardianDisability) {
		this.guardianDisability = guardianDisability;
	}

}