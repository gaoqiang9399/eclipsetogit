package  app.component.examine.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.examine.entity.MfExamRemindConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfExamRemindConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Feb 10 14:50:22 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamRemindConfigFeign {
	@RequestMapping("/MfExamineTemplateIndexRel/insert")
	public void insert(@RequestBody MfExamRemindConfig mfExamRemindConfig) throws ServiceException;
	@RequestMapping("/MfExamineTemplateIndexRel/delete")
	public void delete(@RequestBody MfExamRemindConfig mfExamRemindConfig) throws ServiceException;
	@RequestMapping("/MfExamineTemplateIndexRel/update")
	public void update(@RequestBody MfExamRemindConfig mfExamRemindConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 修改禁用启用状态
	 * @param mfExamRemindConfig
	 * @return
	 * @throws ServiceException
	 * int
	 * @author 沈浩兵
	 * @date 2017-2-13 上午10:07:01
	 */
	@RequestMapping("/MfExamineTemplateIndexRel/updateSts")
	public int updateSts(@RequestBody MfExamRemindConfig mfExamRemindConfig) throws ServiceException;
	@RequestMapping("/MfExamineTemplateIndexRel/getById")
	public MfExamRemindConfig getById(@RequestBody MfExamRemindConfig mfExamRemindConfig) throws ServiceException;
	@RequestMapping("/MfExamineTemplateIndexRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamRemindConfig") MfExamRemindConfig mfExamRemindConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得检查提醒列表
	 * @param mfExamRemindConfig
	 * @return
	 * @throws ServiceException
	 * List<MfExamRemindConfig>
	 * @author 沈浩兵
	 * @date 2017-2-13 上午11:01:46
	 */
	@RequestMapping("/MfExamineTemplateIndexRel/getMfExamRemindConfigList")
	public List<MfExamRemindConfig> getMfExamRemindConfigList(@RequestBody MfExamRemindConfig mfExamRemindConfig) throws ServiceException;
}
