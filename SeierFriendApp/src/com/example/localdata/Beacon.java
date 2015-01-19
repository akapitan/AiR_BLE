package com.example.localdata;

import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;

import java.util.List;

/**
 * Created by Viktor on 08/01/2015.
 */
@Table(name="Beacon")
public class Beacon extends Model {
    @Column(name = "idBeacon")
    private long idBeacon;
    @Column(name = "mac")
    private String mac;

    public Beacon() { super(); }

    public Beacon(long idBeacon, String mac)
    {
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

    public List<Beacon> getMacAdresses(){
        return  new Select().from(Beacon.class).execute();
    }
}
