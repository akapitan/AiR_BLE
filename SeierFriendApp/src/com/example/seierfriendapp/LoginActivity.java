package com.example.seierfriendapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.core.DbDataSaver;
import com.example.fragments.LoginFragment;
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
    public static String authToken;
    String url, userLogin, passLogin;
    ArrayList<NameValuePair> header, postParams;
    Object[] jsonParameters;
    boolean login = true;
    //textfield
    EditText username;
    EditText password;
    //progress dialog
    CustomDialog progressDialog;
    Resources res;
    String tagId;
    boolean startedFromNotification;
    Login loggedUser;


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
        //get resources
        res = getResources();

        //username and password text field
        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        //button disabled before creds are entered
        btnSignIn.setEnabled(false);
        btnSignIn.setBackgroundColor(0xFFAAAAAA);

        //Text watcher, for button disable if username & pass is empty
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if username and password empty disable button
                if (!(username.getText().toString().isEmpty() || password.getText().toString().isEmpty())) {
                    btnSignIn.setEnabled(true);
                    btnSignIn.setBackgroundColor(0xFFE25A10);
                } else {
                    btnSignIn.setBackgroundColor(0xFFAAAAAA);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        //set listeners
        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        //Check if started from notification
        try {
            if (getIntent().getExtras().getString("checkIn") != null && getIntent().getExtras().getString("checkIn").equals("checkIn")) {
                if (checkIfUserIsLoggedIn()) {
                    startedFromNotification = true;
                    Log.e("STARTED FROM INTENT!", getIntent().getExtras().getString("checkIn").toString());
                    try {
                        Login TagID = new Select().from(Login.class).where("email == ?", loggedUser.getEmail()).executeSingle();
                        Log.e("ENTERED TAGID ", TagID.getTag_id().toString());
                        //userCheckIn(TagID.getTag_id().toString()); //From database
                        userCheckIn("b5f21f01-6b09-11e4-9def-005056a11f87");
                    } catch (Exception e) {
                        Toast.makeText(this, "Please enter valid TagId " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Please login.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Log.e("Not started from intent", "");
        }

        /// test if user is logged in
        progressDialog = new CustomDialog(this);
        progressDialog.setTitle(res.getString(R.string.loginProgressDialogTitle));
        progressDialog.setMessage(res.getString(R.string.loginProgressDialogMessage));
        progressDialog.show();
        // check if started from logout
        try {
            if (getIntent().getExtras().getString("logout") != null && getIntent().getExtras().getString("logout").equals("logout")) {
                Log.e("Started from logout", getIntent().getExtras().getString("logout").toString());
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            if (checkIfUserIsLoggedIn()) {
                this.username.setText(loggedUser.getEmail());
                this.password.setText(loggedUser.getPassword());
                getUserLoginDataFromServer(loggedUser.getEmail(), loggedUser.getPassword());
            }
        }

        if (checkIfUserIsLoggedIn()) {
            this.username.setText(loggedUser.getEmail());
            this.password.setText(loggedUser.getPassword());
            getUserLoginDataFromServer(loggedUser.getEmail(), loggedUser.getPassword());
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLogin = username.getText().toString();
                passLogin = password.getText().toString();
                progressDialog.show();

                //if there is no user/tag of user
                tagId = checkForTagId(userLogin);
                if (tagId == null) {
                    //showTagIdDialogFragment();
                    alertDialog(getResources().getString(R.string.enter_tag_id));
                } else {
                    // get data from server
                    getUserLoginDataFromServer(userLogin, passLogin);
                }
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
     *
     * @param dataIsCollected - did we get the data
     * @param errors          - were there any errors
     * @param errorMessage    - if there were, what was it
     */
    @Override
    public void DataCollected(boolean dataIsCollected, boolean errors, String errorMessage) {
        if (dataIsCollected) {
            //get data from JsonParser class
            JSONObject jo = jsonParser.getJson();
            Log.e("recieved data", jo.toString());

            try {
                if (login == true) {
                    startedFromNotification = false;
                    saveLogin(jo.getString("authorization_token").toString(), jo.getString("participant_id").toString(), userLogin, passLogin, true, tagId);
                    authToken = jo.getString("authorization_token").toString();
                    Log.e("AUTH TOKEN LOGIN -> ", authToken);
                    getUserStatusDataFromServer(authToken);

                } else if (startedFromNotification == true) {
                    startedFromNotification = false;
                    progressDialog.dismiss();
                    login = false;
                    getUserStatusDataFromServer(authToken);
                    //startMainActivity();
                } else {
                    startedFromNotification = false;
                    DbDataSaver dbSaver = new DbDataSaver();
                    dbSaver.saveUserData(jo, passLogin, authToken);
                    //everything ok. Start main act.
                    progressDialog.dismiss();
                    startMainActivity();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.wrongUsernameAndPass), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    public void getUserLoginDataFromServer(String username, String password) {
        Resources res = getResources();
        url = String.format(res.getString(R.string.loginURI));
        header = new ArrayList<NameValuePair>();
        header.add(new BasicNameValuePair("Authorization", "Basic d2Vic2l0ZTo3ejJFRVdDNA=="));
        postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("email", username));
        postParams.add(new BasicNameValuePair("password", password));
        postParams.add(new BasicNameValuePair("postType", "login"));
        jsonParameters = new Object[]{
                Uri.parse(url),
                "post",
                header,
                postParams,
                null
        };
        jsonParser.getData(jsonParameters);
    }

    public void getUserStatusDataFromServer(String athorization_token) {
        login = false;
        Resources res = getResources();
        //get url from resources
        url = String.format(res.getString(R.string.wsURI));
        header = new ArrayList<NameValuePair>();
        header.add(new BasicNameValuePair("Authorization", athorization_token));
        jsonParameters = new Object[]{
                Uri.parse(url),
                "get",
                header,
                null,
                null
        };
        jsonParser.getData(jsonParameters);
    }

    public void userCheckIn(String TagId) {
        Resources res = getResources();
        url = String.format(res.getString(R.string.checkInURI) + TagId);
        jsonParameters = new Object[]{
                Uri.parse(url),
                "post",
                null,
                null,
                null
        };
        jsonParser.getData(jsonParameters);
    }

    /**
     * Save successful login. Used later for auto login.
     *
     * @param athorization_token Tag id from dialog input
     * @param email              Email from input text
     * @param password           Password from input text
     * @param loggedIn           is user logged in
     */
    private void saveLogin(String athorization_token, String participant_id, String email, String password, boolean loggedIn, String tagId) {
        Login login;
        try {
            Login loggedUser = new Select().from(Login.class).where("email == ?", email).executeSingle();
            Log.e("LoggedUser", loggedUser.getEmail() + " " + loggedUser.getPassword());
            if (loggedUser.getEmail() == null) {
                login = new Login();
            } else {
                login = loggedUser;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            login = new Login();
        }

        login.setAuthorization_token(athorization_token);
        login.setParticipant_id(participant_id);
        login.setEmail(email);
        login.setPassword(password);
        login.setLoggedIn(loggedIn);
        login.setTag_id(tagId);
        Log.e("SaveLogin", "User saved with loggedIn = " + athorization_token + " " + participant_id + " " + email + " " + password + " " + loggedIn);
        login.save();
    }

    /**
     * Method that checks if user is logged in.
     */
    public boolean checkIfUserIsLoggedIn() {
        //Select from db. Check if there is logged in = true (Existing user logged in)
        try {
            loggedUser = new Select().from(Login.class).where("loggedIn == ?", true).executeSingle();
            Log.e("LoggedUser", loggedUser.getEmail() + " " + loggedUser.getPassword() + " TAGID: " + loggedUser.getTag_id());
            if (loggedUser.getEmail() == null || loggedUser.getPassword() == null) {
                progressDialog.dismiss();
                return false;

            } else {
                return true;
            }

        } catch (Exception ex) {
            //no existing user show username and password
            Log.e("CheckIfUserExists", "User does not exists!");
            progressDialog.dismiss();
            return false;
        }
    }

    /**
     * Method that starts main activity
     */
    public void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    /**
     * Checks if user exists and returns his tagID
     *
     * @return
     */
    public String checkForTagId(String email) {
        String tagId = "";
        try {
            Login user = new Select().from(Login.class).where("email == ?", email).executeSingle();
            if (user.getEmail() != null) {
                tagId = user.getTag_id();
            } else {
                tagId = null;
            }
        } catch (Exception ex) {
            tagId = null;
        }
        return tagId;
    }

    public void alertDialog(String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setMessage(title);
        final EditText etTagId = new EditText(LoginActivity.this);
        etTagId.setInputType(InputType.TYPE_CLASS_TEXT);
        etTagId.setHint(getResources().getString(R.string.enter_tag_id));
        alert.setView(etTagId);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("Entered TagID", etTagId.getText().toString());
                tagId = etTagId.getText().toString();
                getUserLoginDataFromServer(userLogin, passLogin);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
            }
        });
        alert.show();
    }

    /*
    * Custom dialog
    * */

    private static final class CustomDialog extends ProgressDialog {

        private CustomDialog(Context context) {
            super(context);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final Resources res = getContext().getResources();
            final int id = res.getIdentifier("titleDivider", "id", "android");
            final View titleDivider = findViewById(id);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(res.getColor(R.color.list_background_pressed));
            }
            final int id1 = res.getIdentifier("alertTitle", "id", "android");
            final TextView title = (TextView) this.getWindow().getDecorView().findViewById(id1);
            if (title != null) {
                title.setTextColor(res.getColor(R.color.counter_text_bg));
            }
        }
    }
}
