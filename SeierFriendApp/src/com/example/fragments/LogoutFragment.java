package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.activeandroid.query.Select;
import com.example.core.BaseApplication;
import com.example.localdata.Login;
import com.example.seierfriendapp.LoginActivity;
import com.example.seierfriendapp.R;

/**
 * Created by matha on 25.01.15..
 */
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
    public void userLogout(){
        //Select from db. Check if there is logged in = true (Existing user logged in)
        try {
            Login loggedUser = new Select().from(Login.class).where("loggedIn == ?", true).executeSingle();
            Log.e("LoggedUser", loggedUser.getEmail() + " " + loggedUser.getPassword());
            Login userLogin = loggedUser;
            userLogin.setEmail(loggedUser.getEmail());
            userLogin.setPassword(loggedUser.getPassword());
            userLogin.setAuthorization_token(loggedUser.getAuthorization_token());
            userLogin.setParticipant_id(loggedUser.getParticipant_id());
            userLogin.setLoggedIn(false);
            userLogin.save();
            Log.e("User Logout","Successful");
            loggedUser = null;
            loggedUser = new Select().from(Login.class).where("loggedIn == ?", true).executeSingle();
            Log.e("LoggedUser11111111111111111", loggedUser.getEmail() + " " + loggedUser.getPassword());

        }catch(Exception ex){
            //no existing user show username and password
            Log.e("CheckIfUserExists","User does not exists!");
            ex.printStackTrace();

        }
    }
}
