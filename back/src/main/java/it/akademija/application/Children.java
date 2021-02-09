/*
 * package it.akademija.application;
 * 
 * import java.time.LocalDate;
 * 
 * import javax.persistence.Column; import javax.persistence.Entity; import
 * javax.persistence.GeneratedValue; import javax.persistence.GenerationType;
 * import javax.persistence.Id; import javax.persistence.OneToOne; import
 * javax.persistence.Table; import javax.validation.constraints.NotEmpty; import
 * javax.validation.constraints.Pattern; import
 * javax.validation.constraints.Size;
 * 
 * import org.springframework.format.annotation.DateTimeFormat;
 * 
 * @Entity
 * 
 * @Table(name = "children") public class Children {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.AUTO) private Long childId;
 * 
 * @NotEmpty(message = "Vardas privalomas!")
 * 
 * @Size(min = 2, max = 70)
 * 
 * @Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$")
 * 
 * @Column private String name;
 * 
 * @NotEmpty(message = "Pavardė privaloma!")
 * 
 * @Size(min = 2, max = 70)
 * 
 * @Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$")
 * 
 * @Column private String surname;
 * 
 * @Pattern(regexp = "^(?!\\s*$)[0-9\\s]{11}$|")
 * 
 * @Column private String personalCode;
 * 
 * @DateTimeFormat(pattern = "yyyy-MM-dd")
 * 
 * @Column private LocalDate birthdate;
 * 
 * @OneToOne(mappedBy = "children") private Application application;
 * 
 * public Long getChildId() { return childId; }
 * 
 * public void setChildId(Long childId) { this.childId = childId; }
 * 
 * public String getName() { return name; }
 * 
 * public void setName(String name) { this.name = name; }
 * 
 * public String getSurname() { return surname; }
 * 
 * public void setSurname(String surname) { this.surname = surname; }
 * 
 * public String getPersonalCode() { return personalCode; }
 * 
 * public void setPersonalCode(String personalCode) { this.personalCode =
 * personalCode; }
 * 
 * public LocalDate getBirthdate() { return birthdate; }
 * 
 * public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }
 * 
 * }
 */