package com.example.localdata;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by goran on 9.1.2015..
 */

@Table(name="Login")
public class Login extends Model {
    @Column(name="idLogin")
    private long idLogin;
    @Column(name = "email")
    private String email;
    @Column(name="TAGID")
    private String TAGID;
    @Column(name="loggedIn")
    private boolean loggedIn;

    public Login(){
        super();
    }

    public Login(long idLogin, String email, String TAGID, boolean loggedIn){
        super();
        this.idLogin=idLogin;
        this.email=email;
        this.TAGID=TAGID;
        this.loggedIn=loggedIn;
    }

    public long getIdLogin() {
        return idLogin;
    }

    public String getEmail() {
        return email;
    }

    public String getTAGID() {
        return TAGID;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setIdLogin(long idLogin) {
        this.idLogin = idLogin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTAGID(String TAGID) {
        this.TAGID = TAGID;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
