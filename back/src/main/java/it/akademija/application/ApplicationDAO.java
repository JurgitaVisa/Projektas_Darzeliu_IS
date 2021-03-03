package it.akademija.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.akademija.application.queue.ApplicationQueueInfo;

public interface ApplicationDAO extends JpaRepository<Application, Long> {

	boolean existsApplicationByChildPersonalCode(String childPersonalCode);

	@Query("SELECT new it.akademija.application.ApplicationInfo(a.id, a.childPersonalCode,  a.childName, a.childSurname, max(case when c.kindergartenChoisePriority ='1' then c.kindergarten.name end) as choise1, max(case when c.kindergartenChoisePriority ='2' then c.kindergarten.name end) as choise2, max(case when c.kindergartenChoisePriority ='3' then c.kindergarten.name end) as choise3, max(case when c.kindergartenChoisePriority ='4' then c.kindergarten.name end) as choise4, max(case when c.kindergartenChoisePriority ='5' then c.kindergarten.name end) as choise5) FROM Application a LEFT JOIN KindergartenChoise c on a.id = c.application.id GROUP BY a.id HAVING a.childPersonalCode LIKE(concat(?1, '%'))")
	Page<ApplicationInfo> findByIdContaining(String childPersonalCode, Pageable pageable);

	@Query("SELECT new it.akademija.application.ApplicationInfo(a.id, a.childPersonalCode,  a.childName, a.childSurname, max(case when c.kindergartenChoisePriority ='1' then c.kindergarten.name end) as choise1, max(case when c.kindergartenChoisePriority ='2' then c.kindergarten.name end) as choise2, max(case when c.kindergartenChoisePriority ='3' then c.kindergarten.name end) as choise3, max(case when c.kindergartenChoisePriority ='4' then c.kindergarten.name end) as choise4, max(case when c.kindergartenChoisePriority ='5' then c.kindergarten.name end) as choise5) FROM Application a LEFT JOIN KindergartenChoise c on a.id = c.application.id GROUP BY a.id")
	Page<ApplicationInfo> findAllApplications(Pageable pageable);

	// get all, kurių statusas yra "Pateikta"
	@Query("SELECT a FROM Application a WHERE a.status=0")
	List<Application> findAllApplicationsWithStatusSubmitted();
	
	@Query("SELECT new it.akademija.application.queue.ApplicationQueueInfo(a.id, a.childPersonalCode, a.childName, a.childSurname, k.name, a.status, a.numberInWaitingList) FROM Application a LEFT JOIN Kindergarten k ON a.approvedKindergarten.id=k.id")
	Page<ApplicationQueueInfo> findQueuedApplications(Pageable pageable);	

}
