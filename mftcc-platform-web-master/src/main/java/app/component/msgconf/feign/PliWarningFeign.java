package  app.component.msgconf.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.msgconf.entity.MfExamineTemplateConfig;
import app.component.msgconf.entity.PliWarning;
import app.util.toolkit.Ipage;

/**
* Title: PliWarningBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jul 11 10:38:28 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PliWarningFeign {
	
	@RequestMapping(value = "/pliWarning/insert")
	public void insert(@RequestBody PliWarning pliWarning) throws ServiceException;
	
	@RequestMapping(value = "/pliWarning/delete")
	public void delete(@RequestBody PliWarning pliWarning) throws ServiceException;
	
	@RequestMapping(value = "/pliWarning/update")
	public void update(@RequestBody PliWarning pliWarning) throws ServiceException;
	
	@RequestMapping(value = "/pliWarning/updateFlag")
	public int updateFlag(@RequestBody PliWarning pliWarning) throws ServiceException;
	
	@RequestMapping(value = "/pliWarning/getById")
	public PliWarning getById(@RequestBody PliWarning pliWarning) throws ServiceException;
	
	@RequestMapping(value = "/pliWarning/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/pliWarning/getAll")
	public List<PliWarning> getAll(@RequestBody PliWarning pliWarning) throws ServiceException;
	
	@RequestMapping(value = "/pliWarning/getMfExamineTemplateConfig")
	public List<MfExamineTemplateConfig> getMfExamineTemplateConfig(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws  ServiceException;
	/**
	 * 
	 * 方法描述： 查询贷后检查模型中没有设置匹配条件的检查模型
	 * @param mfExamineTemplateConfig
	 * @return
	 * @throws ServiceException
	 * List<MfExamineTemplateConfig>
	 * @author 沈浩兵
	 * @date 2017-10-9 上午10:44:05
	 */
	@RequestMapping(value = "/pliWarning/getExamineTemplateByNoKind")
	public List<MfExamineTemplateConfig> getExamineTemplateByNoKind(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws  ServiceException;
}
