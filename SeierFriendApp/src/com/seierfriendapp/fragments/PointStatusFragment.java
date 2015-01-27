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

public class PointStatusFragment extends Fragment {

    //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences();
    // String auth_token = settings.getString("authToken", "");

    public PointStatusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_point_status, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User u = new Select().from(User.class).where("authToken == ?", LoginActivity.authToken).executeSingle();

        try {
            TextView points = ((TextView) getView().findViewById(R.id.txtCurrentPoints));
            points.setText(String.valueOf(u.getPoints()));

            TextView name = ((TextView) getView().findViewById(R.id.txtFriendName));
            name.setText(u.getFirstName() + " " + u.getLastName());

            TextView friendStatus = ((TextView) getView().findViewById(R.id.txtFriendStatus));
            if (u.getPoints() > 0 && u.getPoints() < 800)
                friendStatus.setText(getResources().getString(R.string.friendStatus1));
            if (u.getPoints() > 800 && u.getPoints() < 1200)
                friendStatus.setText(getResources().getString(R.string.friendStatus2));
            if (u.getPoints() > 1200)
                friendStatus.setText(getResources().getString(R.string.friendStatus3));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}