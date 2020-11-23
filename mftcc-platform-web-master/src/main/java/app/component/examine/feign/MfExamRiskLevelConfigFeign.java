package  app.component.examine.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.examine.entity.MfExamRiskLevelConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfExamRiskLevelConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jul 26 09:56:36 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamRiskLevelConfigFeign {
	@RequestMapping("/mfExamRiskLevelConfig/insert")
	public void insert(@RequestBody MfExamRiskLevelConfig mfExamRiskLevelConfig) throws Exception;
	@RequestMapping("/mfExamRiskLevelConfig/delete")
	public void delete(@RequestBody MfExamRiskLevelConfig mfExamRiskLevelConfig) throws Exception;
	@RequestMapping("/mfExamRiskLevelConfig/update")
	public MfExamRiskLevelConfig update(@RequestBody MfExamRiskLevelConfig mfExamRiskLevelConfig) throws Exception;
	@RequestMapping("/mfExamRiskLevelConfig/getById")
	public MfExamRiskLevelConfig getById(@RequestBody MfExamRiskLevelConfig mfExamRiskLevelConfig) throws Exception;
	@RequestMapping("/mfExamRiskLevelConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamRiskLevelConfig") MfExamRiskLevelConfig mfExamRiskLevelConfig) throws Exception;
	/**
	 * 
	 * 方法描述： 获得贷后风险级别配置信息
	 * @param mfExamRiskLevelConfig
	 * @return
	 * @throws Exception
	 * List<MfExamRiskLevelConfig>
	 * @author 沈浩兵
	 * @date 2017-7-26 上午10:01:10
	 */
	@RequestMapping("/mfExamRiskLevelConfig/getExamRiskLevelConfigList")
	public List<MfExamRiskLevelConfig> getExamRiskLevelConfigList(@RequestBody MfExamRiskLevelConfig mfExamRiskLevelConfig) throws Exception;
	
}
