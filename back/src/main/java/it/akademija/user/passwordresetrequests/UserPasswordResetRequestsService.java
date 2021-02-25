package it.akademija.user.passwordresetrequests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.user.User;
import it.akademija.user.UserDAO;

@Service
public class UserPasswordResetRequestsService {

	@Autowired
	UserDAO userDao;
	
	@Autowired
	UserPasswordResetRequestsDAO userPasswordResetRequestsDAO;
	
	@Transactional(readOnly = true)
	public boolean requestPasswordReset(String email) {
		List<User> userList = userDao.findAll();
		Long userId = null;
		for(User user : userList) {
			if(user.getEmail().equals(email)) {
				userId = user.getUserId();
			}
		}
		if(userId != null) {
			userPasswordResetRequestsDAO.saveAndFlush(new UserPasswordResetRequestsEntity(userId));
			return true;
		}
		else {
			return false;
		}
	}
	
}
