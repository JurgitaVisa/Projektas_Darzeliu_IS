package it.akademija.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {

	User findByUsername(String username);

	void deleteByUsername(String username);

}
