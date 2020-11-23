package app.base.cache.interfaceimpl;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import app.base.cacheinterface.CacheInterface;


@Component("cacheInterface")
public class CacheForRedis implements CacheInterface {


	private ListOperations<Object, Object> listOper;

	private ValueOperations<Object, Object> valueOper;

	private JedisConnectionFactory jedisConFaction;

	public CacheForRedis() {
	}

	@Override
	public void setList(RedisTemplate redisTemplate,Object key, Collection<Object> collection) throws Exception {
		try {
			this.listOper = redisTemplate.opsForList();
			listOper.rightPushAll(key, collection);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Redis插入集合数据失败",e);
		}

	}

	@Override
	public void setObject(RedisTemplate redisTemplate,Object key, Object object) throws Exception {
		try {
			this.valueOper = redisTemplate.opsForValue();
			valueOper.set(key, object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Redis插入单个数据失败",e);
		}

	}

	@Override
	public List<Object> getList(RedisTemplate redisTemplate,Object key) throws Exception {
		List<Object> objects = null;
		try {
			this.listOper = redisTemplate.opsForList();
			objects = listOper.range(key, 0, -1);
		} catch (Exception e) {
			throw new Exception("Redis获取List失败",e);
		}
		return objects;
	}

	@Override
	public Object getObject(RedisTemplate redisTemplate,Object key) throws Exception {
		Object obj = null;
		try {
			this.valueOper = redisTemplate.opsForValue();
			obj = valueOper.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Redis获取对象失败",e);
		}
		return obj;
	}

	@Override
	public void removeObject(RedisTemplate redisTemplate,Object key) throws Exception {
		try {
			if(redisTemplate.hasKey(key)){
				redisTemplate.delete(key);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除Redis库中对象失败",e);
		}

	}

	@Override
	public void refreshDbSelected(RedisTemplate redisTemplate) throws Exception {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return true;
			}
		});
	}
	

}
