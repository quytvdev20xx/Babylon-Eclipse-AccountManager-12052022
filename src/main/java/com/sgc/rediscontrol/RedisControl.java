package com.sgc.rediscontrol;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisControl {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  // Create a Jedis connection pool
		  JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
		  
		  // Get the pool and use the database
		  try (Jedis jedis = jedisPool.getResource()) {
			  jedis.auth("123456");
			  jedis.set("mykey", "Hello from Jedis");
			  String value = jedis.get("mykey");
			  System.out.println( value );
			         
			  jedis.zadd("vehicles", 0, "car2"); 
			  jedis.zadd("vehicles", 0, "bike2");
			  Set<String> vehicles = jedis.zrange("vehicles", 0, -1);
			  System.out.println( vehicles );

		  }
		     
		  // close the connection pool
		  jedisPool.close();
	}
}
