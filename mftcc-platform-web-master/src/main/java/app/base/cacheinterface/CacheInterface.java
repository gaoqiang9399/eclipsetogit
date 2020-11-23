package app.base.cacheinterface;

import java.util.Collection;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;

public interface CacheInterface {
	
	public void setList(RedisTemplate redisTemplate,Object key,Collection<Object> collection) throws Exception;
	
	public void setObject(RedisTemplate redisTemplate,Object key,Object object) throws Exception;
	
	public List<Object> getList(RedisTemplate redisTemplate,Object key) throws Exception;
	
	public Object getObject(RedisTemplate redisTemplate,Object key) throws Exception;
	
	public void  removeObject(RedisTemplate redisTemplate,Object key) throws Exception;
	
	public void refreshDbSelected(RedisTemplate redisTemplate) throws Exception;
	
}
