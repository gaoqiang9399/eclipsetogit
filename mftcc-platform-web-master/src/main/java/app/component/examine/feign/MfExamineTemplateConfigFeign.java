package  app.component.examine.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.doc.entity.DocBizSceConfig;
import app.component.examine.entity.MfExamineTemplateConfig;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
* Title: MfExamineTemplateConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Feb 09 15:35:54 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamineTemplateConfigFeign {
	@RequestMapping("/mfExamineTemplateConfig/insert")
	public MfExamineTemplateConfig insert(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws ServiceException;
	@RequestMapping("/mfExamineTemplateConfig/delete")
	public void delete(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws ServiceException;
	@RequestMapping("/mfExamineTemplateConfig/update")
	public void update(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 开关修改启用禁用状态
	 * @param mfExamineTemplateConfig
	 * @return
	 * @throws ServiceException
	 * int
	 * @author 沈浩兵
	 * @date 2017-2-10 上午10:18:35
	 */
	@RequestMapping("/mfExamineTemplateConfig/updateSts")
	public int updateSts(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws ServiceException;
	@RequestMapping("/mfExamineTemplateConfig/getById")
	public MfExamineTemplateConfig getById(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws ServiceException;
	@RequestMapping("/mfExamineTemplateConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamineTemplateConfig") MfExamineTemplateConfig mfExamineTemplateConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得匹配到的检查模板配置信息
	 * @param mfExamineTemplateConfig
	 * @return
	 * @throws ServiceException
	 * List<MfExamineTemplateConfig>
	 * @author 沈浩兵
	 * @date 2017-2-13 下午2:37:02
	 */
	@RequestMapping("/mfExamineTemplateConfig/getConfigMatchedList")
	public List<MfExamineTemplateConfig> getConfigMatchedList(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得匹配到的检查模板配置信息
	 * @param formData
	 * @return
	 * @throws ServiceException
	 * List<MfExamineTemplateConfig>
	 * @author 沈浩兵
	 * @date 2017-2-13 下午5:43:51
	 */
	@RequestMapping("/mfExamineTemplateConfig/getConfigMatchedList")
	public List<MfExamineTemplateConfig> getConfigMatchedList(@RequestParam("relId") String relId,@RequestParam("entFlag") String entFlag) throws ServiceException;
	/**
	 * 
	 * 方法描述：获取所有可用检查模型
	 * @param formData
	 * @return
	 * @throws ServiceException
	 * List<MfExamineTemplateConfig>
	 * @author 呼艳明
	 * @date 2017-2-13 下午5:43:51
	 */
	@RequestMapping("/mfExamineTemplateConfig/getAllExamineTemplate")
	public List<MfExamineTemplateConfig> getAllExamineTemplate() throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得匹配到的可用的检查模板配置信息
	 * @param mfExamineTemplateConfig
	 * @return
	 * @throws Exception
	 * List<MfExamineTemplateConfig>
	 * @author 沈浩兵
	 * @date 2017-7-12 上午11:32:40
	 */
	@RequestMapping("/mfExamineTemplateConfig/getUseableConfigMatchedList")
	public List<MfExamineTemplateConfig> getUseableConfigMatchedList(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws Exception;
	/**
	 * 
	 * 方法描述： 打开自定义表单，如果没有配置表单，生成自定义表单。
	 * @param templateId
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-7-18 下午2:40:21
	 */
	@RequestMapping("/mfExamineTemplateConfig/getOpenFormTemplate")
	public String getOpenFormTemplate(@RequestBody String templateId) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得检查检查卡配置信息
	 * @param templateId
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-7-24 下午4:12:56
	 */
	@RequestMapping("/mfExamineTemplateConfig/getListData")
	public Map<String,Object> getListData(@RequestBody String templateId) throws ServiceException;
	@RequestMapping("/mfExamineTemplateConfig/updateDoc")
	public void updateDoc(@RequestParam("optCode") String optCode,@RequestBody List<DocBizSceConfig> docBizSceConfigList);
	/**
	 * 
	 * 方法描述： 获得检查模型要件配置信息和模板配置信息
	 * @return
	 * @throws ServiceException
	 * JSONObject
	 * @author 沈浩兵
	 * @date 2017-8-5 上午11:39:46
	 */
	@RequestMapping("/mfExamineTemplateConfig/getDocAndUploadConfigData")
	public JSONObject getDocAndUploadConfigData() throws Exception;
	/**
	 * 
	 * 方法描述：获得展期业务配置相关 
	 * @return
	 * @throws Exception
	 * JSONObject
	 * @author 沈浩兵
	 * @date 2017-9-8 下午5:06:10
	 */
	@RequestMapping("/mfExamineTemplateConfig/getExtensionConfigData")
	public JSONObject getExtensionConfigData() throws Exception;

	/**
	 *
	 * 方法描述：获取检查模型
	 * @param mfExamineTemplateConfig
	 * @return
	 * @throws ServiceException
	 * List<MfExamineTemplateConfig>
	 */
	@RequestMapping("/mfExamineTemplateConfig/getExamineTemplateList")
	public List<MfExamineTemplateConfig> getExamineTemplateList(@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws ServiceException;
}
