package com.sgc.accountmanager.service;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sgc.accountmanager.AccountManagerClient;
import com.sgc.accountmanager.entities.ChangePasswordRequest;
import com.sgc.accountmanager.entities.Customer;
import com.sgc.accountmanager.entities.Message;
import com.sgc.accountmanager.entities.ScratchCardCallBackResponse;
import com.sgc.accountmanager.entities.RegisterObj;
import com.sgc.accountmanager.entities.ScratchCardCallBackRequest;
import com.sgc.accountmanager.repository.UserRepository;

import com.sgc.utils.JWTUtils;
import com.sgc.utils.MD5Hashing;
import com.sgc.utils.MessageType;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CustomerServiceServiceImpl implements ICustomerService {
    private static final String REDIS_CACHE_VALUE = "cache_cus";
//    @Autowired
//    private UserRepository repository;
    @Autowired
    private CustomerCacheManager customerDAO; 
    public CustomerServiceServiceImpl(UserRepository userRepository,CustomerCacheManager customerDAO) {
     //   this.repository = userRepository;
        this.customerDAO = customerDAO;
    }

//    @Override
//    @CachePut(value = REDIS_CACHE_VALUE, key = "#customer")
//   // @Cacheable(value = REDIS_CACHE_VALUE)
//    public Message save(String customer) {
//    	Message message = new Message();
//    	try {
////    		ObjectMapper mapper = new ObjectMapper();
////    		Customer cusReqest = mapper.readValue(customer, Customer.class);
////    		
////    		if (cusReqest==null || cusReqest.getUserName()== null) {
////    			message.setStatus(MessageType.FAIL);
////    			message.setMessage("USER KH??NG T???N T???I");
////    			return message;
////    		}
////    		else {
////    			Customer cusResp=repository.save(cusReqest);
////    			message.setStatus(MessageType.SUCCESS);
////    			message.setMessage("???? SAVE OKI");
////    			message.setData(cusResp);
////    			return message;
////    		}
//    		ObjectMapper mapper = new ObjectMapper();
//			Customer cusReqest = mapper.readValue(customer, Customer.class);
//			System.out.println("users ????ng nh???p: " + cusReqest.getUserName());
//			if (cusReqest.getUserName().trim().equals("") || cusReqest.getUserName().trim() == null) {
//				message.setStatus(MessageType.FAIL);
//				message.setMessage("Kh??ng ???????c ????? tr???ng username");
//				return message;
//			}
//			if (cusReqest.getPassword().trim().equals("") || cusReqest.getPassword().trim() == null) {
//				message.setStatus(MessageType.FAIL);
//				message.setMessage("Kh??ng ???????c ????? tr???ng password");
//				return message;
//			}
//			Customer cusResp=repository.save(cusReqest);
//			if(cusResp!=null) {
//				message.setStatus(MessageType.SUCCESS);
//				message.setMessage("???? SAVE OKI");
//				message.setData(cusResp);
//				return message;
//			}
//			else {
//				message.setStatus(MessageType.FAIL);
//				message.setMessage("L???I KO SAVE ???????C DB");
//				return message;
//			}
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			message.setStatus(MessageType.FAIL);
//			message.setMessage("SAVE:L???i khi x??? l??");
//			e.printStackTrace();
//		}
//        return message;
//    }
    @Override
    @Cacheable(value = "users", key = "#userId")
    public Customer findById(Integer userId) {
        return customerDAO.findFirstByUserId(userId);
    }
    @Override
	//@Cacheable(value= REDIS_CACHE_VALUE, key ="#username")
   // @Cacheable(REDIS_CACHE_VALUE)
    public Customer findByUserName(String username) {
		// TODO Auto-generated method stub
		
		System.out.println("=========findByUserName:"+username);
		
		try {
				Customer customer=customerDAO.findFirstByUserName(username);
				if(customer!=null&&customer.getUserName()!=null) {
				//	System.out.println(userName+" : ==>tim thay customer.getUserName() tuong ung:"+customer.getUserName());
					return customer;
				}
				else
					System.out.println("============findByUserName:Khong ton tai user: "+ username);

			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
		
	}
    @Override
    @CachePut(value = REDIS_CACHE_VALUE, key = "#username")
    public Message saveByUserName(String username, String jsonRequest) {
    	try {
    		Message message = new Message();
    		ObjectMapper mapper = new ObjectMapper();
			Customer customer = mapper.readValue(jsonRequest, Customer.class);
        	message.setStatus(MessageType.SUCCESS);
    		message.setData(customer);
        	if (customerDAO.saveByUserName(customer)!=null)
        	{
        		message.setMessage("====saveByUserName: C???p nh???t USER th??nh c??ng. username="+username);
        		return message;
        	}
        	else {
        		message.setMessage("====saveByUserName: C???p nh???t USER THAT BAI. username="+username);
        		message.setStatus(MessageType.FAIL);
        		
        		return message;
        	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
    }
    @Override
    @CachePut(value = REDIS_CACHE_VALUE, key = "#username")
    public Message saveByUserName(String username, Customer customer) {
    	try {
    		Message message = new Message();
    		
        	if (customerDAO.saveByUserName(customer)!=null)
        	{
        		message.setMessage("====saveByUserName: C???p nh???t USER th??nh c??ng. username="+username);
        		message.setStatus(MessageType.SUCCESS);
        		message.setData(customer);
        		return message;
        	}
        	else {
        		message.setMessage("====saveByUserName: C???p nh???t USER THAT BAI. username="+username);
        		message.setStatus(MessageType.FAIL);
        		
        		return message;
        	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
    }
    @Override
    @CacheEvict(value = "Users", key = "#id")
    public List<Customer> delete(Integer id) {
        customerDAO.deleteById(id);
        return customerDAO.findAll();
    }

	@Override
	@Cacheable(value = REDIS_CACHE_VALUE)
	//@Cacheable(value = "user-get-all")
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return customerDAO.findAll();
	}

	
	//@Cacheable(value = REDIS_CACHE_VALUE)
//	/**
//	 * l??u ??:h??m n??y ch??? d??ng cho server-server, kh??ng d??ng cho client, v?? c?? tr??? l???i to??n b??? n???i dung c???a th??ng tin ng?????i ch??i
//	 */
//	public Message findByUserName(String customer) {//l??u ??:h??m n??y ch??? d??ng cho server-server, kh??ng d??ng cho client
//		// TODO Auto-generated method stub
//		Message message = new Message();
//		System.out.println("T??M THEO USERNAME:"+customer);
//		
//		try {
//			//create ObjectMapper instance
//				ObjectMapper mapper = new ObjectMapper();
//				Customer cusReqest = mapper.readValue(customer, Customer.class);
//				
//				if (cusReqest==null || cusReqest.getUserName()== null) {
//					message.setStatus(MessageType.FAIL);
//					message.setMessage("USER KH??NG T???N T???I");
//					return message;
//				}
//				System.out.println("users check: " + cusReqest.getUserName());
//				Customer customerCache=repository.findFirstByUserName(cusReqest.getUserName());//xem ????a l??n cache
//
//				if(customerCache!=null&&customerCache.getUserName()!=null) {
//					message.setStatus(MessageType.SUCCESS);
//					message.setMessage("DONE, USER T???N T???I");
//					message.setData(customerCache);// xem lai ko can tra noi dung
//					System.out.println("_________________######__________LOGIN__OK__________________#####________________"+cusReqest.getUserId());
//				}
//				else {
//					message.setStatus(MessageType.FAIL);
//					message.setMessage("DONE, KH??NG T???N T???I, B???N C?? TH??? T???O USER M???I");
//				}
////				CcuLog ccuLog = new CcuLog();
////				ccuLog.setUserId(users.getUserId());
////				ccuLog.setTypeDevice(users.getTypeDevice());
////				ccuLog.setTypeDevice(loginRequest.getTypeDevice());
////				ccuLog.save();
//			} catch (Exception e) {
//				message.setStatus(MessageType.FAIL);
//				message.setMessage("L???I H??? TH???NG CHECK");
//				e.printStackTrace();
//			}
//		return message;
//		
//	}

	
	private Customer validateTokenWithUserName(String userName, String token ) {
		// TODO Auto-generated method stub
		Customer customer=null;
		System.out.println("###################### validateToken============= " );
		HashMap<String, String> map = JWTUtils.validateJWT(token);
		if (map == null) {
			
			System.out.println("Token kh??ng h???p l???");
			return null;
		}
		else {
			Gson gson = new Gson();    
			String sUser=map.get("json");
			
			Customer cus = gson.fromJson(sUser, Customer.class);
			if(cus!=null) {
				System.out.println("Token OOKKKK"+cus.getUserName());
				if(cus.getUserName().equals(userName)) {
					customer=customerDAO.findFirstByUserName(userName);
				}else
				{
					System.out.println("Token C???A USER KH??C->"+cus.getUserName());
				}
			}
			return customer;
		}
	}
	
	private boolean validateTokenWithUserId(int userId, String token ) {
		try {
			// TODO Auto-generated method stub
			Customer customer=null;
			System.out.println("###################### validateToken============= userId= "+userId );
			HashMap<String, String> map = JWTUtils.validateJWT(token);
			if (map == null) {
				
				System.out.println("==============Token kh??ng h???p l???");
				return false;
			}
			else {
				Gson gson = new Gson();    
				String userIdToken=map.get("userId");
				if(userIdToken.equals(String.valueOf(userId))) {
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	public int isValidUserName(String username) {
		for (int i = 0; i < username.length(); i++) {
			char c = username.charAt(i);
			if ((('0' > c) || (c > '9')) && (('A' > c) || (c > 'Z')) && (('a' > c) || (c > 'z'))) {
				return -1;
			}
		}
		Customer customer=this.findByUserName(username);
		if (customer!=null) {
			return -2;
		}
		if (username.length() > 50 || username.length() < 4)
			return -3;
		return 1;
	}
	@Override
	//@CachePut(value = REDIS_CACHE_VALUE, key = "#customer.userId")
	//@CachePut(value = "Users")
	public Message register(String jsonRequest) {
		// TODO Auto-generated method stub
		Message message = new Message();
		System.out.println("======Register:v??o ????ng k?? t??? game:"+jsonRequest);
		
		try {
			//create ObjectMapper instance
			ObjectMapper mapper = new ObjectMapper();
			Customer customer = mapper.readValue(jsonRequest, Customer.class);
			System.out.println("======Register: users ????ng k?? " + customer.getUserName()+ " Passs="+customer.getPassword());

			int iResult = isValidUserName(customer.getUserName());
//			if (iResult == -1) {
//				message.setStatus(MessageType.FAIL);
//				message.setMessage("T??n t??i kho???n kh??ng ???????c ch???a k?? t??? ?????c bi???t");
//				System.out.println("T??n t??i kho???n kh??ng ???????c ch???a k?? t??? ?????c bi???t");
//				return message;
//			} else 
				
				if (iResult == -2) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("T??i kho???n ???? ???????c s??? d???ng");
				System.out.println("T??i kho???n ???? ???????c s??? d???ng");
				return message;
			} else if (iResult == -3) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("T??i kho???n ch??? ???????c ph??p ch???a t??? 6 ?????n 20 k?? t???");
				System.out.println("T??i kho???n ch??? ???????c ph??p ch???a t??? 6 ?????n 50 k?? t???");
				return message;
			}
			if (customer.getUserName().contains("admin")) {
				message.setStatus(MessageType.FAIL);
				System.out.println("B???n vui l??ng ch???n t??i kho???n kh??c!");

				return message;
			}
//			if (!isValidPassword(customer.getPassword())) {
//				message.setStatus(MessageType.FAIL);
//				message.setMessage("M???t kh???u ph???i > 6 k?? t???, bao g???m c??? ch??? v?? s???!");
//				System.out.println("M???t kh???u ph???i > 6 k?? t???, bao g???m c??? ch??? v?? s???!");
//				return message;
//			}

			if (customer.getPassword().toString().trim().length() >= 80) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("M???t kh???u ph???i < 80 k?? t???, bao g???m c??? ch??? v?? s???!");
				System.out.println("M???t kh???u ph???i < 80 k?? t???, bao g???m c??? ch??? v?? s???!");
				return message;
			}

//			if (customer.getUserName().equals(customer.getPassword())) {
//				message.setStatus(MessageType.FAIL);
//				message.setMessage("M???t kh???u kh??ng ???????c tr??ng v???i t??n ????ng nh???p!");
//				System.out.println("M???t kh???u kh??ng ???????c tr??ng v???i t??n ????ng nh???p!");
//				return message;
//			}


//			users.setGameId("0");
//			users.setGameHasPlayed("");
//			users.setRegPhone("");
//			users.setLinkAvatar("");
//			users.setFullName("");
//			users.setBirthDay("");
//			users.setAddress("");
//			users.setNationalId("");
//			users.setNationalIdIssuedDate("");
//			users.setNationalIdIssuedLocation("");
//			users.setIme("");
//			users.setIp("");
//			users.setGender(2);
//			users.setXu(0);
//			users.setActive(1);
//			users.setLockStatus(0);
//			users.setTypeAcc(1);
//			users.setPassWord(Md5.hash(users.getPassWord()));
//			users.setTimeUpdate(new Timestamp(System.currentTimeMillis()));
//			users.save();
//
//			// th??m users v??o cache
//			CacheManage.getInstance().onAddUsersRegister(users);
//
//			Users updateUserId = CacheManage.getInstance().mapUsers.get(users.getUserName().trim());
//			updateUserId.setUserId(users.getId());
//			updateUserId.save();
//			updateUserId.update();
//			CacheManage.getInstance().onAddUsersRegister(updateUserId);
		
			customer.setPassword(MD5Hashing.getMD5(customer.getPassword()));
			customer.setUpdateTime(new Date(System.currentTimeMillis()));
			//customer.setDiamond(666);//test
			

			Customer cusResp= this.customerDAO.saveByUserName(customer);
			
			//String token = JWTUtils.generateJWT(cusResp);
			//cusResp.setToken(token);//neu dang ky thanh cong thi cung tao token luon, lay doi tuong nay thi moi co userid
			
			message.setStatus(MessageType.SUCCESS);
			message.setMessage("????ng k?? th??nh c??ng");
			
			message.setData(cusResp);
			System.out.println("######======REGISTER:cusResp.getUserId()="+cusResp.getUserId());
		} catch (Exception e) {
			message.setStatus(MessageType.FAIL);
			message.setMessage("????NG K??: th???t b???i");
			e.printStackTrace();
		}
		return message;
		
	}
//	@Override
//	//@Cacheable(value=REDIS_CACHE_VALUE,key = "#customer.userName")
//	public Customer login(Customer customer) {
//		// TODO Auto-generated method stub
//		return this.findByUserName(customer.getUserName());//khi login chi co username , ko co userid
//		
////		Message message = new Message();
////		System.out.println("======================????NG NH???P T??? GAME:"+cusReqest.toString());
////		
////		try {
////			
////				System.out.println("===ACCOUNT MANAGER: login users ????ng nh???p: " + cusReqest.getUserName());
////				if ( cusReqest.getUserName() == null||cusReqest.getUserName().trim().equals("")) {
////					message.setStatus(MessageType.FAIL);
////					message.setMessage("Kh??ng ???????c ????? tr???ng username");
////					System.out.println("Kh??ng ???????c ????? tr???ng username");
////					return message;
////				}
////				if ( cusReqest.getPassword() == null||cusReqest.getPassword().equals("")) {
////					message.setStatus(MessageType.FAIL);
////					message.setMessage("Kh??ng ???????c ????? tr???ng password");
////					System.out.println("Kh??ng ???????c ????? tr???ng password");
////					return message;
////				}
////				
////				Customer customer=this.findByUserName(cusReqest.getUserName());//khi login chi co username , ko co userid
////				if(customer==null) {
////					message.setStatus(MessageType.FAIL);
////					message.setMessage("User khong ton tai!");
////					System.out.println("User:"+cusReqest.getUserName()+" khong ton tai!!");
////					return message;
////				}
////				String passwordMd5=MD5Hashing.getMD5(cusReqest.getPassword());//Kiem tra mat khau
//////				System.out.println("==========login jsonRequest passwordMd5="+passwordMd5);
//////				System.out.println("==========login customer passwordMd5="+customer.getPassword());
////				if (!passwordMd5.equals(customer.getPassword())){
////					message.setStatus(MessageType.FAIL);
////					message.setMessage("M???t kh???u kh??ng ????ng!");
////					System.out.println("==========login customer M???t kh???u kh??ng ????ng!=");
////					return message;
////				}
////				
////				String token = JWTUtils.generateJWT(customer.getUserName(),String.valueOf(customer.getUserId()));
////				customer.setToken(token);
////				System.out.println("TOKEN="+token);
////				this.save(customer);
////	
//////				CcuLog ccuLog = new CcuLog();
//////				ccuLog.setUserId(users.getUserId());
//////				ccuLog.setTypeDevice(users.getTypeDevice());
//////				ccuLog.setTypeDevice(loginRequest.getTypeDevice());
//////				ccuLog.save();
////	
////				message.setStatus(MessageType.SUCCESS);
////				message.setMessage("????ng nh???p th??nh c??ng");
////				message.setData(customer);
////				System.out.println("######__________LOGIN__OK_- customer.getUserId()="+customer.getUserId());
////			} catch (Exception e) {
////				message.setStatus(MessageType.FAIL);
////				message.setMessage("????ng nh???p th???t b???i");
////				e.printStackTrace();
////			}
////		return message;
//	
//	}
	@Override
	//@Cacheable(value = REDIS_CACHE_VALUE, key="#userName")
	public Message login(String userName, String jsonRequest) {
		// TODO Auto-generated method stub
		Message message = new Message();
		System.out.println("================????NG NH???P T??? GAME: jsonRequest="+jsonRequest);
		
		try {
			//create ObjectMapper instance
				ObjectMapper mapper = new ObjectMapper();
				Customer cusReqest = mapper.readValue(jsonRequest, Customer.class);
			
				System.out.println("===ACCOUNT MANAGER: login users ????ng nh???p: " + cusReqest.getUserName());
				if ( cusReqest.getUserName() == null||cusReqest.getUserName().trim().equals("")) {
					message.setStatus(MessageType.FAIL);
					message.setMessage("Kh??ng ???????c ????? tr???ng username");
					System.out.println("Kh??ng ???????c ????? tr???ng username");
					return message;
				}
				if ( cusReqest.getPassword() == null||cusReqest.getPassword().equals("")) {
					message.setStatus(MessageType.FAIL);
					message.setMessage("Kh??ng ???????c ????? tr???ng password");
					System.out.println("Kh??ng ???????c ????? tr???ng password");
					return message;
				}
				//Customer customer=this.login(cusReqest);
				Customer customer=this.customerDAO.findFirstByUserName(cusReqest.getUserName());//khi login chi co username , ko co userid
				if(customer==null) {
					message.setStatus(MessageType.FAIL);
					message.setMessage("User khong ton tai!");
					System.out.println("User:"+cusReqest.getUserName()+" khong ton tai!!");
					return message;
				}
				String passwordMd5=MD5Hashing.getMD5(cusReqest.getPassword());//Kiem tra mat khau
//				System.out.println("==========login jsonRequest passwordMd5="+passwordMd5);
//				System.out.println("==========login customer passwordMd5="+customer.getPassword());
				if (!passwordMd5.equals(customer.getPassword())){
					message.setStatus(MessageType.FAIL);
					message.setMessage("M???t kh???u kh??ng ????ng!");
					System.out.println("==========login customer M???t kh???u kh??ng ????ng!=");
					return message;
				}
				String token = JWTUtils.generateJWT(customer.getUserName(),String.valueOf(customer.getUserId()));
				customer.setToken(token);
				System.out.println("TOKEN="+token);
				customer.setLastLoginTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
				this.customerDAO.saveByUserName(customer);
				message.setStatus(MessageType.SUCCESS);
				message.setMessage("????ng nh???p th??nh c??ng");
				message.setData(customer);
				System.out.println("######__________LOGIN__OK_- customer.getUserId()="+customer.getUserId());
				
//				CcuLog ccuLog = new CcuLog();
//				ccuLog.setUserId(users.getUserId());
//				ccuLog.setTypeDevice(users.getTypeDevice());
//				ccuLog.setTypeDevice(loginRequest.getTypeDevice());
//				ccuLog.save();
		} catch (Exception e) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("????ng nh???p th???t b???i");
				e.printStackTrace();
			}
		return message;
	
	}
	@Override
	public Message loginByToken(String jsonRequest) {
		// TODO Auto-generated method stub
		Message message = new Message();
		System.out.println("????NG NH???P T??? GAME:"+jsonRequest);
		
		try {
			//create ObjectMapper instance
				ObjectMapper mapper = new ObjectMapper();
				Customer cusReqest = mapper.readValue(jsonRequest, Customer.class);
				System.out.println("users ????ng nh???p: " + cusReqest.getUserName());
				if ( cusReqest.getUserName() == null||cusReqest.getUserName().trim().equals("") ) {
					message.setStatus(MessageType.FAIL);
					message.setMessage("Kh??ng ???????c ????? tr???ng username");
					return message;
				}
				//login b???ng token n??n check token
				if (cusReqest.getToken() == null) {
					message.setStatus(MessageType.FAIL);
					message.setMessage("Thi???u TOKEN");
					return message;
				}
				
				Customer customer=validateTokenWithUserName(cusReqest.getUserName(),cusReqest.getToken());

				//this.save(customer);//co can save lai ko?
//				CcuLog ccuLog = new CcuLog();
//				ccuLog.setUserId(users.getUserId());
//				ccuLog.setTypeDevice(users.getTypeDevice());
//				ccuLog.setTypeDevice(loginRequest.getTypeDevice());
//				ccuLog.save();
	
				message.setStatus(MessageType.SUCCESS);
				message.setMessage("????ng nh???p b???ng Token th??nh c??ng");
				message.setData(customer);
				System.out.println("_________________######__________LOGIN__OK__________________#####________________"+customer.getUserId());
			} catch (Exception e) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("????ng nh???p th???t b???i");
				e.printStackTrace();
			}
		return message;
	
	}
//	@Override
//	public Customer getToken(String userName) {
//		// TODO Auto-generated method stub
//		Customer customer;
//		try {
//			System.out.println("################ getToken++++++++++++++++");
//			 customer=repository.findFirstByUserName(userName);
//			 if(customer==null) {
//				 System.out.println("repository.findFirstByUserName(userName)==> null");
//			 }else {
//				 System.out.println("################ getToken userid=++++++++++++++++"+customer.getUserId());
//				 String token = JWTUtils.generateJWT(customer);
//				 System.out.println("################ get TOKEN="+token);	
//				 customer.setToken(token);
//				 return customer;
//			 }
//			
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	@Override
	//@CacheEvict(REDIS_CACHE_VALUE) 
   //@CacheEvict(value = REDIS_CACHE_VALUE, key="#userName")
	public Message changePassword(String userName,String jsonRequest) {
		System.out.println("==============changePassword:"+jsonRequest);
		Message message = new Message();
		try {
			ObjectMapper mapper = new ObjectMapper();
			ChangePasswordRequest changeRequest =mapper.readValue(jsonRequest, ChangePasswordRequest.class);
			//kiem tra token
			boolean rs=this.validateTokenWithUserId(changeRequest.getUserId(), changeRequest.getToken());//xem ????a l??n cache
			if(!rs) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("User khong ton tai!");
				System.out.println("User:"+changeRequest.getUserId()+" khong ton tai TOken nay!!");
				return message;
			} 
			Customer customer=this.customerDAO.findFirstByUserName(changeRequest.getUserName());
			if (customer == null) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("T??i kho???n kh??ng t???n t???i");
				System.out.println("==============changePassword:T??i kho???n kh??ng t???n t???i");
			}
			String passMD5=MD5Hashing.getMD5(changeRequest.getOldPassWord());
			if (!customer.getPassword().equals(passMD5)) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("M???t kh???u kh??ng ????ng!");
				System.out.println("==============changePassword:M???t kh???u kh??ng ????ng!Request pw="+passMD5 +" pass="+customer.getPassword());
				return message;
			}
			if (!isValidPassword(changeRequest.getPassword())) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("M???t kh???u ph???i > 6 k?? t???, bao g???m c??? ch??? v?? s???!");
				System.out.println("==============changePassword:M???t kh???u ph???i > 6 k?? t???, bao g???m c??? ch??? v?? s???");
				return message;
			}
			if (changeRequest.getPassword().toString().trim().length() >= 30) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("M???t kh???u ph???i < 30 k?? t???, bao g???m c??? ch??? v?? s???!");
				return message;
			}
			if (customer.getUserName().equals(changeRequest.getPassword())) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("M???t kh???u kh??ng ???????c tr??ng v???i t??n ????ng nh???p!");
				return message;
			}
			customer.setPassword(MD5Hashing.getMD5(changeRequest.getPassword()));
			//customer.setUpdateTime(new Time);
			AccountManagerClient client =new AccountManagerClient();
			 //message =client.saveByUserName("http://localhost:8080/customer/saveByUserName", customer);
			System.out.println("==============changePassword:saveByUserName");
			//message=this.saveByUserName(customer.getUserName(), customer);
			customer=this.customerDAO.saveByUserName(customer);
			
			message.setStatus(MessageType.SUCCESS);
			message.setMessage("C???p nh???t m???t kh???u th??nh c??ng");
			message.setData(customer);
			return message;
		} catch (Exception e) {
			message.setStatus(MessageType.FAIL);
			message.setMessage("That bai");
			return message;
		}

	}// 0->9: 48->57
	// a->z: 97->122
	// A->Z: 65->90
	private boolean isValidPassword(String str) {
		int so = 0;
		int chu = 0;
		int dacbiet = 0;
		if (str.length() < 7)
			return false;
		for (int i = 0; i < str.length(); i++) {
			int value = (int) str.charAt(i);
			if (value >= 48 && value <= 57)
				so = 1;
			else if ((value >= 97 && value <= 122) || (value >= 65 && value <= 90))
				chu = 1;
			else
				dacbiet = 1;
		}
		int sum = so + chu + dacbiet;
		if (sum >= 2)
			return true;
		else
			return false;
	}

	@Override
	public Message topupByScratchCard(String jsonRequest) {
		Message message = new Message();
		try {
			System.out.println("Paycard nh???n ???????c");
			
			ObjectMapper mapper = new ObjectMapper();
			ScratchCardCallBackRequest payCardCallBackResquest = mapper.readValue(jsonRequest, ScratchCardCallBackRequest.class);
			 
			System.out.println("payCardCallBackResponse " + payCardCallBackResquest.toString());
			//login b???ng token n??n check token
			if (payCardCallBackResquest.getToken() == null) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("Thi???u TOKEN");
				return message;
			}
			
			boolean rs=this.validateTokenWithUserId(payCardCallBackResquest.getUserId(),payCardCallBackResquest.getToken());

			if (!rs) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("userId kh??ng t???n t???i");
				return message;
			}
			Customer customer=this.findById(payCardCallBackResquest.getUserId());
			int lastXuUpdate = customer.getDiamond();
			int diamond = payCardCallBackResquest.getValue();
			
			customer.addDiamond(diamond,customer.getUserName());//ph???n n??y ph???i x??? l?? theo transaction

			//this.save(customer);
			this.customerDAO.saveByUserName(customer);
