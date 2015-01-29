package com.seierfriendapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.activeandroid.query.Select;
import com.pascalwelsch.holocircularprogressbar.HoloCircularProgressBar;
import hr.foi.seierfriendapp.R;
import com.seierfriendapp.localdata.User;
import com.seierfriendapp.seierfriendapp.LoginActivity;

public class PointStatusFragment extends Fragment {

    final int level1 = 800;
    final int level2 = 1200;
    final int level3 = 1200;

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
            if (u.getPoints() > 0 && u.getPoints() < level1) {
                friendStatus.setText(getResources().getString(R.string.friendStatus1));
                float circle = ((float) u.getPoints())/level1;
                HoloCircularProgressBar progressBar = (HoloCircularProgressBar)getView().findViewById(R.id.holoCircularProgressBar);
                Log.e("User points: ",String.valueOf(circle));
                progressBar.setProgress(circle);
                progressBar.setMarkerEnabled(false);

            }

            if (u.getPoints() > 800 && u.getPoints() < level2) {
                friendStatus.setText(getResources().getString(R.string.friendStatus2));
                float circle = ((float) u.getPoints())/level2;
                HoloCircularProgressBar progressBar = (HoloCircularProgressBar)getView().findViewById(R.id.holoCircularProgressBar);
                Log.e("User points: ",String.valueOf(circle));
                progressBar.setProgress(circle);
                progressBar.setMarkerEnabled(false);
            }
            if (u.getPoints() > level2) {
                friendStatus.setText(getResources().getString(R.string.friendStatus3));
                float circle = ((float) u.getPoints())/level3;
                HoloCircularProgressBar progressBar = (HoloCircularProgressBar)getView().findViewById(R.id.holoCircularProgressBar);
                Log.e("User points: ",String.valueOf(circle));
                progressBar.setProgress(circle);
                progressBar.setMarkerEnabled(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
