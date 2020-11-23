package app.component.model.feign;

import app.component.model.entity.MfTagColumnConfig;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
* Title: MfTagColumnConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jan 05 15:14:28 CST 2018
**/

@FeignClient("mftcc-platform-templete-tag")
public interface MfNewTagColumnConfigFeign {
	
	@RequestMapping(value = "/mfTagColumnConfig/insert")
	public void insert(@RequestBody MfTagColumnConfig mfTagColumnConfig) throws Exception;
	
	@RequestMapping(value = "/mfTagColumnConfig/delete")
	public void delete(@RequestBody MfTagColumnConfig mfTagColumnConfig) throws Exception;
	
	@RequestMapping(value = "/mfTagColumnConfig/update")
	public void update(@RequestBody MfTagColumnConfig mfTagColumnConfig) throws Exception;
	
	@RequestMapping(value = "/mfTagColumnConfig/getById")
	public MfTagColumnConfig getById(@RequestBody MfTagColumnConfig mfTagColumnConfig) throws Exception;
	
	@RequestMapping(value = "/mfTagColumnConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述：获得 获得标签表格列对应字段信息
	 * @param mfTagColumnConfig
	 * @return
	 * @throws Exception
	 * List<MfTagColumnConfig>
	 * @author 沈浩兵
	 * @date 2018-1-5 下午4:33:07
	 */
	@RequestMapping(value = "/mfTagColumnConfig/getMfTagColumnConfigList")
	public List<MfTagColumnConfig> getMfTagColumnConfigList(@RequestBody MfTagColumnConfig mfTagColumnConfig) throws Exception;
	/**
	 * 
	 * 方法描述： 获得 获得标签表格列对应字段信息 Map
	 * @param mfTagColumnConfig
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018-1-5 下午4:40:33
	 */
	@RequestMapping(value = "/mfTagColumnConfig/getMfTagColumnConfigMap")
	public Map<String,Object> getMfTagColumnConfigMap(@RequestBody MfTagColumnConfig mfTagColumnConfig) throws Exception;
	/**
	 * 
	 * 方法描述： 获得字典分组和拼接信息
	 * @param mfTagColumnConfig
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018-1-11 上午11:40:52
	 */
	@RequestMapping(value = "/mfTagColumnConfig/getColumnGroupInfoMap")
	public Map<String,Object> getColumnGroupInfoMap(@RequestBody MfTagColumnConfig mfTagColumnConfig) throws Exception;
	
}
