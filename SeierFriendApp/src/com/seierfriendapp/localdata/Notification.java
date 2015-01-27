package com.seierfriendapp.localdata;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by goran on 9.1.2015..
 */

@Table(name = "Notification")
public class Notification extends Model {

    @Column(name = "idNotification")
    private long idNotification;
    @Column(name = "deviceId")
    private String deviceId;
    @Column(name = "checkedIn")
    private boolean checkedIn;
    @Column(name = "dateNotification")
    private Date dateNotification;

    public Notification() {
        super();
    }

    public Notification(long idNotification, boolean checkedIn, Date dateNotification) {
        super();
        this.idNotification = idNotification;
        this.checkedIn = checkedIn;
        this.dateNotification = dateNotification;
    }


    public long getIdNotification() {
        return idNotification;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public Date getDateNotification() {
        return dateNotification;
    }

    public void setIdNotification(long idNotification) {
        this.idNotification = idNotification;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }
}

