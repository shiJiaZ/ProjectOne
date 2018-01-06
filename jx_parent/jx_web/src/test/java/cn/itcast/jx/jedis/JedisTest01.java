package cn.itcast.jx.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest01 {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		/*Jedis jedis = new Jedis("127.0.0.1", 6379);

		jedis.set("itheima18", "itheima18,牛逼");
		
		String result = jedis.get("itheima18");
		
		System.out.println(result);*/
		
		//配置参数
		JedisPoolConfig config = new JedisPoolConfig();
		//空闲时最大连接数
		config.setMaxIdle(10);
		//最大连接数
		config.setMaxTotal(50);
		
		//创建池对象
		JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);
		//
		Jedis jedis = pool.getResource();
		
		String result = jedis.get("itheima18");
		
		jedis.del("itheima18");
		
		
		System.out.println(result);
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
