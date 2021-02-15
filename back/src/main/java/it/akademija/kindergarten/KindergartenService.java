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
	 * Get all kindergarten ID's, names and addresses
	 * 
	 * @return a list of kindergarten ID, names and addresses sorted by name ASC
	 */
	@Transactional(readOnly = true)
	public List<KindergartenDTO> getAllKindergartenNames() {
		List<Kindergarten> kindergartens = gartenDao.findAll(Sort.by("name").ascending());
		return kindergartens.stream().map(garten -> new KindergartenDTO(garten.getId(), garten.getName(),
				garten.getAddress(), garten.getElderate())).collect(Collectors.toList());
	}
<<<<<<< Updated upstream
	
=======
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
	/**
	 * Gel all elderates
	 * 
	 * @return list of elderates
	 */
	public Set<String> getAllElderates() {
<<<<<<< Updated upstream
		
		return gartenDao.findDistinctElderates();
	}
=======

		return gartenDao.findDistinctElderates();
	}
>>>>>>> Stashed changes
>>>>>>> Stashed changes

	/**
	 * 
	 * Returns a page of Kindergarten with specified page number and page size
	 * 
	 * @param pageable
	 * @return page from kindergarten database
	 */
	@Transactional(readOnly = true)
	public Page<Kindergarten> getKindergartenPage(Pageable pageable) {

		return gartenDao.findAll(pageable);
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
	public void createNewKindergarten(Kindergarten kindergarten) {
//		String name= kindergarten.getName();
//		name= name.substring(0, 1).toUpperCase() + name.substring(1);
//		kindergarten.setName(name);
		gartenDao.save(kindergarten);

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
		
		if (kindergarten!=null && kindergarten.getId()!=id) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Delete kindergarten with specified id
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
	public void updateKindergarten(String id, Kindergarten updatedInfo) {
		Kindergarten current = gartenDao.findById(id).orElse(null);

		current.setName(updatedInfo.getName());
		current.setAddress(updatedInfo.getAddress());
		current.setElderate(updatedInfo.getElderate());
		current.setCapacityAgeGroup2to3(updatedInfo.getCapacityAgeGroup2to3());
		current.setCapacityAgeGroup3to6(updatedInfo.getCapacityAgeGroup3to6());

		gartenDao.save(current);
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
	 * Get one by name
	 * 
	 * @param name
	 * @return
	 */
	public List<Kindergarten> getOneByName(String name) {

		return gartenDao.findByNameContainingIgnoreCase(name);
	}

	public KindergartenDAO getGartenDao() {
		return gartenDao;
	}

	public void setGartenDao(KindergartenDAO gartenDao) {
		this.gartenDao = gartenDao;
	}

	

}
