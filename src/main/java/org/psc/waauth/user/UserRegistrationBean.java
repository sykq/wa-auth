package org.psc.waauth.user;

import java.util.UUID;

public class UserRegistrationBean {

    private String userName;
    private String emailAddress;
    private String accountName;
    private UUID salt;
    private byte[] password;
    private byte[] confirmationPassword;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress
     *            the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName
     *            the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return the salt
     */
    public UUID getSalt() {
        return salt;
    }

    /**
     * @param salt
     *            the salt to set
     */
    public void setSalt(UUID salt) {
        this.salt = salt;
    }

    /**
     * @return the password
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * @return the confirmationPassword
     */
    public byte[] getConfirmationPassword() {
        return confirmationPassword;
    }

    /**
     * @param confirmationPassword
     *            the confirmationPassword to set
     */
    public void setConfirmationPassword(byte[] confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

}
