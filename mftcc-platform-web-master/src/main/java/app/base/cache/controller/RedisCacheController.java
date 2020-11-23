package app.base.cache.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.util.oscache.ScreenCache;

import app.base.cache.feign.BusiCacheFeign;
import app.base.cacheinterface.BusiCacheInterface;
import app.base.shiro.ShiroService;

@Controller
@RequestMapping("/redis")
public class RedisCacheController extends BaseFormBean {
	@Autowired
	private BusiCacheFeign busiCacheFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Value("${spring.shiro.switch}")
	private String shiroSwitch;
	@Autowired
	private ShiroService shiroService;
	@Autowired
	private BusiCacheInterface busiCacheInterface;
	@Autowired
	private RedisTemplate redisTemplate;

	@RequestMapping("/test/{key}")
	@ResponseBody
	public Collection<Object> test(@PathVariable("key") String key) throws Exception {

		return busiCacheInterface.getParmDic(key);
	}

	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String manage() throws Exception {
		ActionContext.initialize(request, response);
		return "sys/Cache_Manage";
	}

/*	@RequestMapping(value = "/refreshAllDB", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> refreshAllDB() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		busiCacheFeign.refreshAllDB();// 刷新按钮和数据字典
		if ("1".equals(shiroSwitch))
			shiroService.updatePermission();// 刷新shiro
		ScreenCache cache = new ScreenCache();
		cache.reloadFormXmlInCache();
		cache.reloadTableXmlInCache();
		map.put("result", true);
		return map;
	}*/

	@RequestMapping(value = "/initParmdic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initParmdic() throws Exception {

		busiCacheFeign.initParmdic();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		return map;
	}

	@RequestMapping(value = "/initButton", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initButton() throws Exception {
		busiCacheFeign.initButton();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		return map;
	}

	@RequestMapping(value = "/removeButton", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeButton() throws Exception {
		busiCacheFeign.removeButton();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		return map;
	}

	@RequestMapping(value = "/removeParmDic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeParmDic() throws Exception {
		busiCacheFeign.removeParmDic();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		return map;
	}

	@RequestMapping(value = "/refreshParmDic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> refreshParmDic() throws Exception {
		busiCacheFeign.refreshAllParmDic();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		return map;
	}

	@RequestMapping(value = "/refreshRoleButton", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> refreshAllButton() throws Exception {
		busiCacheFeign.refreshRoleButton();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		return map;
	}

	@RequestMapping(value = "/addParmDic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addParmDic(@RequestBody Map<String, Object> map) throws Exception {
		busiCacheFeign.addParmDic(map);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("result", true);
		return resultMap;
	}
}