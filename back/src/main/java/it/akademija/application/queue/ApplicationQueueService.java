package it.akademija.application.queue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.application.Application;
import it.akademija.application.ApplicationDAO;
import it.akademija.application.ApplicationStatus;
import it.akademija.kindergarten.Kindergarten;
import it.akademija.kindergarten.KindergartenService;
import it.akademija.kindergartenchoise.KindergartenChoise;

@Service
public class ApplicationQueueService {

	@Autowired
	private ApplicationDAO applicationDao;

	@Autowired
	private KindergartenService gartenService;

	@Transactional
	public void processApplicationsToQueue() {

		List<Application> applications = applicationDao.findAllApplicationsWithStatusSubmitted().stream()
				.sorted(Comparator.comparing(Application::getPriorityScore).reversed()
						.thenComparing(Application::getBirthdate).thenComparing(Application::getChildSurname, String.CASE_INSENSITIVE_ORDER))
				.collect(Collectors.toList());

		// reset state for any non-approved applications
		for (Application application : applications) {

			long age = application.calculateAgeInYears();
			if (age >= 7) {
				application.setStatus(ApplicationStatus.Neaktualus);
			}

			resetToInitialState(application, age);
		}

		List<Application> applicationQueue = new ArrayList<>();

		// set initial number to 0
		int lastNumberInWaitingList = 0;

		for (Application a : applications) {
			
			if(a.getStatus().equals(ApplicationStatus.Neaktualus)) {
				continue;
			}

			long age = a.calculateAgeInYears();

			List<KindergartenChoise> choises = a.getKindergartenChoises().stream()
					.sorted(Comparator.comparing(KindergartenChoise::getKindergartenChoisePriority))
					.collect(Collectors.toList());

			for (KindergartenChoise choise : choises) {

				Kindergarten garten = choise.getKindergarten();
				int availablePlaces = getNumberOfAvailablePlaces(garten, age);

				if (availablePlaces > 0) {
					a.setApprovedKindergarten(garten);
					gartenService.increaseNumberOfTakenPlacesInAgeGroup(garten, age);
					break;
				}
			}

			if (a.getApprovedKindergarten() == null) {

				lastNumberInWaitingList++;
				a.setNumberInWaitingList(lastNumberInWaitingList);
			}

			applicationQueue.add(a);
		}

		applicationDao.saveAll(applicationQueue);

	}

	/**
	 * 
	 * Get application queue sorted by Child surname
	 * 
	 * @param pageable
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<ApplicationQueueInfo> getApplicationQueueInformation(Pageable pageable) {

		return applicationDao.findQueuedApplications(pageable);
	}

	/**
	 * 
	 * Get application queue filtered by Child personal code and sorted by Child
	 * surname
	 * 
	 * @param pageable
	 * 
	 * @return
	 */
	public Page<ApplicationQueueInfo> getApplicationQueueInformationFilteredByChildId(String childPersonalCode,
			Pageable pageable) {
		
		return applicationDao.findQueuedApplicationsContaining(childPersonalCode, pageable);
	}

	/**
	 * Reset state of queued application to initial values and decrease number of
	 * taken places in Kindergarten for corresponding age group by 1
	 * 
	 * @param application
	 * @param age
	 */
	private void resetToInitialState(Application application, long age) {
		Kindergarten garten = application.getApprovedKindergarten();
		if (garten != null) {
			gartenService.decreaseNumberOfTakenPlacesInAgeGroup(garten, age);
			application.setApprovedKindergarten(null);
		}
		application.setNumberInWaitingList(0);
		applicationDao.save(application);
	}

	/**
	 * Get number of available places in specified Kindergarten age group
	 * 
	 * @param garten
	 * @param age
	 * @return number of available places
	 */
	private int getNumberOfAvailablePlaces(Kindergarten garten, long age) {

		if (age >= 1 && age < 3) {
			return garten.getCapacityAgeGroup2to3() - garten.getPlacesTakenAgeGroup2to3();
		} else if (age >= 3 && age < 7) {
			return garten.getCapacityAgeGroup3to6() - garten.getPlacesTakenAgeGroup3to6();
		} else {
			return 0;
		}
	}

	public ApplicationDAO getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDAO applicationDao) {
		this.applicationDao = applicationDao;
	}

	public KindergartenService getGartenService() {
		return gartenService;
	}

	public void setGartenService(KindergartenService gartenService) {
		this.gartenService = gartenService;
	}

}
