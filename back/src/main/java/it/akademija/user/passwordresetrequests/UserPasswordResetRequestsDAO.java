package it.akademija.user.passwordresetrequests;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordResetRequestsDAO extends JpaRepository<UserPasswordResetRequestsEntity, Long> {

}
