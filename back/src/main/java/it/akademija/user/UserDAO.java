package it.akademija.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.role.Role;

public interface UserDAO extends JpaRepository<User, Long> {

	User findByUsername(String username);

	List<User> findByRole(Role role);

	void deleteByUsername(String username);

	List<User> findAllByOrderByUserIdDesc();

}
