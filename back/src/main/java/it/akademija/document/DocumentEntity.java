package it.akademija.document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class DocumentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String type;
	
	@Column
	private String size;
	
	@Lob
	private byte[] data;

	@Column
	private long uploaderId;
	
	public DocumentEntity(Long id, String name, String type, byte[] data, long uploaderId) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.data = data;
		this.uploaderId = uploaderId;
	}
	
	public DocumentEntity(String name, String type, byte[] data, long uploaderId) {
		super();
		this.name = name;
		this.type = type;
		this.data = data;
		this.uploaderId = uploaderId;
	}

	public DocumentEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	
	
}
