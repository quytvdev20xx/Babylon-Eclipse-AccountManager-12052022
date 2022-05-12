package com.sgc.accountmanager.service;

import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sgc.accountmanager.entities.Customer;
import com.sgc.accountmanager.repository.UserRepository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
@RedisHash(value = "customer", timeToLive = 3000)
@Repository
public class CustomerCacheManager {
	@Autowired
    private UserRepository repository;
	 public CustomerCacheManager(UserRepository userRepository) {
	        this.repository = userRepository;
	    }
	//@Override

	@Caching(evict = { @CacheEvict(value = "usersList", allEntries = true), }, put = {
	@CachePut(value = "customer", key = "#customer.getUserId()") })
	public Customer saveUser(Customer customer) {

	try {

		return repository.save(customer);

	} catch (Exception e) {
		e.printStackTrace();
	//throw new CustomException("Error while saving user", 500);
		return null;
	}

	}
	@Caching(evict = { @CacheEvict(value = "usersList", allEntries = true), }, put = {
			@CachePut(value = "customer", key = "#customer.getUserName()") })
	public Customer saveByUserName(Customer customer) {
	
		try {
			customer.setUpdateTime(new Date());
			return repository.save(customer);
		
		} catch (Exception e) {
			e.printStackTrace();
		//throw new CustomException("Error while saving user", 500);
			return null;
		}
	
	}
	
	@Cacheable(value = "customer", key = "#userId")
	public Customer findFirstByUserId(Integer userId) {
		return repository.findFirstByUserId(userId);//.orElseThrow(() -> new CustomException("User not found", 400));
	}

	@Cacheable(value = "customer", key = "#userName")
	public Customer findFirstByUserName(String userName) {
		return repository.findFirstByUserName(userName);
	}
	@Cacheable(value = "customer", key = "#facebookId")// ==> cần chuyển về username để cho đồng bộ, và tránh cache dữ liệu cũ
	public Customer findByFacebookId(String facebookId) {
		return repository.findFirstByFacebookId(facebookId);
	}
	@CacheEvict(value = "customer", key = "#id")
	public void deleteById(Integer integer) {
		repository.deleteById(integer);
	}
	public List<Customer> findAll(){
		return repository.findAll();
	}
}
