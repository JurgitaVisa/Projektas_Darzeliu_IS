package it.akademija.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.role.Role;

public interface UserDAO extends JpaRepository<User, Long> {

	User findByUsername(String username);

	List<User> findByRole(Role role);

	void deleteByUsername(String username);

	Page<User> findAll(Pageable pageable);

}
