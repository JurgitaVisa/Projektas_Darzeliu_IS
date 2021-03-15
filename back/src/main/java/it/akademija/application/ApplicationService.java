package it.akademija.application;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.application.priorities.Priorities;
import it.akademija.application.priorities.PrioritiesDAO;
import it.akademija.application.priorities.PrioritiesDTO;
import it.akademija.journal.JournalService;
import it.akademija.journal.ObjectType;
import it.akademija.journal.OperationType;
import it.akademija.kindergarten.Kindergarten;
import it.akademija.kindergarten.KindergartenService;
import it.akademija.kindergartenchoise.KindergartenChoise;
import it.akademija.kindergartenchoise.KindergartenChoiseDAO;
import it.akademija.kindergartenchoise.KindergartenChoiseDTO;
import it.akademija.user.ParentDetails;
import it.akademija.user.ParentDetailsDAO;
import it.akademija.user.ParentDetailsDTO;
import it.akademija.user.User;
import it.akademija.user.UserService;

@Service
public class ApplicationService {

	@Autowired
	private ApplicationDAO applicationDao;

	@Autowired
	private UserService userService;

	@Autowired
	private KindergartenService gartenService;

	@Autowired
	private ParentDetailsDAO parentDetailsDao;

	@Autowired
	private PrioritiesDAO prioritiesDao;

	@Autowired
	private KindergartenChoiseDAO choiseDao;

	@Autowired
	private JournalService journalService;

	/**
	 * 
	 * Get information about submitted applications for logged in user
	 * 
	 * @param currentUsername
	 * @return list of user applications
	 */
	@Transactional(readOnly = true)
	public Set<ApplicationInfoUser> getAllUserApplications(String currentUsername) {

		return applicationDao.findAllUserApplications(currentUsername);
	}

	/**
	 * Create an application for logged in user's child with specified child data.
	 * Receives and updates user data, receives saves additional child's guardian if
	 * person with such personal code is not already in the database. Sets received
	 * main guardian, additional guardian, priorities and chosen kindergartens to
	 * application.
	 * 
	 * @param currentUsername
	 * @param data            child data, gurdians' data, priorities data,
	 *                        kindergarten choises
	 * @return application or null if no kindergarten are chosen
	 */
	@Transactional
	public Application createNewApplication(String currentUsername, ApplicationDTO data) {

		Application application = new Application();

		User firstParent = userService.updateUserData(data.getMainGuardian(), currentUsername);

		ParentDetailsDTO detailsDto = data.getAdditionalGuardian();

		if (detailsDto.getPersonalCode() != null && detailsDto.getPersonalCode() != "") {
			ParentDetails secondParent = parentDetailsDao.findByPersonalCode(detailsDto.getPersonalCode());

			if (secondParent == null) {
				secondParent = parentDetailsDao.save(
						new ParentDetails(detailsDto.getPersonalCode(), detailsDto.getName(), detailsDto.getSurname(),
								detailsDto.getEmail(), detailsDto.getAddress(), detailsDto.getPhone()));
			}

			application.setAdditionalGuardian(secondParent);
		}

		PrioritiesDTO prioritiesDto = data.getPriorities();

		Priorities priorities = prioritiesDao.save(new Priorities(prioritiesDto.isLivesInVilnius(),
				prioritiesDto.isChildIsAdopted(), prioritiesDto.isFamilyHasThreeOrMoreChildrenInSchools(),
				prioritiesDto.isGuardianInSchool(), prioritiesDto.isGuardianDisability()));

		application.setPriorities(priorities);
		application.setPriorityScore(priorities.getScore());

		application.setSubmitedAt();
		application.setStatus(ApplicationStatus.Pateiktas);
		application.setChildName(capitalize(data.getChildName()));
		application.setChildSurname(capitalize(data.getChildSurname()));
		application.setChildPersonalCode(data.getChildPersonalCode());
		application.setBirthdate(data.getBirthdate());

		application.setMainGuardian(firstParent);

		KindergartenChoiseDTO choisesDto = data.getKindergartenChoises();
		Set<KindergartenChoise> choises = new HashSet<>();

		for (int i = 1; i <= 5; i++) {
			if (null != choisesDto.getKindergartenId(i)) {
				Kindergarten garten = gartenService.findById(choisesDto.getKindergartenId(i));
				if (garten != null) {
					KindergartenChoise choise = choiseDao.save(new KindergartenChoise(garten, application, i));
					choises.add(choise);
				}
			}
		}

		if (choises.isEmpty()) {

			return null;

		}

		application.setKindergartenChoises(choises);
		application = applicationDao.saveAndFlush(application);

		return application;

	}

