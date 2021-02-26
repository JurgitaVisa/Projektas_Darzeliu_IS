package it.akademija.user.passwordresetrequests;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userpasswordresetrequests")
public class UserPasswordResetRequestsEntity {
	@Id
	@Column
	private Long userId;
	
	public UserPasswordResetRequestsEntity() {
	} 
	
	public UserPasswordResetRequestsEntity(Long userId) {
		super();
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
