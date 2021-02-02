package it.akademija.kindergarten;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KindergartenService {

	@Autowired
	private KindergartenDAO gartenDao;
	
	@Transactional(readOnly = true)
	public List<KindergartenDTO> getAllKindergarten() {
		List<Kindergarten> kindergartens = gartenDao.findAll();
		return kindergartens.stream().map(garten-> new KindergartenDTO(garten.getName(), garten.getAddress())).collect(Collectors.toList());
	}
	
	
	//@Transactional(readOnly = true)
	public Kindergarten findByName(String name) {
		
		return gartenDao.findByNameIgnoreCase(name);			
	}
	
	@Transactional
	public void createNewKindergarten(Kindergarten kindergarten) {
		gartenDao.save(kindergarten);
		
	}
	
	
	
	
	

	public KindergartenDAO getGartenDao() {
		return gartenDao;
	}

	public void setGartenDao(KindergartenDAO gartenDao) {
		this.gartenDao = gartenDao;
	}


	

	
	
	
	
	
}