//			users.update();

//			List<TransectionLog> listTransectionLog = TransectionLog.find.query().findList();
//			long idLog = listTransectionLog.size() + 1;
//
//			TransectionLog transectionLog = new TransectionLog();
//			transectionLog.setId(idLog);
//			transectionLog.setUserId(users.getUserId());
//			transectionLog.setXu(lastXuUpdate);
//			transectionLog.setLastXuUpdate(users.getXu());
//			transectionLog.setDescription("n???p ti???n qua paycard");
//			transectionLog.setContent(payCardCallBackResponse.getValue());
//			transectionLog.setBonus(
//					Double.parseDouble(payCardCallBackResponse.getValue()) * listBonus.get(0).getBonus() + "");
//			transectionLog.save();

			message.setStatus(MessageType.SUCCESS);
			message.setMessage("th??nh c??ng");
			message.setData(customer);
		} catch (Exception e) {
			message.setStatus(MessageType.FAIL);
			message.setMessage("Ki???m tra ph???n truy???n v???, ho???c convert");
		}

		System.out.println("____________H???t lu???ng PayCard____________");
		return message;
		
	}
	@Override
	public Message topupByInApp(String jsonRequest) {
		return null;
	}
	@Override
	public synchronized Message debitmoney(String jsonRequest, int numofdebit) {
		// TODO Auto-generated method stub
		Message message = new Message();
		System.out.println("==============START:debitDiamond:"+jsonRequest);
		
		try {
		
			ObjectMapper mapper = new ObjectMapper();
			Customer cusReqest = mapper.readValue(jsonRequest, Customer.class);
			if ( cusReqest.getUserId() <=0) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("Kh??ng ???????c ????? tr???ng userid");
				System.out.println("Kh??ng ???????c ????? tr???ng userid");
				return message;
			}
			boolean rs=this.validateTokenWithUserId(cusReqest.getUserId(), cusReqest.getToken());//xem ????a l??n cache
			if(!rs) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("User khong ton tai!");
				System.out.println("User:"+cusReqest.getUserName()+" khong ton tai!!");
				return message;
			}else {
				Customer customer=this.customerDAO.findFirstByUserName(cusReqest.getUserName());
				int oldMoney=customer.getDiamond();
				if(customer.getDiamond()>=numofdebit) {
					customer.setDiamond(customer.getDiamond()- numofdebit);
					message.setStatus(MessageType.SUCCESS);
					message.setMessage(" TRU TIEN THANH CONG");
					message.setData(customer);
					
					this.customerDAO.saveByUserName(customer);
					System.out.println("=====debitmoney=====TRU TIEN THANH CONG======="+customer.getUserId() +" OLD MONEY="+oldMoney+" NEW MONEY="+customer.getDiamond());
				}
				else {
					message.setStatus(MessageType.FAIL);
					message.setMessage("So tien khong du de su dung");
					System.out.println("So tien khong du de su dung"+customer.getUserId() +"  MONEY="+oldMoney+" < Debit="+numofdebit);
					
				}
				
			}
		} catch (Exception e) {
			message.setStatus(MessageType.FAIL);
			message.setMessage("==========debitmoney===TRU TIEN THAT BAI");
			e.printStackTrace();
		}
		return message;
		
	}
	
	@Override
	public Message topupDiamond(String jsonRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer validateToken(String token) {
		// TODO Auto-generated method stub
		Customer customer=null;
		System.out.println("###################### validateToken============= " );
		HashMap<String, String> map = JWTUtils.validateJWT(token);
		if (map == null) {
			System.out.println("Token kh??ng h???p l???");
			return null;
		}
		else {
			Gson gson = new Gson();    
			String sUser=map.get("json");
			Customer cus= gson.fromJson(sUser, Customer.class);
			if(cus!=null) {
				customer=this.findById(cus.getUserId());
			}
			
		}
		return customer;
	}

	@Override
	public Message findByFacebookId(String jsonRequest) {
		Message message = new Message();
		System.out.println("findByFacebookId:"+jsonRequest);
		
		try {
			//create ObjectMapper instance
				ObjectMapper mapper = new ObjectMapper();
				Customer cusReqest = mapper.readValue(jsonRequest, Customer.class);
				System.out.println("findByFacebookId: " + cusReqest.getFacebookId());
				if ( cusReqest.getFacebookId() == null||cusReqest.getFacebookId().trim().equals("") ) {
					message.setStatus(MessageType.FAIL);
					message.setMessage("Kh??ng ???????c ????? tr???ng FacebookId");
					return message;
				}
				Customer customer=this.customerDAO.findByFacebookId(cusReqest.getFacebookId());
				if(customer!=null) {
					message.setStatus(MessageType.SUCCESS);
					message.setMessage("????ng nh???p b???ng Token th??nh c??ng");
					message.setData(customer);
				}else
				{
					message.setStatus(MessageType.FAIL);
					message.setMessage("KHONG TON TAI FACEBOOK NAY");
					System.out.println("KHONG TON TAI FACEBOOK NAY");
					
				}
			
			} catch (Exception e) {
				message.setStatus(MessageType.FAIL);
				message.setMessage("????ng nh???p th???t b???i");
				e.printStackTrace();
			}
		return message;
	}

	

	
}
