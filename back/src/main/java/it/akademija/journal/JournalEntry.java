package it.akademija.journal;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "event_journal")
public class JournalEntry {

	@Id
	@Column(name = "entry_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long entryID;
	
	@Column(name= "user_id")
	private Long userID;
	
	@Column(name= "user_name")
	private String userName;
	
	@Column(name= "event_time")
	private LocalDate eventTime;
	
	@Column(name= "operation_type")
	private OperationType operationType;
	
	@Column(name= "object_id")
	private Long objectID;
	
	@Column(name= "object_type")
	private ObjectType objectType;
	
	@Column(name="entry_message")
	private String entryMessage;
	
	public String getEntryMessage() {
		return entryMessage;
	}

	public void setEntryMessage(String entryMessage) {
		this.entryMessage = entryMessage;
	}

	public Long getEntryID() {
		return entryID;
	}
	
	public void setEntryID(Long entryID) {
		this.entryID = entryID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public LocalDate getEventTime() {
		return eventTime;
	}
	public void setEventTime(LocalDate eventTime) {
		this.eventTime = eventTime;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public Long getObjectID() {
		return objectID;
	}
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}
	public ObjectType getObjectType() {
		return objectType;
	}
	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

//	public JournalEntry(@NotEmpty Long entryID, @NotEmpty(message = "user id") Long userID, String userName,
//			@NotEmpty LocalDate eventTime, @NotEmpty OperationType operationType, @NotEmpty Long objectID,
//			@NotEmpty ObjectType objectType, @NotEmpty String entryMessage) {
//		super();
//		this.entryID = entryID;
//		this.userID = userID;
//		this.userName = userName;
//		this.eventTime = eventTime;
//		this.operationType = operationType;
//		this.objectID = objectID;
//		this.objectType = objectType;
//		this.entryMessage = entryMessage;
//	}
	
	public JournalEntry(@NotEmpty(message = "user id") Long userID, String userName,
			@NotEmpty LocalDate eventTime, @NotEmpty OperationType operationType, @NotEmpty Long objectID,
			@NotEmpty ObjectType objectType, @NotEmpty String entryMessage) {
		
		super();
		this.userID = userID;
		this.userName = userName;
		this.eventTime = eventTime;
		this.operationType = operationType;
		this.objectID = objectID;
		this.objectType = objectType;
		this.entryMessage = entryMessage;
	}
}
