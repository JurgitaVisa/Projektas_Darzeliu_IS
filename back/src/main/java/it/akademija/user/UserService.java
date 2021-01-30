package it.akademija.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.role.Role;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAO userDao;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(username + " not found.");
		} else {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					AuthorityUtils.createAuthorityList(new String[] { "ROLE_" + user.getRole().toString() }));
		}

	}

	/**
	 * Create new user with specified parameters. Deletes FirstUser "admin" which
	 * was initialized at start up if there are other users with ADMIN authorization
	 * in the user repository
	 * 
	 * @param userData
	 */
	@Transactional
	public void createUser(UserDTO userData) {
		User newUser = new User();

		newUser.setRole(Role.valueOf(userData.getRole()));
		newUser.setName(userData.getName());
		newUser.setSurname(userData.getSurname());
		newUser.setBirthdate(userData.getBirthdate());
		newUser.setPersonalCode(userData.getPersonalCode());
		newUser.setAddress(userData.getAddress());
		newUser.setPhone(userData.getPhone());
		newUser.setEmail(userData.getEmail());
		newUser.setUsername(userData.getUsername());
		newUser.setPassword(encoder.encode(userData.getPassword()));

		userDao.save(newUser);

		if (userData.getRole().equals("ADMIN") && userDao.findByRole(Role.ADMIN).size() > 1
				&& userDao.findByUsername("admin") != null) {
			userDao.deleteByUsername("admin");
		}

	}

	/**
	 * 
	 * Delete user with a specified username. In case there are no other users with
	 * ADMIN authorization in the user repository, creates a temporary user with
	 * username "admin" and password "laikinas"
	 * 
	 * @param username
	 */
	@Transactional
	public void deleteUser(String username) {

		if (findByUsername(username).getRole().equals(Role.ADMIN) && userDao.findByRole(Role.ADMIN).size() == 1) {
			userDao.save(new User(Role.ADMIN, "admin", "admin", "admin", encoder.encode("laikinas")));
		}

		userDao.deleteByUsername(username);
	}

	/**
	 * Siulau aprasyti savo metodus, kad butu aisku, ka jie turetu daryti Sitas
	 * metodas neturetu grazinti pilno User i front, nes ten yra per daug info, tame
	 * tarpe psw ir asmens kodas
	 * 
	 * @return list of user details for ADMIN
	 */
	public Iterable<User> getAll() {
		return userDao.findAll();
	}

	/**
	 * 
	 * Finds user with a specified username. Don't return User entity via REST. 
	 * 
	 * @param username
	 * @return User entity (includes sensitive data)
	 */
	@Transactional(readOnly = true)
	public User findByUsername(String username) {

		return userDao.findByUsername(username);

	}

	/**
	 * Restores user password to initial value for user with specified username.
	 * Initial password value equals to username
	 * 
	 * @param username
	 */
	@Transactional
	public void restorePassword(String username) {
		User user=findByUsername(username);
		user.setPassword(encoder.encode(username));

		userDao.save(user);

	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}
