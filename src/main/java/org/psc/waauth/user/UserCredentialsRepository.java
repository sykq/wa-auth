package org.psc.waauth.user;

import java.util.Optional;

import org.psc.waauth.user.domain.User;
import org.psc.waauth.user.domain.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> findByUserid(long userid);

    Optional<UserCredentials> findByUser(User user);
}
