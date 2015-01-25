package com.example.localdata;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by goran on 9.1.2015..
 */

@Table(name="Login")
public class Login extends Model {
    @Column(name = "email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="authorization_token")
    private String authorization_token;
    @Column(name="participant_id")
    private String participant_id;
    @Column(name="loggedIn")
    private boolean loggedIn;

    public Login(){
        super();
    }

    public String getAuthorization_token() {
        return authorization_token;
    }

    public void setAuthorization_token(String authorization_token) {
        this.authorization_token = authorization_token;
    }

    public String getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(String participant_id) {
        this.participant_id = participant_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
