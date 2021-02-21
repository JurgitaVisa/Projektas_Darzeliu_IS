package it.akademija.kindergarten;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KindergartenService {

	@Autowired
	private KindergartenDAO gartenDao;

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
	public void deleteKindergarten(String id) {

		gartenDao.deleteById(id);

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

//	/**
//	 * 
//	 * Get one by name
//	 * 
//	 * @param name
//	 * @return
//	 */
//	public List<Kindergarten> getOneByName(String name) {
//
//		return gartenDao.findByNameContainingIgnoreCase(name);
//	}

	public KindergartenDAO getGartenDao() {
		return gartenDao;
	}

	public void setGartenDao(KindergartenDAO gartenDao) {
		this.gartenDao = gartenDao;
	}

}
