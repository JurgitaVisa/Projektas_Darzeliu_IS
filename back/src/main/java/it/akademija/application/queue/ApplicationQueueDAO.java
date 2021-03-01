package it.akademija.application.queue;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationQueueDAO extends JpaRepository<ApplicationQueue, Long> {

	ApplicationQueue findByChildPersonalCode(String childPersonalCode);

	ApplicationQueue deleteByChildPersonalCode(String childPersonalCode);
	
	@Query("SELECT new it.akademija.application.queue.ApplicationQueueInfo(aq.application.id, aq.childPersonalCode, aq.childName, aq.childSurname, aq.kindergarten.name, aq.application.status, aq.numberInWaitingList) FROM ApplicationQueue aq")
	Page<ApplicationQueueInfo> findAllApplications(Pageable pageable);

}
