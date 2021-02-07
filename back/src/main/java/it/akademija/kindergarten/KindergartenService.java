package it.akademija.kindergarten;

import java.util.List;
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
	
	@Transactional(readOnly = true)
	public List<KindergartenDTO> getAllKindergartenNames() {
		List<Kindergarten> kindergartens = gartenDao.findAll(Sort.by("name").ascending());
		return kindergartens.stream().map(garten->new KindergartenDTO(garten.getId(), garten.getName(), garten.getAddress(), garten.getElderate())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<Kindergarten> getKindergartenPage(Pageable pageable) {
		
		return gartenDao.findAll(pageable);
	}

//	@Transactional(readOnly = true)
//	public Kindergarten findByName(String name) {
//
//		return gartenDao.findByNameIgnoreCase(name);
//	}

	@Transactional
	public void createNewKindergarten(Kindergarten kindergarten) {
		gartenDao.save(kindergarten);

	}

	@Transactional(readOnly = true)
	public Kindergarten findById(String id) {

		return gartenDao.findById(id).orElse(null);
	}

	@Transactional
	public void deleteKindergarten(String id) {
		gartenDao.deleteById(id);

	}
	
	@Transactional
	public void deleteByName(String name) {
		gartenDao.deleteByName(name);
	}

	public KindergartenDAO getGartenDao() {
		return gartenDao;
	}

	public void setGartenDao(KindergartenDAO gartenDao) {
		this.gartenDao = gartenDao;
	}

	

}
