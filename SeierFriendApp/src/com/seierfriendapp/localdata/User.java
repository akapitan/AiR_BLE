package com.seierfriendapp.localdata;

import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;

/**
 * Entity class representing a user.
 */

@Table(name = "User")
public class User extends Model {
    @Column(name = "idUser")
    private long idUser;
    @Column(name = "s1member")
    private boolean s1member;
    @Column(name = "employee")
    private boolean employee;
    @Column(name = "salutation")
    private String salutation;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "title")
    private String title;
    @Column(name = "birthdate")
    private Date birthdate;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "points")
    private int points;
    @Column(name = "level")
    private int level;
    @Column(name = "checkins")
    private int checkins;
    @Column(name = "lastCheckin")
    private Date lastCheckin;
    @Column(name = "pointsToday")
    private int pointsToday;
    @Column(name = "authToken")
    private String authToken;

    public User() {
        super();
    }

    public User(long idUser, boolean s1member, boolean employee, String salutation, String firstName, String lastName,
                String title, Date birthdate, String email, String password, int points, int level, int checkins,
                Date lastCheckin, int pointsToday, String authToken) {
        super();
        this.idUser = idUser;
        this.s1member = s1member;
        this.employee = employee;
        this.salutation = salutation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.points = points;
        this.level = level;
        this.checkins = checkins;
        this.lastCheckin = lastCheckin;
        this.pointsToday = pointsToday;
        this.authToken = authToken;
    }


    public boolean isS1member() {
        return s1member;
    }

    public long getIdUser() {
        return idUser;
    }


    public boolean isEmployee() {
        return employee;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    public int getCheckins() {
        return checkins;
    }

    public Date getLastCheckin() {
        return lastCheckin;
    }

    public int getPointsToday() {
        return pointsToday;
    }

    public String authToken() {
        return authToken;
    }

    public void saveUser(User user) {
        try {
            User select = new Select().from(User.class).where("email = ?", user.getEmail()).executeSingle();
            if (select != null) {
                select.points = user.points;
                select.pointsToday = user.pointsToday;
                select.lastCheckin = user.lastCheckin;
                select.level = user.level;
                select.authToken = user.authToken;
                select.save();
                Log.d("UPDATE TRUE", select.email + " - " + select.authToken);
            } else {
                user.save();
                Log.d("UPDATE FALSE", "CREATED NEW USER email: " + user.email + " - " + select.authToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
