package com.example.seierfriendapp;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.activeandroid.ActiveAndroid;
import com.example.core.DbDataSaver;
import com.example.fragments.LoginFragment;
import com.example.services.BLEScan;
import com.example.services.DataCollectedListener;
import com.example.services.JsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends FragmentActivity implements DataCollectedListener{

    JsonParser jsonParser;
    Button btnSignIn;
    Button btnRegister;
    String authToken = "d68d25b7-2f8f-4b0f-b848-66a8762d93b8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        bar.hide();
        setContentView(R.layout.login_layout);

        jsonParser = new JsonParser(getApplicationContext());
        jsonParser.setDataCollectedListener(this);

        LoginFragment firstFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).addToBackStack("").commit();

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Resources res = getResources();
                String url = String.format(res.getString(R.string.wsURI));

                ArrayList<NameValuePair> header = new ArrayList<NameValuePair>();
                header.add(new BasicNameValuePair("Authorization", authToken));
                Object jsonParameters[] = new Object[]{
                        Uri.parse(url),
                        "get",
                        header
                };

                jsonParser.getData(jsonParameters);

                Intent i = new Intent(getApplication(), MainActivity.class);
                startActivity(i);
            }
        });

       btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                String url = String.format(res.getString(R.string.uriRegister));
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });

        ActiveAndroid.initialize(this);
    }

    @Override
    public void DataCollected(boolean dataIsCollected, boolean errors, String errorMessage) {
        if(dataIsCollected){
            JSONObject jo = jsonParser.getJson();
            try {
                Toast.makeText(this, "MyActivity, data collected \n" +
                        "Name: " + jo.getString("id"), Toast.LENGTH_SHORT).show();
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("authToken", authToken);
                        editor.commit();
                        DbDataSaver dbSaver = new DbDataSaver();
                        dbSaver.saveUserData(jo, authToken);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "MyActivity, data is not collected because \n"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "MyActivity, data is not collected because:\n\n"+errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
