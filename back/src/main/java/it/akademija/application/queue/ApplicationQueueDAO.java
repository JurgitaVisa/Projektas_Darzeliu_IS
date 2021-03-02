package it.akademija.application.queue;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationQueueDAO extends JpaRepository<ApplicationQueue, Long> {

	ApplicationQueue findByChildPersonalCode(String childPersonalCode);

	ApplicationQueue deleteByChildPersonalCode(String childPersonalCode);
	
	@Query("SELECT new it.akademija.application.queue.ApplicationQueueInfo(aq.application.id, aq.childPersonalCode, aq.childName, aq.childSurname, k.name, aq.application.status, aq.numberInWaitingList) FROM ApplicationQueue aq LEFT JOIN Kindergarten k ON aq.kindergarten.id=k.id")
	Page<ApplicationQueueInfo> findAllApplications(Pageable pageable);

}
