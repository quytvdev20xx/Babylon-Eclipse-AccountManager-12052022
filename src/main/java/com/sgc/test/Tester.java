package com.sgc.test;

import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.methods.HttpPost;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sgc.accountmanager.entities.ChangePasswordRequest;
import com.sgc.accountmanager.entities.Customer;
import com.sgc.accountmanager.entities.Message;
import com.sgc.utils.DateDeserializer;
import com.sgc.utils.Http;
import com.sgc.utils.MessageType;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Tester {
	public void validateToken(String url,String userName, String token) {
		try {
			
			url+="validateToken?userName="+URLEncoder.encode(userName,"utf-8")+"&token="+token;
			
			System.out.println( "##### validateToken+++++++++++++++++++"+url);
			if (token == null ) {
				System.out.println( "##### TOKEN ===NULLL+++++++++++++++++++");
				return;
			}
			
			String response = Http.sendGet(url);
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
			Gson gson = gsonBuilder.create();

			Customer responseValidateToken = gson.fromJson(response, Customer.class);
			if (responseValidateToken==null) {
				System.out.println( "Token không hợp lệ!!!!");
				return;
			}

			//session.setProviderClient(responseValidateToken.getData().getProviderId());
			
			System.out.println( "OKKKK->"+responseValidateToken.getUserName());
			//Player player = PlayerManager.getInstance().checkLogin(session, user);
//			if (player != null) {
//				doLoginSuccess(session, player);
//			} else {
//				MessageUtils.onValidateToken(session, false, "Người chơi không tồn tại");
//				return;
//			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}
	public String getToken(String url, String username) {
		try {
			url+="getToken?username="+URLEncoder.encode(username,"utf-8");
			System.out.println( "$$$$$$$$$$$$$$$$$  getToken+++++++++++++++++++"+url);
			if (username == null ) {
				
				return null;
			}
			
			
			String response = Http.sendGet(url);
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
			Gson gson = gsonBuilder.create();

			Customer user = gson.fromJson(response, Customer.class);
			if (user == null) {
				System.out.println( "Token không hợp lệ, vui lòng login lại");
				return null;
			}
			else {
				
				System.out.println( "Token="+ user.getToken());
				return user.getToken();

			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public String register(String url, Customer customer) {
		try {
			Gson gson = new Gson();    
		    String json = gson.toJson(customer);
		    
			url+="register?customer="+json;
			System.out.println( "$$$$$$$$$$$$$$$$$  register1+++++++++++++++++++"+url);
			
			
			String response = Http.sendGet(url);
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
			 gson = gsonBuilder.create();

			Customer user = gson.fromJson(response, Customer.class);
			if (user == null) {
				System.out.println( "Token không hợp lệ, vui lòng login lại");
				return null;
			}
			else {
				
				System.out.println( "Token="+ user.getToken());
				return user.getToken();

			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public String register2(String url, Customer customer) {
		try {
			
			Gson gson = new Gson();    
		    String json = gson.toJson(customer);
			
			
			url+="register";
			System.out.println( "$$$$$$$$$$$$$$$$$  register+++++++++++++++++++"+url);
			if (customer == null ) {
				
				return null;
			}
			
			//Map params = new LinkedHashMap<String, String>();
		    //params.put("customer", json);
		   
			String response = Http.sendPost2(url, json);
			
			
		
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
			gson = gsonBuilder.create();

			Customer user = gson.fromJson(response, Customer.class);
			if (user == null) {
				System.out.println( "Loi dang ky");
				return null;
			}
			else {
				
				System.out.println( "Token="+ user.getToken());
				return user.getToken();

			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private void sendRegisterPost()  {
		try {
			 HttpPost post = new HttpPost("http://localhost:8080/customer/register");
		     Customer customer =new Customer();
				customer.setUserName("daica13");
				customer.setIme("111111111111");
				customer.setPassword("a111111111111");
				customer.setProviderId((byte)100);
				customer.setServerID((byte)1);
				Gson gson = new Gson();    
			    String cusJson = gson.toJson(customer);
			    System.out.println("GSON gson.toJson(customer)=="+cusJson);
		     // add request parameter, form parameters
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		     
		     post.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		          CloseableHttpResponse response = httpClient.execute(post)) {
		    	
					GsonBuilder gsonBuilder = new GsonBuilder();
					gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
					gson = gsonBuilder.create();
					String resp=EntityUtils.toString(response.getEntity());
					System.out.println("=======sendPost==respose=>>>"+resp);
					Message message = gson.fromJson(resp, Message.class);
					System.out.println("From server:"+message.getMessage());
					if(message.getStatus()==MessageType.SUCCESS) {
						Customer cus=message.getData();//gson.fromJson(message.getData().toString(), Customer.class);
						System.out.println("From server cus.getUserID="+cus.getUserId() +" : "+cus.getUserName());
					}
		    	
		    	 
		         
		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	       

    }
		private Customer loginPost()  {
			try {
				 HttpPost post = new HttpPost("http://localhost:8080/customer/login");
			     Customer customer =new Customer();
					customer.setUserName("daica6");
					customer.setIme("111111111111");
					customer.setPassword("111111111111");
					customer.setProviderId((byte)100);
					customer.setServerID((byte)1);
					Gson gson = new Gson();    
				    String cusJson = gson.toJson(customer);
				    System.out.println("GSON gson.toJson(customer)=="+cusJson);
			     // add request parameter, form parameters
			     List<NameValuePair> urlParameters = new ArrayList<>();
			     urlParameters.add(new BasicNameValuePair("customer", cusJson));
			     
			     post.setEntity(new UrlEncodedFormEntity(urlParameters));
			
			     try (CloseableHttpClient httpClient = HttpClients.createDefault();
			          CloseableHttpResponse response = httpClient.execute(post)) {
			    	
						GsonBuilder gsonBuilder = new GsonBuilder();
						gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
						gson = gsonBuilder.create();
						String resp=EntityUtils.toString(response.getEntity());
						System.out.println("=======sendPost==respose=>>>"+resp);
						Message message = gson.fromJson(resp, Message.class);
						System.out.println("From server:"+message.getMessage());
						if(message.getStatus()==MessageType.SUCCESS) {
							Customer cus=message.getData();//gson.fromJson(message.getData().toString(), Customer.class);
							System.out.println("From server cus.getUserID="+cus.getUserId() +" : "+cus.getUserName());
						}
			    	
			    	 return message.getData();
			         
			     }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		       

	    }
		private Customer changePassword()  {
			try {
				 HttpPost post = new HttpPost("http://localhost:8080/customer/changePassword");
			     ChangePasswordRequest changepPasswordRequest =new ChangePasswordRequest();
					changepPasswordRequest.setUserName("daica6");
					changepPasswordRequest.setPassword("c1212121212121212");
					changepPasswordRequest.setOldPassWord("c1212121212121212");
					Gson gson = new Gson();    
				    String cusJson = gson.toJson(changepPasswordRequest);
				    System.out.println("GSON gson.toJson(changepPasswordRequest)=="+cusJson);
			     // add request parameter, form parameters
			     List<NameValuePair> urlParameters = new ArrayList<>();
			     urlParameters.add(new BasicNameValuePair("changepPasswordRequest", cusJson));
			     
			     post.setEntity(new UrlEncodedFormEntity(urlParameters));
			
			     try (CloseableHttpClient httpClient = HttpClients.createDefault();
			          CloseableHttpResponse response = httpClient.execute(post)) {
			    	
						GsonBuilder gsonBuilder = new GsonBuilder();
						gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
						gson = gsonBuilder.create();
						String resp=EntityUtils.toString(response.getEntity());
						System.out.println("=======sendPost==respose=>>>"+resp);
						Message message = gson.fromJson(resp, Message.class);
						System.out.println("From server:"+message.getMessage());
						if(message.getStatus()==MessageType.SUCCESS) {
							Customer cus=message.getData();//gson.fromJson(message.getData().toString(), Customer.class);
							System.out.println("From server cus.getUserID="+cus.getUserId() +" : "+cus.getUserName());
						}
			    	
			    	 return message.getData();
			         
			     }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		       

	    }
	public void checkRedis(String key) {
	  JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
			  
			  // Get the pool and use the database
			  try (Jedis jedis = jedisPool.getResource()) {
				 // jedis.auth("123456");
				  //jedis.set("mykey", "Hello from Jedis");
				  String value = jedis.get("customer::1");
				  System.out.println( value );
//				  Gson gson = new Gson();  
//				  GsonBuilder gsonBuilder = new GsonBuilder();
//				  gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
//				  gson = gsonBuilder.create();
//					
//				  Customer cus = gson.fromJson(value, Customer.class);
//				  System.out.println(  cus.getUserName());
//				  jedis.zadd("vehicles", 0, "car"); 
//				  jedis.zadd("vehicles", 0, "bike");
//				  Set<String> vehicles = jedis.zrange("vehicles", 0, -1);
//				  System.out.println( vehicles );
	
			  }
			     
		  // close the connection pool
		  jedisPool.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="http://localhost:8080/customer/";
		Tester test= new Tester();
		
		String token=test.getToken(url, "admin");
//		if(token!=null)
//		test.validateToken(url,"admin", token);
//		
		//=====Test đăng ký===//
//		Customer customer =new Customer();
//		customer.setUserName("daica11");
//		customer.setIme("111111111111");
//		customer.setProviderId((byte)100);
//		customer.setServerID((byte)1);
//		customer.setPassword("111111111111");
//		test.register2(url, customer);
		//Customer cus= test.loginPost();
		//test.validateToken(url, cus.getUserName(), cus.getToken());
		//test.changePassword();
	//	test.sendRegisterPost();;
		//test.checkRedis("customer");
	}

}
