package it.akademija.user;

import java.security.SecureRandom;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	int strength = 14;

	private PasswordEncoder encoder = new BCryptPasswordEncoder(strength, new SecureRandom());

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

		if (userData.getRole().equals("USER")) {
			ParentDetails details = new ParentDetails();
			details.setAddress(userData.getAddress());
			details.setEmail(userData.getEmail());
			details.setName(userData.getName());
			details.setPersonalCode(userData.getPersonalCode());
			details.setPhone("370" + userData.getPhone());
			details.setSurname(userData.getSurname());
			newUser.setParentDetails(details);
		}

		newUser.setName(userData.getName());
		newUser.setSurname(userData.getSurname());
		newUser.setEmail(userData.getEmail());
		newUser.setRole(Role.valueOf(userData.getRole()));
		newUser.setUsername(userData.getUsername());
		newUser.setPassword(encoder.encode(userData.getUsername()));
		userDao.saveAndFlush(newUser);
	}

	/*
	 * if (userData.getRole().equals("ADMIN") &&
	 * userDao.findByRole(Role.ADMIN).size() > 1 &&
	 * userDao.findByUsername("admin@admin.lt") != null) {
	 * userDao.deleteByUsername("admin@admin.lt"); }
	 */

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
	 * Returns a page of registered Users info with specified page number and page
	 * size
	 * 
	 * @return list of user details for ADMIN
	 */
	@Transactional(readOnly = true)
	public Page<UserInfo> getAllUsers(Pageable pageable) {
		Page<User> users = userDao.findAll(pageable);
		Page<UserInfo> dtoPage = users.map(new Function<User, UserInfo>() {
			@Override
			public UserInfo apply(User user) {
				UserInfo dto = new UserInfo(user.getUserId(), user.getRole().name(), user.getUsername());
				return dto;
			}

		});
		return dtoPage;
	}

	@Transactional
	public UserInfo getUserDetails(String username) {
		User user = userDao.findByUsername(username);
		if (user.getRole().equals(Role.USER)) {
			return new UserInfo(user.getRole().name(), user.getName(), user.getSurname(),
					user.getParentDetails().getPersonalCode(), user.getParentDetails().getAddress(),
					user.getParentDetails().getPhone(), user.getEmail(), user.getUsername());

		}
		return new UserInfo(user.getUserId(), user.getRole().name(), user.getName(), user.getSurname(),
				user.getUsername());

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
		User user = findByUsername(username);
		user.setPassword(encoder.encode(username));

		userDao.save(user);

	}

	/**
	 * Changes users password
	 * 
	 * @param username
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@Transactional
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		User user = findByUsername(username);
		String currentPassword = user.getPassword();
		if (encoder.matches(oldPassword, currentPassword)) {
			user.setPassword(encoder.encode(newPassword));
			return true;
		} else {
			return false;
		}
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
		ParentDetails details = new ParentDetails();

		if (userData.getRole().equals("USER")) {
			details.setAddress(userData.getAddress());
			details.setPersonalCode(userData.getPersonalCode());
			details.setPhone("370" + userData.getPhone());
			details.setEmail(userData.getEmail());
			details.setName(userData.getName());
			details.setSurname(userData.getSurname());
			user.setParentDetails(details);
		}

		user.setName(userData.getName());
		user.setSurname(userData.getSurname());
		user.setEmail(userData.getEmail());

		userDao.save(user);

	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}
