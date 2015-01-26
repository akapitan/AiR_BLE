package com.example.core;

import android.util.Log;
import com.example.localdata.User;
import com.example.localdata.Voucher;
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
            Log.e("DB-NOT_SAVED", "not saved");
        }
    }

    public void saveVoucherData(JSONObject jo)
    {
        try {
            long idVoucher = Long.parseLong(jo.getString("id"));
            String name = jo.getString("name").toString();
            int type = Integer.parseInt(jo.getString("type"));
            String description = jo.getString("description").toString();
            boolean redeemable = Boolean.parseBoolean(jo.getString("redeemable"));
            String imageUrl = jo.getString("image_url").toString();
            String voucherTitle = jo.getString("voucher_title").toString();
            String voucherTerms = jo.getString("voucher_terms").toString();
            String voucherLogoUrl = jo.getString("voucher_logo_url").toString();
            String voucherImageUrl = jo.getString("voucher_image_url").toString();
            int voucherEan = Integer.parseInt(jo.getString("voucher_ean"));
            String voucherStoreName = jo.getString("voucher_store_name").toString();
            String voucherStoreLocation = jo.getString("voucher_store_location").toString();
            String voucherValidUntil = jo.getString("voucher_valid_until").toString();
            String voucherDiscountStampUrl = jo.getString("voucher_discount_stamp_url").toString();
            int validFor = Integer.parseInt(jo.getString("valid_for"));
            Date validFrom = ConvertToDate(jo.getString("valid_from"));
            Date validUntil = ConvertToDate(jo.getString("valid_until"));
            boolean multipleRedeemable = Boolean.parseBoolean(jo.getString("multiple_redeemable"));
            int vestingPeriod = Integer.parseInt(jo.getString("vesting_period"));
            int nonRedeemableReason = Integer.parseInt(jo.getString("non_redeemable_reason"));



            Voucher voucher;
            voucher = new Voucher(idVoucher, type, name, description, redeemable, imageUrl, voucherTitle, voucherTerms,
                    voucherLogoUrl, voucherImageUrl, voucherEan, voucherStoreName, voucherStoreLocation, voucherValidUntil,
                    voucherDiscountStampUrl, validFor, validFrom, validUntil, multipleRedeemable, vestingPeriod, nonRedeemableReason);

            voucher.saveVoucher(voucher);
            Log.e("VOUCHER DB-SAVED", voucher + " - " + voucher.getName().toString());
        } catch (JSONException e){
            e.printStackTrace();
            Log.e("VOUCHER DB-NOT_SAVED", "not saved");
        }
    }

}
