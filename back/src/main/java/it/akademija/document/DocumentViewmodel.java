package it.akademija.document;

import java.time.LocalDate;

public class DocumentViewmodel {
	
	private long documentId;
	private String name;
	private LocalDate uploadDate;
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}
	public DocumentViewmodel(long documentId, String name, LocalDate uploadDate) {
		super();
		this.documentId = documentId;
		this.name = name;
		this.uploadDate = uploadDate;
	}
	public DocumentViewmodel() {
		super();
	}
	
}
