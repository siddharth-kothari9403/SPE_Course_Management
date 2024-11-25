package Elective_Management.Elective_Management.model;

import java.io.Serializable;

//request class to get the request from the user, this is used for authentication and registering a customer/driver/admin
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    //contains username, and password
    private String username;
    private String password;

    //need default constructor for JSON Parsing
    public JwtRequest() {}

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

