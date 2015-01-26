package com.example.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.activeandroid.query.Select;
import com.example.localdata.User;
import com.example.seierfriendapp.LoginActivity;
import com.example.seierfriendapp.R;
import com.example.services.DataCollectedListener;
import com.example.services.JsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class PointStatusFragment extends Fragment {

    //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences();
   // String auth_token = settings.getString("authToken", "");

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

        User u = new Select().from(User.class).where("authToken == ?", LoginActivity.authToken).executeSingle();

        TextView points = ((TextView) getView().findViewById(R.id.txtCurrentPoints));
        points.setText(String.valueOf(u.getPoints()));

        TextView name = ((TextView) getView().findViewById(R.id.txtFriendName));
        name.setText(u.getFirstName() + " " + u.getLastName());

        TextView friendStatus=((TextView) getView().findViewById(R.id.txtFriendStatus));
        if(u.getPoints()>0 && u.getPoints()<800)
            friendStatus.setText(getResources().getString(R.string.friendStatus1));
        if(u.getPoints()>800 && u.getPoints()<1200)
            friendStatus.setText(getResources().getString(R.string.friendStatus2));
        if(u.getPoints()>1200 )
            friendStatus.setText(getResources().getString(R.string.friendStatus3));
    }
}
