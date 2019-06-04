package com.hust.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {

	JedisPool pool;
	Jedis jedis;
	
	//��ʼ��
	public void start(){
		
		// ��ʼ��Redis���ӳ�
		pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
		// ��Redis���ӳ��л�ȡһ������
		jedis = pool.getResource();
		// Redis������
		jedis.auth("123456");
		
		
	}
	
	//��Ӳ���
	public void putTest(String key,String value){
		
//		jedis.set("user", "admin");
		jedis.set(key, value);
		
//		System.out.println("redis����=="+jedis.get("user"));
		
	}
	
	//ɾ��
	
	
	
	//get
	public String getValue(String key){
		
		return jedis.get(key);
		
	}
	
	
	

}
