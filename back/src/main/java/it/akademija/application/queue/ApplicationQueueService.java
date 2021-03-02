package it.akademija.application.queue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import it.akademija.kindergarten.Kindergarten;
import it.akademija.kindergarten.KindergartenDAO;
import it.akademija.kindergarten.KindergartenService;
import it.akademija.kindergartenchoise.KindergartenChoise;

@Service
public class ApplicationQueueService {

	@Autowired
	private ApplicationDAO applicationDao;

	@Autowired
	private KindergartenService gartenService;

	@Autowired
	private ApplicationQueueDAO queueDao;

	@Transactional
	public void processApplicationsToQueue() {
		List<Application> applications = applicationDao.findAllApplicationsWithStatusSubmitted().stream()
				.sorted(Comparator.comparing(Application::getPriorityScore).reversed()
						.thenComparing(Application::getBirthdate).thenComparing(Application::getChildSurname))
				.collect(Collectors.toList());

		// reset state for any non-approved applications
		for (Application a : applications) {
			ApplicationQueue notYetApproved = queueDao.findByChildPersonalCode(a.getChildPersonalCode());
			if (notYetApproved != null) {
				
				long age = a.calculateAgeInYears();				
				resetToInitialState(notYetApproved, age);
			}
		}

		List<ApplicationQueue> applicationQueue = new ArrayList<>();

		// set initial number to 0
		int lastNumberInWaitingList = 0;

		for (Application a : applications) {

			long age = a.calculateAgeInYears();			

			ApplicationQueue newApplication = queueDao.findByChildPersonalCode(a.getChildPersonalCode());

			if (newApplication == null) {
				newApplication = new ApplicationQueue(a.getChildPersonalCode(), a.getChildName(), a.getChildSurname());
				newApplication.setApplication(a);
			}

			List<KindergartenChoise> choises = a.getKindergartenChoises().stream()
					.sorted(Comparator.comparing(KindergartenChoise::getKindergartenChoisePriority))
					.collect(Collectors.toList());

			for (KindergartenChoise choise : choises) {

				Kindergarten garten = choise.getKindergarten();
				int availablePlaces = getNumberOfAvailablePlaces(garten, age);

				if (availablePlaces > 0) {
					newApplication.setKindergarten(garten);
					gartenService.increaseNumberOfTakenPlacesInAgeGroup(garten, age);
					break;
				}
			}

			if (newApplication.getKindergarten() == null) {

				lastNumberInWaitingList++;
				newApplication.setNumberInWaitingList(lastNumberInWaitingList);
			}

			applicationQueue.add(newApplication);
		}
		

		queueDao.saveAll(applicationQueue);

	}
	
	/**
	 * 
	 * Get application queue
	 * @param pageable 
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<ApplicationQueueInfo> getApplicationQueueInformation(Pageable pageable) {
		
		return queueDao.findAllApplications(pageable);
	}
	
	

	/**
	 * Reset state of queued application to initial values and decrease number of
	 * taken places in Kindergarten for corresponding age group by 1
	 * 
	 * @param application
	 * @param age
	 */
	private void resetToInitialState(ApplicationQueue application, long age) {
		Kindergarten garten = application.getKindergarten();
		if (garten != null) {
			gartenService.decreaseNumberOfTakenPlacesInAgeGroup(garten, age);
			application.setKindergarten(null);
		}
		application.setNumberInWaitingList(0);
		queueDao.save(application);
	}
	

	/**
	 * Get number of available places in specified Kindergarten age group
	 * 
	 * @param garten
	 * @param age
	 * @return number of available places
	 */
	private int getNumberOfAvailablePlaces(Kindergarten garten, long age) {

		if (age >= 2 && age < 3) {
			return garten.getCapacityAgeGroup2to3() - garten.getPlacesTakenAgeGroup2to3();
		} else {
			return garten.getCapacityAgeGroup3to6() - garten.getPlacesTakenAgeGroup3to6();
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

	public ApplicationQueueDAO getQueueDao() {
		return queueDao;
	}

	public void setQueueDao(ApplicationQueueDAO queueDao) {
		this.queueDao = queueDao;
	}


}
