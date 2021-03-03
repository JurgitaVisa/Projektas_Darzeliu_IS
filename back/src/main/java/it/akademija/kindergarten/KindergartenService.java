package it.akademija.kindergarten;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.application.Application;
import it.akademija.application.ApplicationDAO;

@Service
public class KindergartenService {

	private static final Logger LOG = LoggerFactory.getLogger(KindergartenService.class);

	@Autowired
	private KindergartenDAO gartenDao;

	@Autowired
	private ApplicationDAO applicationDao;

	/**
	 * Get all kindergarten ID's, names and addresses where capacity in any age
	 * group is more than zero
	 * 
	 * @return a list of kindergarten ID, names and addresses sorted by name ASC
	 */
	@Transactional(readOnly = true)
	public List<KindergartenInfo> getAllWithNonZeroCapacity() {
		List<Kindergarten> kindergartens = gartenDao.findAllWithNonZeroCapacity(Sort.by("name").ascending());
		return kindergartens.stream().map(garten -> new KindergartenInfo(garten.getId(), garten.getName(),
				garten.getAddress(), garten.getElderate())).collect(Collectors.toList());
	}

	/**
	 * Gel all elderate names
	 * 
	 * @return list of elderates
	 */
	public Set<String> getAllElderates() {

		return gartenDao.findDistinctElderates();
	}

	/**
	 * 
	 * Returns a page of Kindergarten with specified page number and page size
	 * 
	 * @param pageable
	 * @return page from kindergarten database
	 */
	@Transactional(readOnly = true)
	public Page<Kindergarten> getKindergartenPage(Pageable pageable) {

		return gartenDao.findAllKindergarten(pageable);
	}

	/**
	 * 
	 * Returns a page of Kindergarten filtered by name containing text with
	 * specified page number and page size
	 * 
	 * @param pageable
	 * @return filtered page from kindergarten database
	 */
	@Transactional(readOnly = true)
	public Page<Kindergarten> getKindergartenPageFilteredByName(String name, Pageable pageable) {

		return gartenDao.findByNameContainingIgnoreCase(name, pageable);

	}

	/**
	 * Save new kindergarten to database
	 * 
	 * @param kindergarten
	 */
	@Transactional
	public void createNewKindergarten(KindergartenDTO kindergarten) {

		gartenDao.save(new Kindergarten(kindergarten.getId(), kindergarten.getName(), kindergarten.getAddress(),
				kindergarten.getElderate(), kindergarten.getCapacityAgeGroup2to3(),
				kindergarten.getCapacityAgeGroup3to6()));

	}

	/**
	 * Find kindergarten by id. Read only
	 * 
	 * @param id
	 * @return kindergarten or null if not found
	 */
	@Transactional(readOnly = true)
	public Kindergarten findById(String id) {

		return gartenDao.findById(id).orElse(null);
	}

	/**
	 * Find kindergarten by name. Read only
	 * 
	 * @param name
	 * @return kindergarten or null if not found
	 */
	@Transactional(readOnly = true)
	public boolean nameAlreadyExists(String name, String id) {
		Kindergarten kindergarten = gartenDao.findByName(name);

		if (kindergarten != null && kindergarten.getId() != id) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Delete kindergarten with specified id. Also deletes all related kindergarten
	 * choises
	 * 
	 * @param id
	 */
	@Transactional
	public ResponseEntity<String> deleteKindergarten(String id) {

		Kindergarten garten = gartenDao.findById(id).orElse(null);

		if (garten != null) {
			Set<Application> applicationQueue = garten.getApprovedApplications();
			for (Application a : applicationQueue) {
				a.setApprovedKindergarten(null);
				applicationDao.saveAndFlush(a);
			}

			gartenDao.deleteById(id);

			LOG.info("** UserService: trinamas darželis ID [{}] **", id);
			return new ResponseEntity<String>("Darželis ištrintas sėkmingai", HttpStatus.OK);

		} else {

			return new ResponseEntity<String>("Darželis su tokiu įstaigos kodu nerastas", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Update kindergarten
	 * 
	 * @param currentInfo
	 * @param kindergarten
	 */
	@Transactional
	public void updateKindergarten(String id, KindergartenDTO updatedInfo) {
		Kindergarten current = gartenDao.findById(id).orElse(null);

		current.setName(updatedInfo.getName());
		current.setAddress(updatedInfo.getAddress());
		current.setElderate(updatedInfo.getElderate());
		current.setCapacityAgeGroup2to3(updatedInfo.getCapacityAgeGroup2to3());
		current.setCapacityAgeGroup3to6(updatedInfo.getCapacityAgeGroup3to6());

		gartenDao.save(current);
	}

	/**
	 * 
	 * Kindergarten prioritize statistics
	 * 
	 * @param pageable
	 * 
	 * @return statistics
	 */
	public Page<KindergartenStatistics> getKindergartenStatistics(Pageable pageable) {

		return gartenDao.findAllChoises(pageable);
	}

	/**
	 * Delete kindergarten by name. Used during DB setup
	 * 
	 * @param name
	 */
	@Transactional
	public void deleteByName(String name) {
		gartenDao.deleteByName(name);
	}

	/**
	 * 
	 * Set number of taken places in Kindergarten for corresponding age group by 1
	 * 
	 * @param garten
	 */
	public void decreaseNumberOfTakenPlacesInAgeGroup(Kindergarten garten, long age) {

		if (age >= 2 && age < 3) {
			int takenPlaces = garten.getPlacesTakenAgeGroup2to3() - 1;
			if (takenPlaces < 0) {
				takenPlaces = 0;
			}
			garten.setPlacesTakenAgeGroup2to3(takenPlaces);
		} else {
			int takenPlaces = garten.getPlacesTakenAgeGroup3to6() - 1;
			if (takenPlaces < 0) {
				takenPlaces = 0;
			}
			garten.setPlacesTakenAgeGroup3to6(takenPlaces);
		}

		gartenDao.save(garten);
	}

	/**
	 * Increase number of taken places for specific Kindergarten age group by 1
	 * 
	 * @param garten
	 * @param age
	 */
	public void increaseNumberOfTakenPlacesInAgeGroup(Kindergarten garten, long age) {

		if (age >= 2 && age < 3) {
			int capacity = garten.getPlacesTakenAgeGroup2to3() + 1;
			garten.setPlacesTakenAgeGroup2to3(capacity);
		} else {
			int capacity = garten.getPlacesTakenAgeGroup3to6() + 1;
			garten.setCapacityAgeGroup3to6(capacity);
		}
		gartenDao.save(garten);
	}

	/**
	 * Reset number of taken places in all groups to 0
	 * 
	 */
	@Transactional
	public void resetTakenPlacesToZero() {
		List<Kindergarten> gartenList = gartenDao.findAll();
		for (Kindergarten k : gartenList) {
			k.setPlacesTakenAgeGroup2to3(0);
			k.setPlacesTakenAgeGroup3to6(0);
		}

		gartenDao.saveAll(gartenList);
	}

	public KindergartenDAO getGartenDao() {
		return gartenDao;
	}

	public void setGartenDao(KindergartenDAO gartenDao) {
		this.gartenDao = gartenDao;
	}

	public ApplicationDAO getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDAO applicationDao) {
		this.applicationDao = applicationDao;
	}

	

}
