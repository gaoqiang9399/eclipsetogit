package  app.component.examine.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.examine.entity.MfExamRiskConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfExamRiskConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Feb 16 15:01:00 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamRiskConfigFeign {
	@RequestMapping("/mfExamRiskConfig/insert")
	public void insert(@RequestBody MfExamRiskConfig mfExamRiskConfig) throws ServiceException;
	@RequestMapping("/mfExamRiskConfig/delete")
	public void delete(@RequestBody MfExamRiskConfig mfExamRiskConfig) throws ServiceException;
	@RequestMapping("/mfExamRiskConfig/update")
	public void update(@RequestBody MfExamRiskConfig mfExamRiskConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 更新风险模板状态 
	 * @param mfExamRiskConfig
	 * @return
	 * @throws ServiceException
	 * int
	 * @author 沈浩兵
	 * @date 2017-2-17 下午9:03:34
	 */
	@RequestMapping("/mfExamRiskConfig/updateSts")
	public int updateSts(@RequestBody MfExamRiskConfig mfExamRiskConfig) throws ServiceException;
	@RequestMapping("/mfExamRiskConfig/getById")
	public MfExamRiskConfig getById(@RequestBody MfExamRiskConfig mfExamRiskConfig) throws ServiceException;
	@RequestMapping("/mfExamRiskConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamRiskConfig") MfExamRiskConfig mfExamRiskConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得检查风险模型
	 * @param mfExamRiskConfig
	 * @return
	 * @throws ServiceException
	 * List<MfExamRiskConfig>
	 * @author 沈浩兵
	 * @date 2017-2-17 上午9:44:25
	 */
	@RequestMapping("/mfExamRiskConfig/getExamRiskConfigList")
	public List<MfExamRiskConfig> getExamRiskConfigList(@RequestBody MfExamRiskConfig mfExamRiskConfig) throws ServiceException;
	
}
