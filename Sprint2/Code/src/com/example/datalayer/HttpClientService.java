package com.example.datalayer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class HttpClientService {

	public HttpClientService(){
		
	}
	
	public static String getResponseFromUrl(String url) {
		String resString = "";
		try{	
			HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
			HttpGet httpget = new HttpGet(url); // Set the action you want to do
			HttpResponse response = httpclient.execute(httpget); // Executeit
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent(); // Create an InputStream with the
													// response
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,
					"UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line);
	
			resString = sb.toString();
	
			is.close();
			
		}catch(Exception e){
			e.printStackTrace();
			resString = "";
		}
		return resString;
	}
	
}
