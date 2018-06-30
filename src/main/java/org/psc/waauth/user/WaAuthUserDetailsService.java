package org.psc.waauth.user;

import java.util.Optional;

import org.psc.waauth.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WaAuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public WaAuthUserDetailsService() {
/*
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN")
                .build();
        userDetailsManager = new InMemoryUserDetailsManager(user, admin);
*/
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("username=" + username + " not found");
        }

        return new UserPrincipal(user.get());
    }

}
