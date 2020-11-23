package app.base.cache.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("mftcc-platform-factor")
public interface BusiCacheFeign {
//	@RequestMapping(value = "/busiCache/refreshAllDB", method = RequestMethod.POST)
//	public void refreshAllDB();

	@RequestMapping(value = "/busiCache/initParmdic", method = RequestMethod.POST)
	public void initParmdic();

	@RequestMapping(value = "/busiCache/initButton", method = RequestMethod.POST)
	public void initButton();

	@RequestMapping(value = "/busiCache/removeButton", method = RequestMethod.POST)
	public void removeButton();

	@RequestMapping(value = "/busiCache/removeParmDic", method = RequestMethod.POST)
	public void removeParmDic();

	@RequestMapping(value = "/busiCache/refreshParmDic", method = RequestMethod.POST)
	public void refreshParmDic(@RequestBody String key);

	@RequestMapping(value = "/busiCache/addParmDic", method = RequestMethod.POST)
	public void addParmDic(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/busiCache/refreshAllParmDic", method = RequestMethod.POST)
	public void refreshAllParmDic();
	
	@RequestMapping(value = "/busiCache/reLoadDescTempCache", method = RequestMethod.POST)
	public void reLoadDescTempCache()throws Exception ;
	
	@RequestMapping(value = "/busiCache/refreshRoleButton", method = RequestMethod.POST)
	public void refreshRoleButton();
	
	@RequestMapping(value = "/busiCache/refreshSysRoleButtonCache")
	public void refreshSysRoleButtonCache() throws Exception ;
	
	@RequestMapping(value = "/busiCache/initPmsDataRangCache" , method = RequestMethod.POST)
	public void initPmsDataRangCache() throws Exception ;


	@RequestMapping(value = "/busiCache/refreshEncryptCache", method = RequestMethod.POST)
	public void refreshEncryptCache();
}
