package app.base.cache.interfaceimpl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import app.base.cacheinterface.BusiCacheInterface;
import app.base.cacheinterface.CacheInterface;
import app.component.sys.entity.SysUser;

@Component
public class BusiRedisCache implements BusiCacheInterface {

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private RedisTemplate sessionUserRedisTemplate;
	@Autowired
	private RedisTemplate paramRedisTemplate;
	@Autowired
	private RedisTemplate btnRedisTemplate;
	@Autowired
	private RedisTemplate pmsRedisTemplate;

	@Autowired
	private CacheInterface cacheForRedis;
	
	@Override
	public void removeButton() throws Exception {
		try {
			cacheForRedis.refreshDbSelected(btnRedisTemplate);
		} catch (Exception e) {
			throw new Exception("删除缓存中数据字典项失败");
		}
	}

	@Override
	public List<Object> getParmDic(String key) throws Exception {
		return cacheForRedis.getList(paramRedisTemplate,key);
	}
	
	@Override
	public void delParmDic(Object key) throws Exception {
		cacheForRedis.removeObject(paramRedisTemplate,key);
	}

	@Override
	public void addParmDic(Object key, Object object) throws Exception {
		cacheForRedis.setObject(paramRedisTemplate,key, object);
	}
	
	@Override
	public List<Object> getParmDicList(Object key) throws Exception {
		List<Object> list = cacheForRedis.getList(paramRedisTemplate,key);
		return list;
	}
	@Override
	public Object getParmDicObj(Object key) throws Exception {
		return cacheForRedis.getObject(paramRedisTemplate,key);
	}
	
	@Override
	public Object getPmsObj(Object key) throws Exception {
		return cacheForRedis.getObject(pmsRedisTemplate,key);
	}
	
	@Override
	public void setSessionAttr(String key, SysUser sysUser) {
		try {
			cacheForRedis.setObject(sessionUserRedisTemplate,key, sysUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setParmDic(Object key,Object value) throws Exception {
		if(value instanceof Collection){
			cacheForRedis.setList(paramRedisTemplate,key,(Collection<Object>) value);
		}else{
			cacheForRedis.setObject(paramRedisTemplate,key,value);
		}
		
	}

	//
	//
	// @Override
	// public void refreshParmDic(Object key) throws Exception {
	// Integer dbIndex = Integer.parseInt(parmDicDataBaseIndex);
	// cacheForRedis.selectDb(dbIndex);
	// cacheForRedis.removeObject(key);
	// List<Object> list = cacheService.findByCondition(key);
	// if (list != null && list.size() > 0)
	// cacheForRedis.setList(key, list);
	// }
	
	
	@Override
	public List<Object> getNmdWayList(Object key) throws Exception {
		List<Object> list = cacheForRedis.getList(paramRedisTemplate,key);
		return list;
	}

}
