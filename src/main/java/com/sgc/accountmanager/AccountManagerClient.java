package com.sgc.accountmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sgc.accountmanager.entities.ChangePasswordRequest;
import com.sgc.accountmanager.entities.Customer;
import com.sgc.accountmanager.entities.Message;
import com.sgc.utils.DateDeserializer;
import com.sgc.utils.MessageType;

public class AccountManagerClient {
	private String url="http://localhost:8080/customer/register";
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private HttpPost http;
//	public  AccountManagerClient(String url) {
//		this.url=url;
//		this.http = new HttpPost(url);
//	}
	
	 /**
	  * Spring r
	  * @return
	  */
//    public String doGet(String userName, String password, int providerId, int serverId, String ime,String ip) {
//        
//        
//        Customer customer=null;
//		try {
//			customer=new Customer();
//			customer.setUserName(userName);
//			
//			customer.setPassword(password);
//			customer.setProviderId(providerId);
//			customer.setServerID(serverId);
//			customer.setIme(ime);
//			customer.setIp(ip);
//			RestTemplate restTemplate = new RestTemplate();
//			
//	        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//	        String response = responseEntity.getBody();
//			///========
//			Gson gson = new Gson();    
//		    String cusJson = gson.toJson(customer);
//		    System.out.println("GSON gson.toJson(customer)=="+cusJson);
//	   
//		     List<NameValuePair> urlParameters = new ArrayList<>();
//		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
//		     
//		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
//		
//		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
//		        CloseableHttpResponse response = httpClient.execute(http)) {
//	    		GsonBuilder gsonBuilder = new GsonBuilder();
//				gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
//				gson = gsonBuilder.create();
//				String resp=EntityUtils.toString(response.getEntity());
//				System.out.println("=======sendPost==respose=>>>"+resp);
//				Message message = gson.fromJson(resp, Message.class);
//				System.out.println("From server:"+message.getMessage());
//				if(message.getStatus()==MessageType.SUCCESS) {
//					Customer cus=message.getData();//gson.fromJson(message.getData().toString(), Customer.class);
//					System.out.println("From server cus.getUserID="+cus.getUserId() +" : "+cus.getUserName());
//				}
//		     }
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	    
//		return customer;
//    }
	 

