package cn.itcast.jx.jedis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class SpringDataRedisTest02 {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-springdataredis.xml");
		//获取redis的模板
		RedisTemplate<Object, Object>  redisTemplate= (RedisTemplate<Object, Object>) ctx.getBean("redisTemplate");
//		/通过模板操作
		redisTemplate.opsForValue().set("itheima18", "好样的！");
		Object result = redisTemplate.opsForValue().get("itheima18");
		System.out.println(result);
		
	}

}
