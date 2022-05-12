package com.sgc.accountmanager.service;


import com.sgc.accountmanager.entities.Customer;
import com.sgc.accountmanager.entities.Message;

import java.util.List;

public interface ICustomerService {
   
    List<Customer> findAll();
    Customer findById(Integer id);
   // Customer findByUserId(Integer id);
    Customer findByUserName(String customer);
    List<Customer> delete(Integer id);
    Customer validateToken( String token); 
//    Customer validateTokenWithUserId(int userId, String token); 
//    Customer validateTokenWithUserName(String userName, String token);
//    Customer getToken(String userName); //test thử với validate Token
    Message register(String customer);
	//Message login(String customer);
	Message changePassword(String userName,String changePasswordRequest);
	Message loginByToken(String customer); 
	//các hàm này nên tách riêng interface backend
	Message topupDiamond(String jsonRequest); //cần chặn chỉ được công vào từ kênh của Admin
	Message topupByInApp(String jsonRequest);
	Message topupByScratchCard(String jsonRequest);
	//Customer save(Customer customerEntity);
	Message debitmoney(String jsonRequest, int numofdebit);
	//Customer login(Customer jsonRequest);
	Message login(String userName, String jsonRequest);
	Message saveByUserName(String username, String customer);
	Message saveByUserName(String username, Customer customer);
	Message findByFacebookId(String customer);
    
}
