package it.akademija.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.user.User;
import it.akademija.user.UserDAO;

@Component
public class DatabaseUserDetailPasswordService implements UserDetailsPasswordService {

	private UserDetailsService userDetailsService;

	private UserDAO userDao;

	public DatabaseUserDetailPasswordService(UserDetailsService userDetailsService, UserDAO userDao) {
		super();
		this.userDetailsService = userDetailsService;
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
		
		User user = userDao.findByUsername(userDetails.getUsername());
		user.setPassword(newPassword);
		userDao.saveAndFlush(user);

		return this.userDetailsService.loadUserByUsername(user.getUsername());
	}

}
