package it.akademija.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.akademija.role.Role;

public interface UserDAO extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u LEFT JOIN ParentDetails d ON u.userId=d.user.userId GROUP BY u.userId HAVING u.username=?1 ")
	User findByUsername(String username);

	List<User> findByRole(Role role);

	void deleteByUsername(String username);

	Page<User> findAll(Pageable pageable);

}
