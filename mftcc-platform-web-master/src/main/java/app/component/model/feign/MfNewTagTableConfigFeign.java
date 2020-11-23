package app.component.model.feign;

import app.component.model.entity.MfTagTableConfig;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
* Title: MfTagTableConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jan 05 14:50:42 CST 2018
**/

@FeignClient("mftcc-platform-templete-tag")
public interface MfNewTagTableConfigFeign {
	
	@RequestMapping(value = "/mfTagTableConfig/insert")
	public void insert(@RequestBody MfTagTableConfig mfTagTableConfig) throws Exception;
	
	@RequestMapping(value = "/mfTagTableConfig/delete")
	public void delete(@RequestBody MfTagTableConfig mfTagTableConfig) throws Exception;
	
	@RequestMapping(value = "/mfTagTableConfig/update")
	public void update(@RequestBody MfTagTableConfig mfTagTableConfig) throws Exception;
	
	@RequestMapping(value = "/mfTagTableConfig/getById")
	public MfTagTableConfig getById(@RequestBody MfTagTableConfig mfTagTableConfig) throws Exception;
	
	@RequestMapping(value = "/mfTagTableConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	/**
	 * 
	 * 方法描述： 获得标签表格配置信息
	 * @param mfTagTableConfig
	 * @return
	 * @throws Exception
	 * List<MfTagTableConfig>
	 * @author 沈浩兵
	 * @date 2018-1-5 下午3:31:00
	 */
	@RequestMapping(value = "/mfTagTableConfig/getMfTagTableConfigLsit")
	public List<MfTagTableConfig> getMfTagTableConfigLsit(@RequestBody MfTagTableConfig mfTagTableConfig) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据标签编号获得表格的最大行数和列数
	 * @param keyNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018-1-8 下午10:18:45
	 */
	@RequestMapping(value = "/mfTagTableConfig/getLineNumStrByKeyNo")
	public Map<String, Object> getLineNumStrByKeyNo(@RequestBody String keyNo) throws Exception;
	
}
