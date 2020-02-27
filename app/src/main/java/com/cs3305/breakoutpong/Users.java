package com.cs3305.breakoutpong;

/**
 * Users is a class representing a users account
 */
public class Users{

    /**
     * String : represents a users name
     */
    private String name;
    /**
     * String : represents a users email
     */
    private String email;
    /**
     * String : represents a users password
     */
    private String password;

    /**
     * Getter method for name
     * @return String : represents a users name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name
     * @param name String : represents a users name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for email
     * @return String : represents a users email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for email
     * @param email String : represents a users email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for password
     * @return String : represents a users password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for password
     * @param password String : represents a users password
     */
    //TODO add SHA256 encryption
    public void setPassword(String password) {
        this.password = password;
    }
}