	public Message sendRegister(String url, String userName, String password, int providerId, String reg_phone, int serverId, byte type,String ime,String ip, String facebookId)  {
		Customer customer=null;
		try {
			customer=new Customer();
			customer.setUserName(userName);
			customer.setPassword(password);
			customer.setProviderId(providerId);
			customer.setReg_phone(reg_phone);
			customer.setServerID(serverId);
			customer.setType(type);
			customer.setIme(ime);
			customer.setIp(ip);
			customer.setFacebookId(facebookId);
			Gson gson = new Gson();    
		    String cusJson = gson.toJson(customer);
		    System.out.println("======sendRegister===>GSON gson.toJson(customer)=="+cusJson);
		    System.out.println("======sendRegister===>customer.getPassword()="+customer.getPassword());
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		     
		     http = new HttpPost(url);
		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		        CloseableHttpResponse response = httpClient.execute(http)) {
	    		GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
				gson = gsonBuilder.create();
				String resp=EntityUtils.toString(response.getEntity());
				System.out.println("=======sendPost==respose=>>>"+resp);
				Message message = gson.fromJson(resp, Message.class);
				if(message!=null) {
					System.out.println("From server:"+message.getMessage());
					
					if(message.getStatus()==MessageType.SUCCESS) {
						Customer cusData=message.getData();//gson.fromJson(message.getData().toString(), Customer.class);
						System.out.println("From server cus.getUserID="+cusData.getUserId() +" : "+cusData.getUserName());
					}
					return message;
				}
				else
				{
					System.out.println("From server sendRegister: NULL");
				}
				
		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
		return null;

    }
	public Customer save(String url, String userName, String password, int providerId, int serverId, String ime,String ip)  {
		Customer customer=null;
		try {
			
			customer=new Customer();
			customer.setUserName(userName);
			
			customer.setPassword(password);
			customer.setProviderId(providerId);
			customer.setServerID(serverId);
			customer.setIme(ime);
			customer.setIp(ip);
			Gson gson = new Gson();    
		    String cusJson = gson.toJson(customer);
		    System.out.println("GSON gson.toJson(customer)=="+cusJson);
	   
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		 
		     
		     http = new HttpPost(url);
		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		        CloseableHttpResponse response = httpClient.execute(http)) {
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
	    
		return customer;

    }
	public Message saveByUserName(String url,  Customer customer)  {
		
		try {
			
			
			 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
		    String cusJson = gson.toJson(customer);
		    System.out.println("GSON gson.toJson(customer)=="+cusJson);
	   
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("userName", customer.getUserName()));
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		 
		     
		     http = new HttpPost(url);
		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		        CloseableHttpResponse response = httpClient.execute(http)) {
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
				
				return message;
				
		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
		return null;

    }
	public Message findByUserName(String url,String userName, int gameID)  {
		Customer customer=null;
		try {
			
			customer=new Customer();
			customer.setUserName(userName);
			customer.setGameID(gameID);
			
			Gson gson = new Gson();    
		    String cusJson = gson.toJson(customer);
		    System.out.println("GSON gson.toJson(customer)=="+cusJson);
	   
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		     http = new HttpPost(url);
		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		        CloseableHttpResponse response = httpClient.execute(http)) {
	    		GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
				gson = gsonBuilder.create();
				String resp=EntityUtils.toString(response.getEntity());
				System.out.println("=======sendPost==respose=>>>"+resp);
				Message message = gson.fromJson(resp, Message.class);
				System.out.println("From server:"+message.getMessage());
				return message;
//				if(message.getStatus()==MessageType.SUCCESS) {
//					Customer cusRespone=message.getData();//gson.fromJson(message.getData().toString(), Customer.class);
//					System.out.println("From server="+message.getMessage());
//					if(cusRespone!=null)
//						return cusRespone;
//				}
		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
		return null;

    }
	public Message findByFacebookId(String url,String facebookId, int gameID)  {
		Customer customer=null;
		try {
			
			customer=new Customer();
			customer.setFacebookId(facebookId);
			customer.setGameID(gameID);
			
			Gson gson = new Gson();    
		    String cusJson = gson.toJson(customer);
		    System.out.println("GSON gson.toJson(customer)=="+cusJson);
	   
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		     http = new HttpPost(url);
		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		        CloseableHttpResponse response = httpClient.execute(http)) {
	    		GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
				gson = gsonBuilder.create();
				String resp=EntityUtils.toString(response.getEntity());
				System.out.println("=======sendPost==respose=>>>"+resp);
				Message message = gson.fromJson(resp, Message.class);
				System.out.println("From server:"+message.getMessage());
				return message;
//				if(message.getStatus()==MessageType.SUCCESS) {
//					Customer cusRespone=message.getData();//gson.fromJson(message.getData().toString(), Customer.class);
//					System.out.println("From server="+message.getMessage());
//					if(cusRespone!=null)
//						return cusRespone;
//				}
		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
		return null;

    }
	public Message logIn(String url,String userName, String password, int gameID, String ime, String ip)  {
		Customer customer=null;
		try {
			
			customer=new Customer();
			customer.setUserName(userName);
			customer.setPassword(password);
			customer.setGameID(gameID);
			customer.setIme(ime);
			customer.setIp(ip);
			
			Gson gson = new Gson();    
		    String cusJson = gson.toJson(customer);
		    System.out.println("=======logIn url:"+url);
		    System.out.println("=======logIn==userName=>>>"+userName+ " customer.getPassword()="+customer.getPassword() +" IME="+customer.getIme());
		    System.out.println("=======logIn GSON gson.toJson(customer)=="+cusJson);
	   
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("userName", userName));
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		     http = new HttpPost(url);
		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		        CloseableHttpResponse response = httpClient.execute(http)) {
	    		GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
				gson = gsonBuilder.create();
				String resp=EntityUtils.toString(response.getEntity());
				System.out.println("=======sendPost==respose=>>>"+resp);
				Message message = gson.fromJson(resp, Message.class);
				System.out.println("From server:"+message.getMessage());
				return message;

		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
		return null;

    }
	public Message logInByToken(String url,String userName, String token, int gameID, String ime, String ip)  {
		Customer customer=null;
		try {
			
			customer=new Customer();
			customer.setUserName(userName);
			customer.setToken(token);
			customer.setGameID(gameID);
			customer.setIme(ime);
			customer.setIp(ip);
			
			Gson gson = new Gson();    
		    String cusJson = gson.toJson(customer);
		    System.out.println("=======logInBy TOKEN url:"+url);
		    System.out.println("=======logIn==userName=>>>"+userName+ " customer.getPassword()="+customer.getPassword() +" IME="+customer.getIme());
		    System.out.println("=======logIn GSON gson.toJson(customer)=="+cusJson);
	   
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("userName", userName));
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		     http = new HttpPost(url);
		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		        CloseableHttpResponse response = httpClient.execute(http)) {
	    		GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
				gson = gsonBuilder.create();
				String resp=EntityUtils.toString(response.getEntity());
				System.out.println("=======sendPost==respose=>>>"+resp);
				Message message = gson.fromJson(resp, Message.class);
				System.out.println("From server:"+message.getMessage());
				return message;

		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
		return null;

    }
	public Message debitmoney(String url,int userId,String userName,String token, int numOfDebit, int gameID)  {
		Customer customer=null;
		try {
			
			customer=new Customer();
			customer.setUserName(userName);
			customer.setUserId(userId);
			customer.setToken(token);
			customer.setGameID(gameID);
			
			Gson gson = new Gson();    
		    String cusJson = gson.toJson(customer);
		    System.out.println("=======logIn url:"+url);
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		     urlParameters.add(new BasicNameValuePair("numofdebit", String.valueOf(numOfDebit)));
		     http = new HttpPost(url);
		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		        CloseableHttpResponse response = httpClient.execute(http)) {
	    		GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
				gson = gsonBuilder.create();
				String resp=EntityUtils.toString(response.getEntity());
				System.out.println("=======sendPost==respose=>>>"+resp);
				Message message = gson.fromJson(resp, Message.class);
				System.out.println("From server:"+message.getMessage());
				return message;

		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
		return null;

    }
	public Message changePassWord(String url,int userId,String userName, String oldPassWord, String newPassWord, int gameID, String token)  {
		ChangePasswordRequest customer=null;
		try {
			
			customer=new ChangePasswordRequest();
			customer.setUserName(userName);
			customer.setUserId(userId);
			//customer.setToken(token);
			customer.setOldPassWord(oldPassWord);
			customer.setPassword(newPassWord);
			customer.setGameId(gameID);
			customer.setToken(token);
			
			Gson gson = new Gson();    
		    String cusJson = gson.toJson(customer);
		    System.out.println("=======changePassWord url:"+url);
		     List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("userName", userName));
		     urlParameters.add(new BasicNameValuePair("customer", cusJson));
		     
		     http = new HttpPost(url);
		     http.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		     try (CloseableHttpClient httpClient = HttpClients.createDefault();
		        CloseableHttpResponse response = httpClient.execute(http)) {
	    		GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
				gson = gsonBuilder.create();
				String resp=EntityUtils.toString(response.getEntity());
				System.out.println("=======changePassWord==respose=>>>"+resp);
				Message message = gson.fromJson(resp, Message.class);
				System.out.println("changePassWord From server:"+message.getMessage());
				return message;

		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
		return null;

    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String urlReiste="http://localhost:8080/customer/register";
		String urlFindByName="http://localhost:8080/customer/findByUserName";
		String urlRegister="http://localhost:8080/customer/register";
		String urlSave="http://localhost:8080/customer/save";
		String urldebitmoney="http://localhost:8080/customer/debitmoney";
		String urllogin="http://localhost:8080/customer/login";
		String urlChangePw="http://localhost:8080/customer/changePassword";
		AccountManagerClient client=new AccountManagerClient();
		//client.setUrl(urlRegister);
		//client.findByUserName(url, "daica77", 1);//("daica33", "adaica33", 100, 1, "3333333333", "localhost");
		//Message ms1=client.logIn(urllogin, "daica9001", "123456a", 1, "1234567890","locahost");
		//Customer cus=ms1.getData();
		//Message ms2=client.debitmoney(urlRegister,237892,"daica99", cus.getToken(), 10,1);
		
		//client.save(urlSave,"daica94", "dai932222", 100, 1, "3333333333", "localhost");
		//client.sendRegister(urlRegister, "dai7007", "123456a", 10, 10, "8888888888", "locahost");
//		Message ms1=client.logIn(urllogin, "dai7007", "123456a", 1, "1234567890","locahost");
//		System.out.println("UserId()="+ms1.getData().getUserId());
//		System.out.println("tokem="+ms1.getData().getToken());
		System.out.println("======>BAT DAU CHANG PASS");
		Message ms2=client.changePassWord(urlChangePw, 237902, "dai7007", "123456a", "123456b", 1,"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImRhaTcwMDciLCJ1c2VySWQiOiIyMzc5MDIifQ.A3EUIJoLKiRjEZgGeVdAC_SlKShr4zTWt8dZD4OzA_w");
		System.out.println("======>BAT DAU CHANG PASS: DONE, STATUS="+ms2.getMessage());
	}

}
