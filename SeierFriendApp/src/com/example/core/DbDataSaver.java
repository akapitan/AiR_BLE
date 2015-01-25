package com.example.core;

import android.util.Log;
import com.example.localdata.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.services.JsonParser.ConvertToDate;

/**
 * Created by vlazar on 2.12.2014..
 */
public class DbDataSaver {

    public DbDataSaver() {
    }

    /**
     * Save user credentials to local database. If user data exists,
     * update changes
     */
    public void saveUserData(JSONObject jo, String pass, String authTokenn)
    {
        try {
            long idUser = Long.parseLong(jo.getString("id"));
            boolean s1member = Boolean.parseBoolean(jo.getString("s1member"));
            boolean employee = Boolean.parseBoolean(jo.getString("employee"));
            String salutation = jo.getString("salutation").toString();
            String firstName = jo.getString("first_name").toString();
            String lastName = jo.getString("last_name").toString();
            String title = jo.getString("title").toString();
            String email = jo.getString("email").toString();
            String password = pass;
            String authToken=authTokenn;
            int points = Integer.parseInt(jo.getString("points").toString());
            int pointsToday = Integer.parseInt(jo.getString("points_today"));
            Date birthdate = ConvertToDate(jo.getString("birthdate"));
            int level = Integer.parseInt(jo.getString("level"));
            int checkins = Integer.parseInt(jo.getString("checkins"));

            //Save timestamp of last checkin
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lastCheckin = new Date();

            /*password;*/

            User user;
            user = new User(idUser, s1member, employee, salutation, firstName, lastName, title, birthdate, email,
                    password, points, level, checkins, lastCheckin, pointsToday, authToken);
            //user.save();
            user.saveUser(user);
            Log.e("DB-SAVED", user + " - " + user.authToken().toString());
        } catch (JSONException e){
            e.printStackTrace();
            Log.e("DB-NOTSAVED", "not saved");
        }
    }

    public void saveVoucherData(JSONObject jo)
    {

    }

}
