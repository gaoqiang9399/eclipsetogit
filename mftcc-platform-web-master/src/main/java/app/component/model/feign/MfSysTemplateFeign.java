package  app.component.model.feign;

import java.util.List;
import java.util.Map;

import app.component.model.entity.MfSysTemplate;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfSysTemplateBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Nov 22 11:14:05 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfSysTemplateFeign {
	
	@RequestMapping(value = "/mfSysTemplate/insert")
	public MfSysTemplate insert(@RequestBody MfSysTemplate mfSysTemplate) throws Exception;
	
	@RequestMapping(value = "/mfSysTemplate/delete")
	public void delete(@RequestBody MfSysTemplate mfSysTemplate) throws ServiceException;
	
	@RequestMapping(value = "/mfSysTemplate/update")
	public void update(@RequestBody MfSysTemplate mfSysTemplate) throws ServiceException;
	/**
	 * 
	 * 方法描述： 修改状态
	 * @param mfSysTemplate
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2016-11-30 下午4:52:39
	 */
	@RequestMapping(value = "/mfSysTemplate/updateStr")
	public void updateStr(@RequestBody MfSysTemplate mfSysTemplate) throws ServiceException;
	
	@RequestMapping(value = "/mfSysTemplate/getById")
	public MfSysTemplate getById(@RequestBody MfSysTemplate mfSysTemplate) throws ServiceException;
	
	@RequestMapping(value = "/mfSysTemplate/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得所有模板信息
	 * @param mfSysTemplate
	 * @return
	 * @throws ServiceException
	 * List<LoanTemplateTagBase>
	 * @author 沈浩兵
	 * @date 2016-9-7 下午4:36:05
	 */
	@RequestMapping(value = "/mfSysTemplate/getAll")
	public List<MfSysTemplate> getAll(@RequestBody MfSysTemplate mfSysTemplate) throws ServiceException;

	/**
	 * 
	 * 方法描述： 模板里的书签设置页面
	 * @param fileName
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author lwq
	 * @date 2017-8-29 下午5:14:48
	 */
	@RequestMapping(value = "/mfSysTemplate/getLoantempTagBase")
	public List<Map<String, Object>> getLoantempTagBase(@RequestBody String fileName,@RequestParam("map") Map<String, String> map)throws ServiceException;
	/**
	 * 
	 * 方法描述： 
	 * @param mfSysTemplate
	 * @return
	 * @throws ServiceException
	 * JSONArray
	 * @author 沈浩兵
	 * @date 2017-8-7 上午11:57:51
	 */
	@RequestMapping(value = "/mfSysTemplate/getAllTemplateList")
	public JSONArray getAllTemplateList(@RequestBody MfSysTemplate mfSysTemplate,@RequestParam("templateNoStr") String templateNoStr) throws Exception;
	
	/**
	 * 
	 * 方法描述： 获得模板列表。倒叙
	 * @param mfSysTemplate
	 * @return
	 * @throws Exception
	 * List<MfSysTemplate>
	 * @author 沈浩兵
	 * @date 2017-12-21 下午5:44:56
	 */
	@RequestMapping(value = "/mfSysTemplate/getTemplateListByDesc")
	public List<MfSysTemplate> getTemplateListByDesc(@RequestBody MfSysTemplate mfSysTemplate) throws Exception;
	/**
	 * 
	 * 方法描述： 获得模板列表。JSONArray,选择组件使用
	 * @param mfSysTemplate
	 * @return
	 * @throws ServiceException
	 * JSONArray
	 * @author 沈浩兵
	 * @date 2017-12-22 下午1:49:00
	 */
	@RequestMapping(value = "/mfSysTemplate/getJSONArray")
	public JSONArray getJSONArray(@RequestBody MfSysTemplate mfSysTemplate) throws Exception;
	/**
	 * 方法描述： 根据模板编号获取模板文件名
	 * @param templateNo
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018-1-26 下午3:53:17
	 */
	@RequestMapping(value = "/mfSysTemplate/getTemplateFileNameById")
	public String getTemplateFileNameById(@RequestBody String templateNo)throws Exception;

	
}
