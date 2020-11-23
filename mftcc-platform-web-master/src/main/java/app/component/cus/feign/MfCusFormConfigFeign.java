package  app.component.cus.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.entity.MfCusFormConfig;
import app.component.doc.entity.DocBizSceConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfCusFormConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue May 24 16:30:02 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusFormConfigFeign {
	
	@RequestMapping("/mfCusFormConfig/insert")
	public void insert(@RequestBody MfCusFormConfig mfCusFormConfig) throws Exception;
	
	@RequestMapping("/mfCusFormConfig/delete")
	public void delete(@RequestBody MfCusFormConfig mfCusFormConfig) throws Exception;
	
	@RequestMapping("/mfCusFormConfig/update")
	public void update(@RequestBody MfCusFormConfig mfCusFormConfig) throws Exception;
	
	@RequestMapping("/mfCusFormConfig/updateList")
	public void updateList(@RequestBody List<MfCusFormConfig> mfCusFormConfigList) throws Exception;
	
	@RequestMapping("/mfCusFormConfig/getById")
	public MfCusFormConfig getById(@RequestBody MfCusFormConfig mfCusFormConfig) throws Exception;
	
	@RequestMapping("/mfCusFormConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 根据模板，更新配置该客户类型的表单
	 * date 2016-9-22
	 * @param cusType
	 * @throws ServiceException
	 */
	@RequestMapping("/mfCusFormConfig/updateFormByCusType")
	public void updateFormByCusType(@RequestParam("cusType") String cusType) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 根据客户类型和action，获得该模块的表单信息；如果该客户类型的信息找不到，则查询模板表单信息；
	 * date 2016-9-26
	 * @param cusType
	 * @param action
	 * @return 如果查询不到结果，则返回null
	 * @throws ServiceException
	 */
	@RequestMapping("/mfCusFormConfig/getByCusType")
	public MfCusFormConfig getByCusType(@RequestParam("cusType") String cusType,@RequestParam("action") String action) throws Exception;
	
	/**
	 * @Description:根据客户类型,action,subformProperty获取子表单属性，获得该模块的表单信息；如果该客户类型的信息找不到，则查询模板表单信息； 
	 * @param cusType
	 * @param action
	 * @param subformProperty
	 * @return
	 * @throws ServiceException
	 * @author: 李伟
	 * @date: 2017-10-30 下午5:31:50
	 */
	@RequestMapping("/mfCusFormConfig/getSubFormByCusType")
	public MfCusFormConfig getSubFormByCusType(@RequestParam("cusType") String cusType,@RequestParam("action") String action,@RequestParam("subformProperty") String subformProperty) throws Exception;
	
	
	
	/**
	 * 
	 * 方法描述： 获得客户类型已配置表单信息 list
	 * @param mfCusFormConfig
	 * @return
	 * @throws ServiceException
	 * List<MfCusFormConfig>
	 * @author 沈浩兵
	 * @date 2016-5-26 下午6:04:12
	 */
	@RequestMapping("/mfCusFormConfig/getAll")
	public List<MfCusFormConfig> getAll(@RequestBody MfCusFormConfig mfCusFormConfig) throws Exception;
	/**
	 * 
	 * 方法描述： 获得客户表单信息总条数
	 * @param mfCusFormConfig
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2016-5-26 下午6:04:07
	 */
	@RequestMapping("/mfCusFormConfig/getAllCounts")
	public String getAllCounts(@RequestBody MfCusFormConfig mfCusFormConfig) throws Exception;
	/**
	 * 
	 * 方法描述：  获得客户类型已配置表单信息 表名拼接串
	 * @param mfCusFormConfig
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2016-5-26 下午6:05:21
	 */
	@RequestMapping("/mfCusFormConfig/getCusForms")
	public String getCusForms(@RequestBody MfCusFormConfig mfCusFormConfig) throws Exception;
	/**
	 * 
	 * 方法描述： 添加客户类型数据字典项
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-3-21 上午10:50:46
	 */
	@RequestMapping("/mfCusFormConfig/insertCusTypeConifg")
	public Map<String,Object> insertCusTypeConifg(@RequestBody Map<String,Object> dataMap) throws Exception;

	@RequestMapping("/mfCusFormConfig/insertDoc")
	public String insertDoc(@RequestParam("optCode") String optCode,@RequestParam("optName") String optName) throws Exception;

	@RequestMapping("/mfCusFormConfig/updateDoc")
	public void updateDoc(@RequestParam("optCode") String optCode,@RequestBody List<DocBizSceConfig> docBizSceConfigList) throws Exception;
	/**
	 * @Description: 修改表单useFlag标志(主表单修改useFlag,子表单也会修改)
	 * @param mfCusFormConfig
	 * @throws ServiceException
	 * @author: 李伟
	 * @date: 2017-11-2 下午3:28:25
	 */
	@RequestMapping("/mfCusFormConfig/updateSts")
	public void updateSts(@RequestBody MfCusFormConfig mfCusFormConfig) throws Exception;

	@RequestMapping("/mfCusFormConfig/getrefreshModle")
	public String getrefreshModle(@RequestParam("custype") String custype) throws Exception;

	@RequestMapping("/mfCusFormConfig/getFormTable")
	public String getFormTable(@RequestParam("id") String id) throws Exception;
}
