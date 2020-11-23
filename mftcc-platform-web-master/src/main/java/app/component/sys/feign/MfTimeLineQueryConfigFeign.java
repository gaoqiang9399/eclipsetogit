package  app.component.sys.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.sys.entity.MfTimeLineQueryConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfTimeLineQueryConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Nov 24 18:01:55 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfTimeLineQueryConfigFeign {
	@RequestMapping("/mfTimeLineQueryConfig/insert")
	public void insert(@RequestBody MfTimeLineQueryConfig mfTimeLineQueryConfig) throws Exception;
	@RequestMapping("/mfTimeLineQueryConfig/delete")
	public void delete(@RequestBody MfTimeLineQueryConfig mfTimeLineQueryConfig) throws Exception;
	@RequestMapping("/mfTimeLineQueryConfig/update")
	public void update(@RequestBody MfTimeLineQueryConfig mfTimeLineQueryConfig) throws Exception;
	@RequestMapping("/mfTimeLineQueryConfig/getById")
	public MfTimeLineQueryConfig getById(@RequestBody MfTimeLineQueryConfig mfTimeLineQueryConfig) throws Exception;
	@RequestMapping("/mfTimeLineQueryConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 获得时间查询配置信息
	 * @param mfTimeLineQueryConfig
	 * @return
	 * @throws Exception
	 * List<MfTimeLineQueryConfig>
	 * @author 沈浩兵
	 * @date 2017-11-24 下午6:08:26
	 */
	@RequestMapping("/mfTimeLineQueryConfig/getTimeLineQueryConfigList")
	public List<MfTimeLineQueryConfig> getTimeLineQueryConfigList(@RequestBody MfTimeLineQueryConfig mfTimeLineQueryConfig) throws Exception;
	/**
	 * 
	 * 方法描述： 获得时间查询配置信息{'1小时内-h1','2小时内-h2'.....}
	 * @param mfTimeLineQueryConfig
	 * @return
	 * @throws Exception
	 * List<String>
	 * @author 沈浩兵
	 * @date 2017-11-24 下午6:27:14
	 */
	@RequestMapping("/mfTimeLineQueryConfig/getTimeLineQueryList")
	public List<String> getTimeLineQueryList(@RequestBody MfTimeLineQueryConfig mfTimeLineQueryConfig) throws Exception;
}
