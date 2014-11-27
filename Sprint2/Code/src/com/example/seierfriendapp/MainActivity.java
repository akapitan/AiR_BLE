package com.example.seierfriendapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datalayer.*;
import com.example.seierfriendapp.R.layout;


public class MainActivity extends Activity {

	Button btnSignIn;
	Button btnRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*
		 * JUST FOR TESTING PURPOSES, 
		 * remove before deployment
		 */
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		/**/
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#444444")));
		bar.hide();
		
		setContentView(R.layout.login);

		btnSignIn = (Button) findViewById(R.id.btnSignIn);
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						PointStatus.class);
				EditText username = (EditText)findViewById(R.id.editText1);
				EditText password = (EditText)findViewById(R.id.editText2);
				
				String url = "http://sfapp.viktor-lazar.com/api/v1/auth/" + username.getText().toString()
						+ "/"+password.getText().toString();
				String result = HttpClientService.getResponseFromUrl(url);
				
				/*
				 * For testing purposes only
				 * 
				 */
				if(result != ""){
					try {
						JSONObject json = new JSONObject(result);
						String b = "Welcome "+json.getJSONArray("user").getJSONObject(0).getString("name").toString()
								+" "+json.getJSONArray("user").getJSONObject(0).getString("lastname").toString();
						
						Toast.makeText(getApplicationContext(), b, Toast.LENGTH_LONG).show();
						startActivity(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
					}
					
				}
				
			}
		});

	}

}
