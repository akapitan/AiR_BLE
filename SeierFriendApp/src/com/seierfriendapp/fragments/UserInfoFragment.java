package com.seierfriendapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.activeandroid.query.Select;
import hr.foi.seierfriendapp.R;
import com.seierfriendapp.localdata.User;
import com.seierfriendapp.seierfriendapp.LoginActivity;

public class UserInfoFragment extends Fragment {


    public UserInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
