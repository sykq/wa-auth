package org.psc.waauth.user;

import java.util.Optional;

import org.psc.waauth.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserid(long userid);

    Optional<User> findByUsername(String username);
}
