package org.psc.waauth.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.psc.waauth.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = -76726661312942701L;
    private User user;
    private UserDetails userDetails;

    public UserPrincipal(User user) {
        this.user = user;
        List<String> userRoles = user.getUserRoles().stream().map(e -> e.getUserRoleId().getRole())
                .collect(Collectors.toList());
        String[] roles = new String[userRoles.size()];
        roles = userRoles.toArray(roles);
        userDetails = org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
                .password(user.getUserCredentials().getHash()).roles(roles).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public String getPassword() {
        return userDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userDetails.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userDetails.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userDetails.isAccountNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userDetails.isEnabled();
    }

}
