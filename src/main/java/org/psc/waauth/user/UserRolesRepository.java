package org.psc.waauth.user;

import java.util.List;

import org.psc.waauth.user.domain.User;
import org.psc.waauth.user.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUser(User user);

}
