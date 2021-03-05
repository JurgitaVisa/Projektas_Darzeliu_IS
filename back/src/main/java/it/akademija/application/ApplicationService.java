package it.akademija.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.application.priorities.Priorities;
import it.akademija.application.priorities.PrioritiesDAO;
import it.akademija.application.priorities.PrioritiesDTO;
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

	/**
	 * 
	 * Get information about submitted applications for logged in user
	 * 
	 * @param currentUsername
	 * @return list of user applications
	 */
	@Transactional(readOnly = true)
	public List<ApplicationInfo> getAllUserApplications(String currentUsername) {

		Set<Application> applications = userService.getUserApplications(currentUsername);

		return applications.stream().map(appl -> new ApplicationInfo(appl.getId(), appl.getChildName(),
				appl.getChildSurname(), appl.getSubmitedAt(), appl.getStatus())).collect(Collectors.toList());
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
	public ResponseEntity<String> createNewApplication(String currentUsername, ApplicationDTO data) {

		Application application = new Application();

		User firstParent = userService.updateUserData(data.getMainGuardian(), currentUsername);

		ParentDetailsDTO detailsDto = data.getAdditionalGuardian();

		if (detailsDto.getPersonalCode() != null && detailsDto.getPersonalCode() != "") {
			ParentDetails secondParent = parentDetailsDao.findByPersonalCode(detailsDto.getPersonalCode());

			if (secondParent == null) {
				secondParent = parentDetailsDao.save(
						new ParentDetails(detailsDto.getPersonalCode(), detailsDto.getName(), detailsDto.getSurname(),
								detailsDto.getEmail(), detailsDto.getAddress(), (detailsDto.getPhone())));
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
			Kindergarten garten = gartenService.findById(choisesDto.getKindergartenId(i));
			if (garten != null) {
				KindergartenChoise choise = choiseDao.save(new KindergartenChoise(garten, application, i));
				choises.add(choise);
			}
		}

		if (choises.isEmpty()) {

			return new ResponseEntity<String>("Prašymo sukurti nepavyko", HttpStatus.BAD_REQUEST);

		} else {
			application.setKindergartenChoises(choises);
			applicationDao.saveAndFlush(application);

			return new ResponseEntity<String>("Prašymas sukurtas sėkmingai", HttpStatus.OK);
		}

	}

	/**
	 * 
	 * capitalize first letter of string
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
	 * Delete application by id. Also deletes connected priorities, kindergarten
	 * choises, and additional guardian who has no other applications. Also
	 * decreases number of taken places in approved Kindergarten if applicable.
	 * 
	 * @param id
	 */
	@Transactional
	public ResponseEntity<String> deleteApplication(Long id) {
		Application application = applicationDao.getOne(id);
		if (application != null) {

			ParentDetails additionalGuardian = application.getAdditionalGuardian();
			if (additionalGuardian != null) {
				int numberOfAdditionalGuardianApplications = additionalGuardian.removeApplication(application);

				if (numberOfAdditionalGuardianApplications == 0) {
					parentDetailsDao.delete(additionalGuardian);
				}

			}

			if (application.getApprovedKindergarten() != null) {
				gartenService.decreaseNumberOfTakenPlacesInAgeGroup(application.getApprovedKindergarten(),
						application.calculateAgeInYears());

			}

			applicationDao.deleteById(id);
			return new ResponseEntity<String>("Ištrinta sėkmingai", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Prašymas nerastas", HttpStatus.NOT_FOUND);
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
		if (application != null) {

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

		return new ResponseEntity<String>("Prašymas nerastas", HttpStatus.NOT_FOUND);
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
