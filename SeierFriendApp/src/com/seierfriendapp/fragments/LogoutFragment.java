package com.seierfriendapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.activeandroid.query.Select;
import com.seierfriendapp.BaseApplication;
import com.seierfriendapp.localdata.Login;
import com.seierfriendapp.seierfriendapp.LoginActivity;

public class LogoutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userLogout();
        Intent dialogIntent = new Intent(BaseApplication.getStaticCurrentContext(), LoginActivity.class);
        //extras for check in recognition, used in loginActivity
        dialogIntent.putExtra("logout", "logout");
        startActivity(dialogIntent);
        return null;
    }

    /**
     * Update user/set logged in to false
     */
    public void userLogout() {

        try {
            Login loggedUser = new Select().from(Login.class).where("loggedIn == ?", true).executeSingle();
            Log.e("LoggedUser", loggedUser.getEmail() + " " + loggedUser.getPassword());
            Login userLogin = loggedUser;

            userLogin.setLoggedIn(false);
            userLogin.save();
            Log.e("User Logout", "Successful");
            loggedUser = null;
            loggedUser = new Select().from(Login.class).where("loggedIn == ?", true).executeSingle();

        } catch (Exception ex) {
            //no existing user show username and password
            Log.e("CheckIfUserExists", "User does not exist!");
            ex.printStackTrace();

        }
    }
}