	/**
	 * 
	 * capitalize first letter of word, other letters to lowercase
	 * 
	 * @param str
	 * @return
	 */
	private String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}

		return Pattern.compile("\\b(.)(.*?)\\b").matcher(str)
				.replaceAll(match -> match.group(1).toUpperCase() + match.group(2).toLowerCase());
	}

	/**
	 * Delete user application by id. Also deletes connected priorities,
	 * kindergarten choises, and additional guardian who has no other applications
	 * connected to them. Also decreases number of taken places in approved
	 * Kindergarten if applicable. Accessible to User only
	 * 
	 * @param id
	 * @return message indicating whether deletion was successful
	 */
	@Transactional
	public ResponseEntity<String> deleteApplication(Long id) {

		Application application = applicationDao.getOne(id);

		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (application != null && application.getMainGuardian().equals(user)) {

			detachAdditionalGuardian(application);

			updateAvailablePlacesInKindergarten(application);

			applicationDao.delete(application);

			journalService.newJournalEntry(OperationType.APPLICATION_DELETED, id, ObjectType.APPLICATION,
					"Ištrintas prašymas");

			return new ResponseEntity<String>("Ištrinta sėkmingai", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Prašymas nerastas", HttpStatus.NOT_FOUND);
	}

	/**
	 * Removes additional guardian who has no other applications connected to them.
	 * 
	 * @param id
	 * @param application
	 */
	public void detachAdditionalGuardian(Application application) {
		ParentDetails additionalGuardian = application.getAdditionalGuardian();

		if (additionalGuardian != null) {
			int numberOfAdditionalGuardianApplications = additionalGuardian.removeApplication(application);

			if (numberOfAdditionalGuardianApplications == 0) {
				parentDetailsDao.delete(additionalGuardian);
			}

			application.setAdditionalGuardian(null);

		}
	}

	/**
	 * Updates number of available places in approved Kindergarten
	 * 
	 * @param application
	 */
	public void updateAvailablePlacesInKindergarten(Application application) {
		long age = application.calculateAgeInYears();

		if (age < 7) {

			ApplicationStatus status = application.getStatus();

			if (status.equals(ApplicationStatus.Pateiktas)) {

				Kindergarten garten = application.getApprovedKindergarten();

				if (garten != null) {

					gartenService.decreaseNumberOfTakenPlacesInAgeGroup(garten, age);
				}

			} else if (status.equals(ApplicationStatus.Patvirtintas)) {

				Kindergarten garten = application.getApprovedKindergarten();

				gartenService.increaseNumberOfAvailablePlacesInAgeGroup(garten, age);

			}
		}

	}

	/**
	 * Deactivate application by id. Also decreases number of taken places in
	 * approved Kindergarten if applicable. Accessible to Manager only
	 * 
	 * @param id
	 */
	@Transactional
	public ResponseEntity<String> deactivateApplication(Long id) {

		Application application = applicationDao.getOne(id);

		if (application == null) {

			return new ResponseEntity<String>("Prašymas nerastas", HttpStatus.NOT_FOUND);

		} else if (application.getStatus().equals(ApplicationStatus.Patvirtintas)) {

			return new ResponseEntity<String>("Veiksmas negalimas. Prašymas jau patvirtintas.",
					HttpStatus.METHOD_NOT_ALLOWED);

		} else {

			application.setStatus(ApplicationStatus.Neaktualus);

			if (application.getApprovedKindergarten() != null) {

				gartenService.decreaseNumberOfTakenPlacesInAgeGroup(application.getApprovedKindergarten(),
						application.calculateAgeInYears());
				application.setApprovedKindergarten(null);
			}

			application.setNumberInWaitingList(0);

			applicationDao.save(application);

			return new ResponseEntity<String>("Statusas pakeistas sėkmingai", HttpStatus.OK);
		}

	}

	/**
	 * 
	 * Check if application for a child already exists
	 * 
	 * @param childPersonalCode
	 * @return true if exists
	 */
	public boolean existsByPersonalCode(String childPersonalCode) {
		return applicationDao.existsApplicationByChildPersonalCode(childPersonalCode);
	}

	/**
	 * Returns a page of information from submitted Applications list with specified
	 * page number and page size.
	 * 
	 * @param pageable
	 * @return page from Application database
	 */
	public Page<ApplicationInfo> getPageFromSubmittedApplications(Pageable pageable) {

		return applicationDao.findAllApplications(pageable);

	}

	/**
	 * Returns a filtered page of information from submitted Applications list,
	 * containing applications that start with specified child personal code.
	 * 
	 * @param childPersonalCode
	 * @param pageable
	 * @return filtered page from Application database
	 */
	public Page<ApplicationInfo> getApplicationnPageFilteredById(String childPersonalCode, Pageable pageable) {

		return applicationDao.findByIdContaining(childPersonalCode, pageable);
	}

	public ApplicationDAO getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDAO applicationDao) {
		this.applicationDao = applicationDao;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public KindergartenService getGartenService() {
		return gartenService;
	}

	public void setGartenService(KindergartenService gartenService) {
		this.gartenService = gartenService;
	}

	public ParentDetailsDAO getParentDetailsDao() {
		return parentDetailsDao;
	}

	public void setParentDetailsDao(ParentDetailsDAO parentDetailsDao) {
		this.parentDetailsDao = parentDetailsDao;
	}

	public PrioritiesDAO getPrioritiesDao() {
		return prioritiesDao;
	}

	public void setPrioritiesDao(PrioritiesDAO prioritiesDao) {
		this.prioritiesDao = prioritiesDao;
	}

	public KindergartenChoiseDAO getChoiseDao() {
		return choiseDao;
	}

	public void setChoiseDao(KindergartenChoiseDAO choiseDao) {
		this.choiseDao = choiseDao;
	}

}
