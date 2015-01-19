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
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.activeandroid.ActiveAndroid;
import com.example.core.DbDataSaver;
import com.example.fragments.LoginFragment;
import com.example.fragments.TagIdDialogFragment;
import com.example.localdata.Login;
import com.example.services.DataCollectedListener;
import com.example.services.JsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends FragmentActivity implements DataCollectedListener {

    JsonParser jsonParser;
    Button btnSignIn;
    Button btnRegister;
    String authToken = "1b38c7e6-78c2-4eaf-8810-eaf1f808133e";
    String url, userLogin, passLogin;
    ArrayList<NameValuePair> header, postParams;
    Object[] jsonParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize active android
        ActiveAndroid.initialize(this);
        //remove action bar
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        bar.hide();
        setContentView(R.layout.login_layout);

        //json parser init
        jsonParser = new JsonParser(getApplicationContext());
        jsonParser.setDataCollectedListener(this);

        LoginFragment firstFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).addToBackStack("").commit();

        //username and password text field
        final EditText username = (EditText) findViewById(R.id.editText1);
        final EditText password = (EditText) findViewById(R.id.editText2);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        //button disabled before creds are entered
        btnSignIn.setEnabled(false);
        btnSignIn.setBackgroundColor(0xFFAAAAAA);

        //Text watcher, for button disable if username & pass is empty
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if username and password empty disable button
                if(!(username.getText().toString().isEmpty() || password.getText().toString().isEmpty())){
                    btnSignIn.setEnabled(true);
                    btnSignIn.setBackgroundColor(0xFFE25A10);
                }else{
                    btnSignIn.setBackgroundColor(0xFFAAAAAA);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        };
        //set listeners
        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        //Check if started from notification
        try {
            if (getIntent().getExtras().getString("checkIn") != null && getIntent().getExtras().getString("checkIn").equals("checkIn")) {
                //TO DO: start checkin!

                Log.e("STARTED FROM INTENT!",getIntent().getExtras().getString("checkIn").toString());
            }
        }catch (Exception e){
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLogin = username.getText().toString();
                passLogin = password.getText().toString();
                //saveLogin(TagIdDialogFragment.TAGID, userLogin, false);

                // get data from server
                getUserDataFromServer(userLogin, passLogin);
                //check if users exists. TAG-ID!
                checkIfUserExists(userLogin, passLogin);
            }
        });

        //registration - open url in browser
        btnRegister = (Button) findViewById(R.id.btnRegister);
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

    /**
     * Listener triggered when data is collected from web.
     * @param dataIsCollected - did we get the data
     * @param errors - were there any errors
     * @param errorMessage - if there were, what was it
     */
    @Override
    public void DataCollected(boolean dataIsCollected, boolean errors, String errorMessage) {
        if (dataIsCollected) {
            //get data from JsonParser class
            JSONObject jo = jsonParser.getJson();
            try {
                Toast.makeText(this, "MyActivity, data collected \n" +
                        "Name: " + jo.getString("id"), Toast.LENGTH_SHORT).show();
                //Save authorization to shared preferences
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("authToken", authToken);
                editor.commit();
                //save login to local db. Used for starting "input Tag ID" dialog in login
                DbDataSaver dbSaver = new DbDataSaver();
                dbSaver.saveUserData(jo, passLogin, authToken);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "MyActivity, data is not collected because \n" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "MyActivity, data is not collected because:\n\n" + errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
    public void getUserDataFromServer(String username, String password){

        /*url = String.format(res.getString(R.string.loginURI));
                header = new ArrayList<NameValuePair>();
                header.add(new BasicNameValuePair("Authorization", "Basic d2Vic2l0ZTo3ejJFRVdDNA=="));
                postParams = new ArrayList<NameValuePair>();
                postParams.add(new BasicNameValuePair("email", userLogin));
                postParams.add(new BasicNameValuePair("password", passLogin));
                jsonParameters = new Object[]{
                        Uri.parse(url),
                        "post",
                        header,
                        postParams
                };

                jsonParser.getData(jsonParameters);*/

        //get user data from server
        //AUTHTOKEN CURRENTLY HARDCODED, LOGIN API DOESN'T WORK! CHANGE LATER!!!
        Resources res = getResources();
        //get url from resources
        url = String.format(res.getString(R.string.wsURI));

        //create heaader for web api. Authorization + authtoken
        header = new ArrayList<NameValuePair>();
        header.add(new BasicNameValuePair("Authorization", authToken));
        jsonParameters = new Object[]{
                Uri.parse(url),
                "get",
                header
        };

        jsonParser.getData(jsonParameters);
    }

    /**
     * Method checks if user that wants to login exists, if it doesn't, dialog shows up.
     * @param username entered username
     * @param password entered password
     */
    public void checkIfUserExists(String username, String password){
        /*
        * To Do:
        * chechk if username and password exists in local database
        * if not start "input Tag ID dialog"
        * and create listener for input result of that dialog
        * */

         // first login - dialog for entering tag-id
        DialogFragment dialog = new TagIdDialogFragment();
        dialog.show(getSupportFragmentManager(), "TagIdDialogFragment");
    }

    /**
     * Save successful login. Used later for auto login.
     * @param tagId Tag id from dialog input
     * @param email Email from input text
     * @param password Password from input text
     * @param loggedIn is user logged in
     */
    private void saveLogin(String tagId, String email, String password, boolean loggedIn ){
        Login login=new Login();
        login.setEmail(email);
        login.setTAGID(tagId);
        login.setLoggedIn(loggedIn);
        login.save();
    }

}
