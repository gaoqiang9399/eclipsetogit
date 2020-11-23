/**
 * Copyright (C)  版权所有
 * 文件名： CacheUtil.java
 * 包名： cn.mftcc.util
 * 说明：
 * @author YuShuai
 * @date 2017-11-1 下午4:27:23
 * @version V1.0
 */
package cn.mftcc.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import app.base.SpringUtil;
import app.base.cacheinterface.CacheInterface;
import app.tech.oscache.CodeUtils;

/**
 * 类名： CacheUtil 描述：静态变量调用redis还是系统现有缓存
 * 
 * @author YuShuai
 * @date 2017-11-1 下午4:27:23
 * 
 * 
 */
public class CacheUtil {

	private static final String isRedisCache = StringUtil
			.KillEmpty(PropertiesUtil.getWebServiceProperty("isRedisCache"), "0");// 是否启用redis缓存
																					// 1
																					// 是
																					// 0否
	private static int _suffix;
	private static CodeUtils codeUtils = new CodeUtils();

	/**
	 * 获取流水号的同步锁。
	 */
	private static byte[] lock = new byte[0];

	/**
	 * 方法描述： 根据缓存中的cacheKey 获取该结果map
	 * 
	 * @param cache_key
	 *            缓存中的key
	 * @return Map<String,Object>
	 * @author YuShuai
	 * @throws Exception
	 * @date 2017-11-1 下午6:29:04
	 */
	public static <T> Map<String, T> getMapByKey(CACHE_KEY cache_key) throws Exception {

		Map<String, T> map = null;
		CacheInterface cacheInterface = SpringUtil.getBean(CacheInterface.class);
		 RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplate");
		map = (Map<String, T>) cacheInterface.getObject(redisTemplate,cache_key.getCode());
		// map = RedisUtil.getMap(cache_key.getCode());

		if (map == null) {
			map = new HashMap<String, T>();
		}
		return map;

	}

	/**
	 * 方法描述： 向缓存的cachekey的map中存数据
	 * 
	 * @param key
	 *            map.key
	 * @param value
	 *            map.value
	 * @param cachekey
	 *            cache中的key void
	 * @author YuShuai
	 * @throws Exception
	 * @date 2017-11-1 下午6:28:35
	 */
	public static void putMapkeyInCache(String key, Object value, CACHE_KEY cache_key) throws Exception {
		CacheInterface cacheInterface =  SpringUtil.getBean(CacheInterface.class);
		RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplate");
		Map<String, Object> map = (Map<String, Object>) cacheInterface.getObject(redisTemplate,cache_key.getCode());

		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put(key, value);
		cacheInterface.setObject(redisTemplate,cache_key.getCode(), map);

	}

	/**
	 * 方法描述：
	 * 
	 * @param cachekey
	 *            cache中的key void
	 * @author YuShuai
	 * @throws Exception
	 * @date 2017-11-1 下午6:28:35
	 */
	public static void removeCache(CACHE_KEY cache_key) throws Exception {

		CacheInterface cacheInterface = (CacheInterface) SpringUtil.getBean(CacheInterface.class);
		RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplate");
		cacheInterface.removeObject(redisTemplate,cache_key.getCode());

	}

	/**
	 * 方法描述： 删除map中的key
	 * 
	 * @param key
	 * @param cachekey
	 *            void
	 * @author YuShuai
	 * @throws Exception
	 * @date 2017-11-2 上午11:22:05
	 */
	public static <T> void remMapKeyCache(String key, CACHE_KEY cache_key) throws Exception {
		CacheInterface cacheInterface = (CacheInterface) SpringUtil.getBean(CacheInterface.class);
		RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplate");
		Map<String, T> map = (Map<String, T>) cacheInterface.getObject(redisTemplate,cache_key.getCode());
		if (map != null && map.containsKey(key)) {
			map.remove(key);
			cacheInterface.setObject(redisTemplate,cache_key.getCode(), map);
		}

	}

	/**
	 * 方法描述： 获取obj缓存
	 * 
	 * @param key
	 * @return Object
	 * @author YuShuai
	 * @throws Exception 
	 * @date 2017-11-2 下午2:20:17
	 */
	public static Object getCacheObject(CACHE_KEY cache_key) throws Exception {
		CacheInterface cacheInterface =  SpringUtil.getBean(CacheInterface.class);
		RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplate");
		Object object = null;
		object = cacheInterface.getObject(redisTemplate,cache_key.getCode());
		return object;
	}

	/**
	 * 方法描述：
	 * 
	 * @param key
	 * @param obj
	 *            void
	 * @author YuShuai
	 * @throws Exception 
	 * @date 2017-11-2 下午2:29:10
	 */
	public static void putCacheObject(Object obj, CACHE_KEY cache_key) throws Exception {
		CacheInterface cacheInterface = (CacheInterface) SpringUtil.getBean(CacheInterface.class);
		RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplate");
		cacheInterface.setObject(redisTemplate,cache_key.getCode(), obj);
	}


	/**
	 * 数字类型枚举
	 */
	public static enum CACHE_KEY {
		/**
		 *手机号
		 */
		phoneMap("phoneMap", "手机号"),
		/**
		 *ip
		 */
		ipMap("ipMap", "ip"),
		/**
		 *手机号验证码
		 */
		telStoreMap("telStoreMap", "手机号验证码"),
		/**
		 *合同号
		 */
		pactNoStatic("pactNoStatic", "合同号"),
		/**
		 *合同号
		 */
		followPactNoMap("followPactNoMap", "合同号"),
		/**
		 *借据号
		 */
		fincShowNoMap("fincShowNoMap", "借据号"),
		/**
		 *客户号
		 */
		cusNoMap("cusNoMap", "客户号"),
		/**
		 *操作员
		 */
		hUserName("hUserName", "操作员"),
		/**
		 *当前操作员
		 */
		mpOper("mpOper", "当前操作员"),
		/**
		 *流水号
		 */
		waterId_suffix("waterId_suffix", "流水号"),
		/**
		 *手机号
		 */
		applyMap("applyMap", "手机号"),
		/**
		 *css、js等文件的缓存版本号
		 */
		CSS_JS_VERSION("css_js_version", "css、js等文件的缓存版本号，每次重启重新生成"),
		/**
		 *标的
		 */
		bidissueMap("bidissueMap", "标的");

		private String code;
		private String name;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		private CACHE_KEY(String code, String name) {
			this.name = name;
			this.code = code;
		}

		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).code;
		}

		public static final String getTypeName(String code) {
			return fromValue(code).name;
		}

		public static final CACHE_KEY fromValue(String inputStr) {
			for (CACHE_KEY c : CACHE_KEY.values()) {
				if (c.name.equals(inputStr) || c.code.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("枚举参数错误，输入参数：" + inputStr);
		}

		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (CACHE_KEY c : CACHE_KEY.values()) {
				resMap.put(c.code, c.name);
			}
			return resMap;
		}

	}

}
