package com.sgc.accountmanager.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sgc.accountmanager.entities.Customer;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<Customer, Integer> {
//	@Query("from Bound b where b.startTimeStamp <= :currentTimeStamp and "
//	        + "b.endTimeStamp >= :currentTimeStamp")
//	List<Customer> findByCurrentTimeStamp(@Param("currentTimeStamp") Timestamp currentTimeStamp);
	//List<Customer> findUpdateTimeBetween(Date start, Date end);
	@Query("select a from Customer a where a.updateTime >= :creationDateTime")
    List<Customer> findAllWithUpdateTimeAfter( @Param("creationDateTime") java.util.Date creationDateTime);
	@Override
    List<Customer> findAll();
	@Override
    void deleteById(Integer integer);
	
	Customer findFirstByUserId(Integer id);
	Customer findFirstByUserName(String username);
	Customer findFirstByFacebookId(String facebookId);
}
