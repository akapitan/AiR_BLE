package com.example.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matha on 29.11.14..
 */
public class JsonParser {

    private DataCollectedListener dataCollectedListener;
    static InputStream inputStream = null;
    static JSONObject jsonObject = null;
    static String json = "";

    boolean gettingJsonStatus = false;
    boolean errors = false;
    String errorMessage = null;
    Context ctx = null;

    public JsonParser(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Method that initiates call to web service.
     * requestParameters is object array that contains data needed to make a call:
     * requestParameters[0] - Url
     * requestParameters[1] - method {"get","post"}
     * requestParameters[2] - NameValuePairs that contains aditional header info
     * @param requestParameters
     */
    public void getData(Object requestParameters[])
    {
        WebServiceDataCollection wsdc = new WebServiceDataCollection();
        Log.e("Prvi", requestParameters[0].toString());
        Log.e("Drugi", requestParameters[1].toString());

        wsdc.execute(requestParameters);
    }



    /**
     * Getter for Data from web service.
     * @return
     */
    public JSONObject getJson(){
        return jsonObject;
    }

    /**
     * DataCollectedListener setter
     * @param dataCollectedListener
     */
    public void setDataCollectedListener(DataCollectedListener dataCollectedListener)
    {
        this.dataCollectedListener = dataCollectedListener;
    }

    /**
     * Object is really an array
     */
    private class WebServiceDataCollection extends AsyncTask<Object,Void,Void>
    {

        @Override
        protected Void doInBackground(Object... params) {
            //nonsense remove later
            ArrayList<NameValuePair> parameters = null;

           try {
               //check the method, second parameter of @paramsž
               if(params[1].toString() == "GET" || params[1].toString() == "get"){

                   HttpClient httpClient = new DefaultHttpClient();
                   HttpGet httpGet = new HttpGet(params[0].toString());
                    // add parameters (if exist) in request header
                   if(params[2] != null){
                       ArrayList<NameValuePair> headerList = (ArrayList<NameValuePair>) params[2];
                       for(int i = 0; i < headerList.size(); i++){
                           //add to header name and value arguments
                           httpGet.addHeader(headerList.get(i).getName(), headerList.get(i).getValue());
                       }
                   }

                   HttpResponse httpResponse = httpClient.execute(httpGet);
                   HttpEntity httpEntity = httpResponse.getEntity();

                   inputStream = httpEntity.getContent();


               }else if(params[1].toString() == "POST" || params[1].toString() == "post"){

                   HttpClient httpClient = new DefaultHttpClient();
                   HttpPost httpPost = new HttpPost(params[0].toString());

                   List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                   nameValuePairs.add(new BasicNameValuePair("method", params[1].toString()));
                   httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")); /////////

                   HttpResponse httpResponse = httpClient.execute(httpPost);
                   HttpEntity httpEntity = httpResponse.getEntity();
                   Log.d("Before post input stream","Lol");
                   inputStream = httpEntity.getContent();
               }
           } catch (ClientProtocolException e) {
               Log.d("Client protocol exception",e.toString());
               e.printStackTrace();
               errors = true;
               errorMessage = e.getLocalizedMessage();
           } catch (IOException e) {
               Log.d("IO exception",e.toString());
               e.printStackTrace();
               errorMessage = e.getLocalizedMessage();
               errors = true;
           } catch (Exception e){
               Log.d("Connection error", e.getLocalizedMessage());
               errors = true;
               errorMessage = e.getLocalizedMessage();
           }

           //read what have you got
            try{

                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"),8);
                StringBuilder stringBuilder = new StringBuilder();
                String ln = null;
                while((ln = br.readLine()) != null){
                    stringBuilder.append(ln + "\n");
                }

                inputStream.close();
                json = stringBuilder.toString();

            } catch (UnsupportedEncodingException e) {
                Log.d("Encoding exception", e.getLocalizedMessage());
                e.printStackTrace();
                errors = true;
                errorMessage = e.getLocalizedMessage();
            } catch (Exception e){
                Log.d("Something is wrong with dem buffer", e.getLocalizedMessage());
                e.printStackTrace();
                errors = true;
                errorMessage = e.getLocalizedMessage();
            }

            //try to put it into json object
            try{
                jsonObject = new JSONObject(json);
                gettingJsonStatus = true;

            } catch (JSONException e) {
                Log.e("JSON parsing", e.toString());
                e.printStackTrace();
                errors = true;
                errorMessage = e.getLocalizedMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(gettingJsonStatus){
                dataCollectedListener.DataCollected(true, errors, errorMessage);
            } else{
              dataCollectedListener.DataCollected(false, errors, errorMessage);
              //  Toast.makeText(ctx, "Something went wrong :(", Toast.LENGTH_LONG).show();
            }
        }
    }
}
