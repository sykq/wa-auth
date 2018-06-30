package org.psc.waauth.user.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: UserRoles
 *
 */
@Entity
@Table(name = "USERROLE")
// @IdClass(org.psc.waauth.user.domain.UserRoleId.class)
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserRoleId userRoleId;

    @MapsId("user")
    @JoinColumn(name = "USERID")
    private User user;

    public UserRole() {
        super();
    }

    /**
     * @return the userRoleId
     */
    public UserRoleId getUserRoleId() {
        return userRoleId;
    }

    /**
     * @param userRoleId
     *            the userRoleId to set
     */
    public void setUserRoleId(UserRoleId userRoleId) {
        this.userRoleId = userRoleId;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

}
