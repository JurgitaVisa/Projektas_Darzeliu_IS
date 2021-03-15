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

	public List<UserPasswordResetRequestsEntity> getAllRequests() {

		return userPasswordResetRequestsDAO.findAll();
	}

	public void deletePasswordRequest(String username) {

		userPasswordResetRequestsDAO.deleteById(userDao.findByUsername(username).getUserId());
	}

	@Transactional(readOnly = true)
	public void requestPasswordReset(String email) {

		User user = userDao.findByUsername(email);
		if (user != null) {
			userPasswordResetRequestsDAO.saveAndFlush(new UserPasswordResetRequestsEntity(user.getUserId()));
		}
	}

}
