package com.example.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.activeandroid.query.Select;
import com.example.localdata.User;
import com.example.seierfriendapp.LoginActivity;
import com.example.seierfriendapp.R;

public class UserInfoFragment extends Fragment {

    /*
    *  TO DO read authToken from Shared Preferences
    */
	
	public UserInfoFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);
         
        return rootView;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //select user from db
        //User u = new Select().from(User.class).where("authToken == ?", authToken).executeSingle();

        User u = new Select().from(User.class).where("authToken == ?", LoginActivity.authToken).executeSingle();
        //set user data
        TextView firstName = ((TextView) getView().findViewById(R.id.txtFirstName));
        firstName.setText(u.getFirstName());

        TextView lastName = ((TextView) getView().findViewById(R.id.txtLastName));
        lastName.setText(u.getLastName());

        TextView email = ((TextView) getView().findViewById(R.id.txtEmail));
        email.setText(u.getEmail());

        TextView points = ((TextView) getView().findViewById(R.id.txtPoints));
        points.setText(String.valueOf(u.getPoints()));

        TextView lastCheckin = ((TextView) getView().findViewById(R.id.txtLastCheckin));
        lastCheckin.setText(String.valueOf(u.getLastCheckin()));
    }
}
