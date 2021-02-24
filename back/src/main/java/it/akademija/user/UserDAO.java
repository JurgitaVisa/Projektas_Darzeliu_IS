package it.akademija.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.akademija.role.Role;

public interface UserDAO extends JpaRepository<User, Long> {

	User findByUsername(String username);

	List<User> findByRole(Role role);

	void deleteByUsername(String username);

	@Query("SELECT new User(u.userId, u.role, u.username) FROM User u")
	Page<User> findAll(Pageable pageable);

}
