package com.example.plants.poisonousplants.View;

/**
 * The class represents a User object
 *
 * @author  Rogelio Paniagua
 * @version 1.0
 * @since   2019-02-19
 */
public class User {

    private String id;
    private String userName;
    private String email;
    private String password;

    /** Creates a user with the specified id, user name, email, and password.
     * @param id The employee’s last name.
     * @param userName The employee’s first name.
     * @param email The employee’s last name.
     * @param password The employee’s last name.
     */
    public User(String id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    /** Gets the user's id.
     * @return A string representing the user’s id.
     */
    public String getId() {
        return id;
    }

    /** Sets the user’s id.
     * @param id A String containing the user’s id.
     *
     */
    public void setId(String id) {
        this.id = id;
    }

    /** Gets the user's user name.
     * @return A string representing the user’s user name.
     *
     */
    public String getUserName() {
        return userName;
    }

    /** Sets the user’s name.
     * @param name A String containing the user’s name.
     *
     */
    public void setName(String name) {
        this.userName = name;
    }

    /** Gets the user's email.
     * @return A string representing the user’s email.
     *
     */
    public String getEmail() {
        return email;
    }

    /** Sets the user’s email.
     * @param email A String containing the user’s email.
     *
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Gets the user's password.
     * @return A string representing the user’s password.
     *
     */
    public String getPassword() {
        return password;
    }

    /** Sets the user’s password.
     * @param password A String containing the user’s password.
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
