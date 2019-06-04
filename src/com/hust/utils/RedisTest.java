package com.hust.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {

	JedisPool pool;
	Jedis jedis;
	
	//初始化
	public void start(){
		
		// 初始化Redis连接池
		pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
		// 从Redis连接池中获取一个连接
		jedis = pool.getResource();
		// Redis的密码
		jedis.auth("123456");
		
		
	}
	
	//添加测试
	public void putTest(String key,String value){
		
//		jedis.set("user", "admin");
		jedis.set(key, value);
		
//		System.out.println("redis测试=="+jedis.get("user"));
		
	}
	
	//删除
	
	
	
	//get
	public String getValue(String key){
		
		return jedis.get(key);
		
	}
	
	
	

}
