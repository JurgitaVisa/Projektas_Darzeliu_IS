package it.akademija.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.role.Role;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAO userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(username+" not found.");
		} else {
			return new org.springframework.security.core.userdetails.User(
		user.getUsername(), user.getPassword(),
		AuthorityUtils.createAuthorityList(
		new String[] { "ROLE_" + user.getRole().toString() }) );
		}	
		
	}	

	@Transactional
	public void createUser(UserDTO userData) {
		User newUser = new User(); 
		PasswordEncoder encoder = new BCryptPasswordEncoder();
//		PasswordEncoder encoder =
//				PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		newUser.setUsername(userData.getUsername());
		newUser.setName(userData.getName());
		newUser.setSurname(userData.getSurname());
		newUser.setPassword(encoder.encode(userData.getPassword()));
		newUser.setRole(Role.valueOf(userData.getRole()));
		
		userDao.save(newUser);
	}

	@Transactional
	public void deleteUser(String username) {
		userDao.deleteByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		
		return userDao.findByUsername(username);
		
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	

	
}
