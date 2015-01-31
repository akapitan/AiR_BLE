package com.seierfriendapp.localdata;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Beacon")
public class Beacon extends Model {
    @Column(name = "idBeacon")
    private long idBeacon;
    @Column(name = "mac")
    private String mac;

    public Beacon() {
        super();
    }

    public Beacon(long idBeacon, String mac) {
        super();
        this.idBeacon = idBeacon;
        this.mac = mac;
    }


    public long getIdBeacon() {
        return idBeacon;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setIdBeacon(long idBeacon) {
        this.idBeacon = idBeacon;
    }

    public List<Beacon> getMacAddresses() {
        return new Select().from(Beacon.class).execute();
    }
}
