package com.example.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.activeandroid.query.Select;
import com.example.localdata.User;
import com.example.seierfriendapp.R;

public class PointStatusFragment extends Fragment {

    //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences();
   // String auth_token = settings.getString("authToken", "");
    /*
    *  TO DO read authToken from Shared Preferences
    */
    String authToken = "1b38c7e6-78c2-4eaf-8810-eaf1f808133e";

	public PointStatusFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_point_status, container, false);
         
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User u = new Select().from(User.class).where("authToken == ?", authToken).executeSingle();

        TextView points = ((TextView) getView().findViewById(R.id.txtCurrentPoints));
        //points.setText(String.valueOf(u.getPoints()));

        TextView name = ((TextView) getView().findViewById(R.id.txtFriendName));
        //name.setText(u.getFirstName() + " " + u.getLastName());
    }
}
