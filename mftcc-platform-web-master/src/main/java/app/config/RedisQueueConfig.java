package app.config;


import app.base.redis.RedisQueue;
import app.base.redis.listener.NormalListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisQueueConfig {
	
	@Autowired
	private RedisTemplate messageRedisTemplate;
	
	@Value("${mftcc.messageListen.name}")
	private String messageListenName;
	
	@Bean
	public RedisQueue<String> exampleQueue() {
		RedisQueue<String> queue = new RedisQueue<String>();
		queue.setRedisTemplate(messageRedisTemplate);
		queue.setKey(messageListenName);
		queue.setListener(new NormalListener<String>());
		return queue;
	}

}
