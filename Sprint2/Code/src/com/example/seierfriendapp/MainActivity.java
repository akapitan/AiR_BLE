package com.example.seierfriendapp;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.fragments.LoginFragment;
import com.example.services.DataCollectedListener;
import com.example.services.JsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements DataCollectedListener{

    JsonParser jsonParser;
    Button btnSignIn;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#444444")));
        bar.hide();
        setContentView(R.layout.login_layout);

        jsonParser = new JsonParser(getApplicationContext());
        jsonParser.setDataCollectedListener(this);

        LoginFragment firstFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Resources res = getResources();
                String url = String.format(res.getString(R.string.wsURI));

                ArrayList<NameValuePair> header = new ArrayList<NameValuePair>();
                header.add(new BasicNameValuePair("Authorization", "9a11e2b3-6ad8-4938-8219-74215228de98"));
                Object jsonParameters[] = new Object[]{
                        Uri.parse(url),
                        "get",
                        header
                };

                jsonParser.getData(jsonParameters);

                /*Intent i = new Intent(getApplication(), PointStatusActivity.class);
                startActivity(i);*/
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
    }

    @Override
    public void DataCollected(boolean dataIsCollected, boolean errors, String errorMessage) {
        if(dataIsCollected){
            JSONObject jo = jsonParser.getJson();
            try {
                Toast.makeText(this, "MyActivity, data collected \n" +
                        "Name: " + jo.getString("first_name").toString(), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "MyActivity, data is not collected because \n"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "MyActivity, data is not collected because:\n\n"+errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
