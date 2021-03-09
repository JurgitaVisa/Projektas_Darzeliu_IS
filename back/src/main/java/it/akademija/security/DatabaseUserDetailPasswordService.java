package it.akademija.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.user.User;
import it.akademija.user.UserDAO;

@Component
public class DatabaseUserDetailPasswordService implements UserDetailsPasswordService {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserDAO userDao;

	@Override
	@Transactional
	public UserDetails updatePassword(UserDetails userDetails, String newPassword) {

		User user = userDao.findByUsername(userDetails.getUsername());
		user.setPassword(newPassword);
		userDao.saveAndFlush(user);

		return this.userDetailsService.loadUserByUsername(user.getUsername());
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}
