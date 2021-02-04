package it.akademija.user;

import java.util.List;
import java.util.stream.Collectors;

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
	 * @param userData data for new user
	 * @throws Exception
	 */

	@Transactional
	public void createUser(UserDTO userData) throws Exception {
		User newUser = new User();

		if (userData.getRole().equals("USER") && ((userData.getPhone() == null || userData.getPhone().isEmpty())
				|| (userData.getAddress() == null || userData.getAddress().isEmpty())
				|| (userData.getPersonalCode() == null || userData.getPersonalCode().isEmpty())
				|| (userData.getBirthdate() == null))) {

			throw new Exception("Negali buti tuscia");
		}

		newUser.setRole(Role.valueOf(userData.getRole()));
		newUser.setName(userData.getName());
		newUser.setSurname(userData.getSurname());
		newUser.setBirthdate(userData.getBirthdate());
		newUser.setPersonalCode(userData.getPersonalCode());
		newUser.setAddress(userData.getAddress());
		newUser.setPhone(userData.getPhone());
		newUser.setEmail(userData.getEmail());
		newUser.setUsername(userData.getUsername());
		newUser.setPassword(encoder.encode(userData.getUsername()));
		userDao.saveAndFlush(newUser);

		/*
		 * if (userData.getRole().equals("ADMIN") &&
		 * userDao.findByRole(Role.ADMIN).size() > 1 &&
		 * userDao.findByUsername("admin@admin.lt") != null) {
		 * userDao.deleteByUsername("admin@admin.lt"); }
		 */

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
			userDao.save(new User(Role.ADMIN, "admin", "admin", "admin@admin.lt", "admin@admin.lt",
					encoder.encode("admin@admin.lt")));
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

	@Transactional(readOnly = true)
	public List<UserInfo> getAllUsers() {
		List<User> users = userDao.findAll();
		return users.stream().map(user -> new UserInfo(user.getUserId(), user.getRole().name(), user.getName(),
				user.getSurname(), user.getUsername())).collect(Collectors.toList());
	}

	@Transactional
	public UserInfo getUserDetails(String username) {
		User user = userDao.findByUsername(username);
		return new UserInfo(user.getRole().name(), user.getName(), user.getSurname(), user.getBirthdate(),
				user.getAddress(), user.getPersonalCode(), user.getPhone(), user.getEmail(), user.getUsername());
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
	 * 
	 * Finds user with a specified personal code. Don't return User entity via REST.
	 * 
	 * @param personalCode
	 * @return User entity (includes sensitive data)
	 */

	@Transactional(readOnly = true)
	public User findByPersonalCode(String personalCode) {

		return userDao.findByPersonalCode(personalCode);
	}

	/**
	 * Restores user password to initial value for user with specified username.
	 * Initial password value equals to username
	 * 
	 * @param username
	 */
	@Transactional
	public void restorePassword(String username) {
		User user = findByUsername(username);
		user.setPassword(encoder.encode(username));

		userDao.save(user);

	}

	/**
	 * 
	 * Updates fields for user with specified username. Field for setting user role
	 * can not be updated. Any user can update their own data.
	 * 
	 * @param userData new user data
	 * @param username
	 */
	@Transactional
	public void updateUserData(UserDTO userData, String username) {
		User user = findByUsername(username);

		user.setName(userData.getName());
		user.setSurname(userData.getSurname());
		user.setAddress(userData.getAddress());
		user.setBirthdate(userData.getBirthdate());
		user.setPersonalCode(userData.getPersonalCode());
		user.setPhone(userData.getPhone());
		user.setEmail(userData.getEmail());
		user.setUsername(userData.getUsername());
		user.setPassword(encoder.encode(userData.getPassword()));

		userDao.save(user);

	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}
