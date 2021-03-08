package it.akademija.document;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDAO extends JpaRepository<DocumentEntity, Long> {

}
