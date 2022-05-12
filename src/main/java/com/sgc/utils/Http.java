package com.sgc.utils;


import java.io.BufferedReader;
import java.io.DataOutputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;



public class Http {
	// HTTP GET request
	public static String sendGet(String url) throws Exception {

		String USER_AGENT = "Mozilla/5.0";
	
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		int responseCode = con.getResponseCode();
		StringBuffer response = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response.toString();

	}

	public static String sendPost2(String url,String customerJson) throws Exception {
		try {
			URL obj = new URL(url);
			 System.out.println("==========sendPost2-->"+url);
			 StringBuilder postData = new StringBuilder();
//			    for (Map.Entry param : params.entrySet()) {
//			        if (postData.length() != 0) postData.append('&');
//			        postData.append(URLEncoder.encode(param.getKey().toString(), "UTF-8"));
//			        postData.append('=');
//			        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//			    }
			    postData.append("customer");
		        postData.append("=");
		        postData.append(customerJson);
			    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			    conn.setRequestMethod("POST");
			    //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		           conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			    conn.setDoOutput(true);
			   // conn.getOutputStream().write(postDataBytes);
			    OutputStream os = conn.getOutputStream();
		           os.write(postDataBytes);
		           os.close(); 
		           System.out.println("==========sendPost2====END===-->");
			    
		           Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		   	    StringBuilder sb = new StringBuilder();
		   	    for (int c; (c = in.read()) >= 0;)
		   	        sb.append((char)c);
		   	    String response = sb.toString();
		   	    System.out.println(response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		    
	      
		return null;
	}
	// HTTP POST request
	
	public static String sendPost(String url, String params) throws Exception {
		System.out.println("\n============Sending 'POST 2' request to URL : " + url);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-type", "text/html");
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + params);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("\n============Sending 'POST 2' END: " );
		return response.toString();
	}
}
