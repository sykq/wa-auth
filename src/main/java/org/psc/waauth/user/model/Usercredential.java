package org.psc.waauth.user.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USERCREDENTIALS database table.
 * 
 */
@Entity
@Table(name="USERCREDENTIALS")
@NamedQuery(name="Usercredential.findAll", query="SELECT u FROM Usercredential u")
public class Usercredential implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userid;

	private String hash;

	private String loginname;

	private String salt;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="USERID")
	private User user;

	public Usercredential() {
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}