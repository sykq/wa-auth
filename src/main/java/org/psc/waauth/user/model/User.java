package org.psc.waauth.user.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "USER" database table.
 * 
 */
@Entity
@Table(name="\"USER\"")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userid;

	private String username;

	//bi-directional one-to-one association to Usercredential
	@OneToOne(mappedBy="user")
	private Usercredential usercredential;

	public User() {
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Usercredential getUsercredential() {
		return this.usercredential;
	}

	public void setUsercredential(Usercredential usercredential) {
		this.usercredential = usercredential;
	}

}