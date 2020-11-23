package app.config;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	

	  @Bean  
	    public RedisTemplate<String,String> paramRedisTemplate(  
	            @Value("${spring.redis.host}") String hostName,  
	            @Value("${spring.redis.port}") int port,  
	            @Value("${spring.redis.password}") String password,  
	            @Value("${spring.redis.pool.max-idle}") int maxIdle,  
	            @Value("${spring.redis.pool.max-active}") int maxTotal,  
	            @Value("${spring.redis.bizdatabase.parmdic}") int index,  
	            @Value("${spring.redis.pool.max-wait}") long maxWaitMillis,
	            @Value("${spring.redis.timeout}")int timeout,
	            @Value("${spring.redis.pool.min-idle}")int minIdle) {  
	        StringRedisTemplate template = new StringRedisTemplate();  
	        template.setConnectionFactory(connectionFactory(hostName, port, password,maxIdle, maxTotal, index, maxWaitMillis, true,timeout,minIdle));  
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(om);
			template.setValueSerializer(jackson2JsonRedisSerializer);
			template.afterPropertiesSet();
			return template;
	    }  
	  	@Bean  
	    public RedisTemplate<String,String> messageRedisTemplate(  
	            @Value("${spring.redis.host}") String hostName,  
	            @Value("${spring.redis.port}") int port,  
	            @Value("${spring.redis.password}") String password,  
	            @Value("${spring.redis.pool.max-idle}") int maxIdle,  
	            @Value("${spring.redis.pool.max-active}") int maxTotal,  
	            @Value("${spring.redis.bizdatabase.message}") int index,  
	            @Value("${spring.redis.pool.max-wait}") long maxWaitMillis,
	            @Value("${spring.redis.timeout}")int timeout,
	            @Value("${spring.redis.pool.min-idle}")int minIdle) {  
	        StringRedisTemplate template = new StringRedisTemplate();  
	        template.setConnectionFactory(connectionFactory(hostName, port, password,maxIdle, maxTotal, index, maxWaitMillis, true,timeout,minIdle));  
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(om);
			template.setValueSerializer(jackson2JsonRedisSerializer);
			template.afterPropertiesSet();
			return template;
	    }  

	  @Bean  
	    public RedisTemplate<String,String> btnRedisTemplate(  
	            @Value("${spring.redis.host}") String hostName,  
	            @Value("${spring.redis.port}") int port,  
	            @Value("${spring.redis.password}") String password,  
	            @Value("${spring.redis.pool.max-idle}") int maxIdle,  
	            @Value("${spring.redis.pool.max-active}") int maxTotal,  
	            @Value("${spring.redis.bizdatabase.button}") int index,  
	            @Value("${spring.redis.pool.max-wait}") long maxWaitMillis,
	            @Value("${spring.redis.timeout}")int timeout,
	            @Value("${spring.redis.pool.min-idle}")int minIdle) {  
	        StringRedisTemplate template = new StringRedisTemplate();  
	        template.setConnectionFactory(connectionFactory(hostName, port, password,maxIdle, maxTotal, index, maxWaitMillis, true,timeout,minIdle));  
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(om);
			template.setValueSerializer(jackson2JsonRedisSerializer);
			template.afterPropertiesSet();
			return template;
	    }  
	  @Bean  
	    public RedisTemplate<String,String> formRedisTemplate(  
	            @Value("${spring.redis.host}") String hostName,  
	            @Value("${spring.redis.port}") int port,  
	            @Value("${spring.redis.password}") String password,  
	            @Value("${spring.redis.pool.max-idle}") int maxIdle,  
	            @Value("${spring.redis.pool.max-active}") int maxTotal,  
	            @Value("${spring.redis.bizdatabase.form}") int index,  
	            @Value("${spring.redis.pool.max-wait}") long maxWaitMillis,
	            @Value("${spring.redis.timeout}")int timeout,
	            @Value("${spring.redis.pool.min-idle}")int minIdle) {  
	        StringRedisTemplate template = new StringRedisTemplate();  
	        template.setConnectionFactory(connectionFactory(hostName, port, password,maxIdle, maxTotal, index, maxWaitMillis, true,timeout,minIdle));  
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(om);
			template.setValueSerializer(jackson2JsonRedisSerializer);
			template.afterPropertiesSet();
			return template;
	    }
	  
	  @Bean  
	    public RedisTemplate<String,String> sessionUserRedisTemplate(  
	            @Value("${spring.redis.host}") String hostName,  
	            @Value("${spring.redis.port}") int port,  
	            @Value("${spring.redis.password}") String password,  
	            @Value("${spring.redis.pool.max-idle}") int maxIdle,  
	            @Value("${spring.redis.pool.max-active}") int maxTotal,  
	            @Value("${spring.redis.bizdatabase.sessionUser}") int index,  
	            @Value("${spring.redis.pool.max-wait}") long maxWaitMillis,
	            @Value("${spring.redis.timeout}")int timeout,
	            @Value("${spring.redis.pool.min-idle}")int minIdle) {  
	        StringRedisTemplate template = new StringRedisTemplate();  
	        template.setConnectionFactory(connectionFactory(hostName, port, password,maxIdle, maxTotal, index, maxWaitMillis, true,timeout,minIdle));  
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(om);
			template.setValueSerializer(jackson2JsonRedisSerializer);
			template.afterPropertiesSet();
			return template;
	    }  
	  @Bean  
	    public RedisTemplate<String,String> pmsRedisTemplate(  
	            @Value("${spring.redis.host}") String hostName,  
	            @Value("${spring.redis.port}") int port,  
	            @Value("${spring.redis.password}") String password,  
	            @Value("${spring.redis.pool.max-idle}") int maxIdle,  
	            @Value("${spring.redis.pool.max-active}") int maxTotal,  
	            @Value("${spring.redis.bizdatabase.pms}") int index,  
	            @Value("${spring.redis.pool.max-wait}") long maxWaitMillis,
	            @Value("${spring.redis.timeout}")int timeout,
	            @Value("${spring.redis.pool.min-idle}")int minIdle) {  
	        StringRedisTemplate template = new StringRedisTemplate();  
	        template.setConnectionFactory(connectionFactory(hostName, port, password,maxIdle, maxTotal, index, maxWaitMillis, true,timeout,minIdle));  
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(om);
			template.setValueSerializer(jackson2JsonRedisSerializer);
			template.afterPropertiesSet();
			return template;
	    }  
	  @Bean  
	    public RedisTemplate<String,String> redisTemplate(  
	            @Value("${spring.redis.host}") String hostName,  
	            @Value("${spring.redis.port}") int port,  
	            @Value("${spring.redis.password}") String password,  
	            @Value("${spring.redis.pool.max-idle}") int maxIdle,  
	            @Value("${spring.redis.pool.max-active}") int maxTotal,  
	            @Value("${spring.redis.database}") int index,  
	            @Value("${spring.redis.pool.max-wait}") long maxWaitMillis,
	            @Value("${spring.redis.timeout}")int timeout,
	            @Value("${spring.redis.pool.min-idle}")int minIdle) {  
	        StringRedisTemplate template = new StringRedisTemplate();  
	        template.setConnectionFactory(connectionFactory(hostName, port, password,maxIdle, maxTotal, index, maxWaitMillis, true,timeout,minIdle));  
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(om);
			template.setValueSerializer(jackson2JsonRedisSerializer);
			template.afterPropertiesSet();
			return template;
	    }  
	  
	    public RedisConnectionFactory connectionFactory(String hostName, int port,  
	            String password, int maxIdle, int maxTotal, int index,  
	            long maxWaitMillis, boolean testOnBorrow,int timeout,int minIdle) {  
	        JedisConnectionFactory jedis = new JedisConnectionFactory();  
	        jedis.setHostName(hostName);  
	        jedis.setPort(port);  
	        if (!StringUtils.isEmpty(password)) {  
	            jedis.setPassword(password);  
	        }  
	        if (index != 0) {  
	            jedis.setDatabase(index);  
	        }  
	        jedis.setTimeout(timeout);
	        jedis.setPoolConfig(poolCofig(maxIdle, maxTotal, maxWaitMillis,testOnBorrow,minIdle));  
	        // 初始化连接pool  
	        jedis.afterPropertiesSet();  
	        RedisConnectionFactory factory = jedis;  
	        return factory;  
	    }  
	  
	    public JedisPoolConfig poolCofig(int maxIdle, int maxTotal,  
	            long maxWaitMillis, boolean testOnBorrow,int minIdle) {  
	        JedisPoolConfig poolCofig = new JedisPoolConfig();  
	        poolCofig.setMaxIdle(maxIdle);  
	        poolCofig.setMaxTotal(maxTotal);  
	        poolCofig.setMaxWaitMillis(maxWaitMillis);
	        poolCofig.setMinIdle(minIdle);
	        poolCofig.setTestOnBorrow(testOnBorrow);  
	        return poolCofig;  
	    }  
	
	
	

//	@Bean
//	public RedisTemplate<String, String> redisTemplate() {
//		
//		 // 池基本配置 
////	    JedisPoolConfig config = new JedisPoolConfig();
////	    config.setMaxTotal(100); 
////	    config.setMaxIdle(1); 
////	    config.setMaxWaitMillis(1); 
////	    config.setTestOnBorrow(1);
////	    jedisPool = new JedisPool(config, addr, port);
//	    JedisConnectionFactory factory = new JedisConnectionFactory();
//	    factory.setHostName("127.0.0.1");  
//	    factory.setPort(6379);  
//	    factory.setTimeout(1000);  
//	    factory.setDatabase(3);
//	    
//		StringRedisTemplate template = new StringRedisTemplate(factory);
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//		ObjectMapper om = new ObjectMapper();
//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//		template.setValueSerializer(jackson2JsonRedisSerializer);
//		template.afterPropertiesSet();
//		return template;
//	}

}
