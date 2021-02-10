/*
 * package it.akademija.application;
 * 
 * import javax.persistence.CascadeType; import
 * javax.persistence.GeneratedValue; import javax.persistence.GenerationType;
 * import javax.persistence.Id; import javax.persistence.JoinColumn; import
 * javax.persistence.OneToOne;
 * 
 * public class Application {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.AUTO) private Long applicationId;
 * 
 * // @ManyToOne(fetch = FetchType.LAZY)
 * 
 * 
 * @JoinColumn(name = "user") private User user;
 * 
 * 
 * @OneToOne(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "child_id", referencedColumnName = "id") private Children
 * children;
 * 
 * }
 */
