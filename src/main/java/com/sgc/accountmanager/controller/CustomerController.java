package com.sgc.accountmanager.controller;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.sgc.accountmanager.entities.Customer;
import com.sgc.accountmanager.entities.Message;
import com.sgc.accountmanager.service.ICustomerService;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.cache.annotation.EnableCaching;
import java.util.List;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@RestController
@RequestMapping("/customer")
@Api(tags = {"CustomerController"})

@EnableCaching
public class CustomerController {
//	
    private ICustomerService customerService;
    @Autowired
    public CustomerController(ICustomerService countryService) {
        this.customerService = countryService;
    }

  
    //
    @GetMapping(value = "/findAllCustomer")
    public ResponseEntity<List<Customer>> findAllCountry() throws RuntimeException {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }
   
    @GetMapping(value = "/findById")
    public ResponseEntity<Customer> findById(@RequestParam Integer id) throws RuntimeException {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }
//    @GetMapping(value = "/findByUserId")
//    public ResponseEntity<Customer> findByUserId(@RequestParam Integer userId) throws RuntimeException {
//        return new ResponseEntity<>(customerService.findByUserId(userId), HttpStatus.OK);
//    }
    @RequestMapping(value = "/findByUserName", 
  		  produces = "application/json", 
  		  method = RequestMethod.POST)
    public ResponseEntity<Customer> findByUserName(@RequestParam String customer) throws RuntimeException {
        return new ResponseEntity<>(customerService.findByUserName(customer), HttpStatus.OK);
    }
    @RequestMapping(value = "/findByFacebookId", 
    		  produces = "application/json", 
    		  method = RequestMethod.POST)
  public ResponseEntity<Message> findByFacebookId(@RequestParam String customer) throws RuntimeException {
      return new ResponseEntity<>(customerService.findByFacebookId(customer), HttpStatus.OK);
  }

    @GetMapping(value = "/deleteById")
    public ResponseEntity<List<Customer>> deleteById(@RequestParam Integer id) throws RuntimeException {
        return new ResponseEntity<>(customerService.delete(id), HttpStatus.OK);
    }
    @RequestMapping(
    		  value = "/register", 
    		  produces = "application/json", 
    		  method = RequestMethod.POST)
    public ResponseEntity<Message> register(@RequestParam String customer) throws RuntimeException {
        return new ResponseEntity<>(customerService.register(customer), HttpStatus.OK);
    }
    @RequestMapping(
  		  value = "/login", 
  		  produces = "application/json", 
  		  method = RequestMethod.POST)
    public ResponseEntity<Message> login(@RequestParam String userName,@RequestParam String customer) throws RuntimeException {
      return new ResponseEntity<>(customerService.login(userName,customer), HttpStatus.OK);
    }
    @RequestMapping(
    		  value = "/loginByToken", 
    		  produces = "application/json", 
    		  method = RequestMethod.POST)
    public ResponseEntity<Message> loginByToken(@RequestParam String customer) throws RuntimeException {
        return new ResponseEntity<>(customerService.loginByToken(customer), HttpStatus.OK);
    }
//    @GetMapping(value = "/getToken")
//    public ResponseEntity<Customer> getToken(@RequestParam String username) throws RuntimeException {
//        return new ResponseEntity<>(customerService.getToken(username), HttpStatus.OK);
//    }
    @GetMapping(value = "/validateToken")
    public ResponseEntity<Customer> validateToken(@RequestParam String token) throws RuntimeException {
        return new ResponseEntity<>(customerService.validateToken(token), HttpStatus.OK);
    }
    @RequestMapping(
    		  value = "/changePassword", 
    		  produces = "application/json", 
    		  method = RequestMethod.POST)
    public ResponseEntity<Message> changePassword(@RequestParam String userName,@RequestParam String customer) throws RuntimeException {
        return new ResponseEntity<>(customerService.changePassword(userName,customer), HttpStatus.OK);
    }
    @RequestMapping(
  		  value = "/debitmoney", 
  		  produces = "application/json", 
  		  method = RequestMethod.POST)
  public ResponseEntity<Message> debitmoney(@RequestParam String customer, int numofdebit) throws RuntimeException {
      return new ResponseEntity<>(customerService.debitmoney(customer,numofdebit), HttpStatus.OK);
  }
    @RequestMapping(
  		  value = "/saveByUserName", 
  		  produces = "application/json", 
  		  method = RequestMethod.POST)
  public ResponseEntity<Message> saveByUserName(@RequestParam String userName,@RequestParam String customer) throws RuntimeException {
      return new ResponseEntity<>(customerService.saveByUserName(userName, customer), HttpStatus.OK);
  }
    
}
