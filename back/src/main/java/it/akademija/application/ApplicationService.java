package it.akademija.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Transactional
	public void createNewApplication(String currentUsername, ApplicationDTO data) {

		Application application = new Application();

		User firstParent = userService.updateUserData(data.getMainGuardian(), currentUsername);

		ParentDetailsDTO detailsDto = data.getAdditionalGuardian();

		if (detailsDto.getPersonalCode() != null && detailsDto.getPersonalCode() != "") {
			ParentDetails secondParent = parentDetailsDao.findByPersonalCode(detailsDto.getPersonalCode());

			if (secondParent == null) {
				secondParent = parentDetailsDao.save(new ParentDetails(detailsDto.getPersonalCode(), detailsDto.getName(),
						detailsDto.getSurname(), detailsDto.getEmail(), detailsDto.getAddress(),
						("370" + detailsDto.getPhone())));
				//parentDetailsDao.saveAndFlush(secondParent);
			}

			application.setAdditionalGuardian(secondParent);
		}
		
		PrioritiesDTO prioritiesDto= data.getPriorities();

		Priorities priorities = prioritiesDao.save(new Priorities(prioritiesDto.isLivesInVilnius(), prioritiesDto.isChildIsAdopted(), prioritiesDto.isFamilyHasThreeOrMoreChildrenInSchools(),
				prioritiesDto.isGuardianInSchool(), prioritiesDto.isGuardianDisability()));	
		
		application.setSubmitedAt();
		application.setStatus(ApplicationStatus.Pateiktas);
		application.setChildName(data.getChildName());
		application.setChildSurname(data.getChildSurname());
		application.setChildPersonalCode(data.getChildPersonalCode());
		application.setBirthdate(data.getBirthdate());
		application.setPriorities(priorities);

		application.setMainGuardian(firstParent);

		KindergartenChoiseDTO choisesDto = data.getKindergartenChoises();

		Kindergarten first = gartenService.findById(choisesDto.getKindergartenId1());
		Kindergarten second = gartenService.findById(choisesDto.getKindergartenId2());
		Kindergarten third = gartenService.findById(choisesDto.getKindergartenId3());
		Kindergarten fouth = gartenService.findById(choisesDto.getKindergartenId4());
		Kindergarten fith = gartenService.findById(choisesDto.getKindergartenId5());

		Set<KindergartenChoise> choises = new HashSet<>();
		
		KindergartenChoise choise1 = choiseDao.save(new KindergartenChoise(first, application, 1));
		choises.add(choise1);
		KindergartenChoise choise2 = choiseDao.save(new KindergartenChoise(second, application, 2));
		choises.add(choise2);
		KindergartenChoise choise3 = choiseDao.save(new KindergartenChoise(third, application, 3));
		choises.add(choise3);
		KindergartenChoise choise4 = choiseDao.save(new KindergartenChoise(fouth, application, 4));
		choises.add(choise4);
		KindergartenChoise choise5 = choiseDao.save(new KindergartenChoise(fith, application, 5));
		choises.add(choise5);

		application.setKindergartenChoises(choises);

		applicationDao.saveAndFlush(application);

	}

//	PERKELTI i prasymu eiles formavima
//	private long calculateAgeInDays(LocalDate birthdate) {
//		int thisYear = LocalDate.now().getYear();
//		LocalDate septemberFirst = LocalDate.of(thisYear, 9, 1);
//		return ChronoUnit.DAYS.between(birthdate, septemberFirst);
//	}

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
