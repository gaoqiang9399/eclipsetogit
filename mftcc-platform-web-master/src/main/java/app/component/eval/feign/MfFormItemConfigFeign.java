package  app.component.eval.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.MfFormItemConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfFormItemConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Nov 05 15:54:18 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfFormItemConfigFeign {
	
	@RequestMapping(value = "/mfFormItemConfig/insert")
	public void insert(@RequestBody MfFormItemConfig mfFormItemConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfFormItemConfig/delete")
	public void delete(@RequestBody MfFormItemConfig mfFormItemConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfFormItemConfig/update")
	public void update(@RequestBody MfFormItemConfig mfFormItemConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfFormItemConfig/getById")
	public MfFormItemConfig getById(@RequestBody MfFormItemConfig mfFormItemConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfFormItemConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfFormItemConfig") MfFormItemConfig mfFormItemConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得可用的表单项配置信息
	 * @param mfFormItemConfig
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2016-11-5 下午4:04:45
	 */
	@RequestMapping(value = "/mfFormItemConfig/getMfFormItemConfigList")
	public Map<String,Object> getMfFormItemConfigList(@RequestBody MfFormItemConfig mfFormItemConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据表单名获得可用的表单项配置信息
	 * @param mfFormItemConfig
	 * @return
	 * @throws ServiceException
	 * List<MfFormItemConfig>
	 * @author 沈浩兵
	 * @date 2016-11-7 下午4:10:32
	 */
	@RequestMapping(value = "/mfFormItemConfig/getFormNameEnList")
	public List<MfFormItemConfig> getFormNameEnList(@RequestBody MfFormItemConfig mfFormItemConfig) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取表单项列表，已表名分类，将相同表名的记录字段列拼接成一个字段，以逗号隔开
	 * @return
	 * @throws ServiceException
	 * List<Map<String,String>>
	 * @author zhs
	 * @date 2016-11-10 上午11:37:45
	 */
	@RequestMapping(value = "/mfFormItemConfig/getFormItemList")
	public List<Map<String, String>> getFormItemList()throws ServiceException;
	
}
