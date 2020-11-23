package  app.component.eval.feign;
import java.util.List;
import java.util.Map;

import app.component.eval.entity.EvalScenceConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.AppProperty;
import app.util.toolkit.Ipage;

/**
* Title: AppPropertyBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Mar 29 01:44:29 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface AppPropertyFeign {
	
	@RequestMapping(value = "/appProperty/insert")
	public AppProperty insert(@RequestBody AppProperty appProperty) throws ServiceException;
	
	@RequestMapping(value = "/appProperty/delete")
	public void delete(@RequestBody AppProperty appProperty) throws ServiceException;
	
	@RequestMapping(value = "/appProperty/update")
	public void update(@RequestBody AppProperty appProperty) throws ServiceException;
	
	@RequestMapping(value = "/appProperty/updateSts")
	public int updateSts(@RequestBody AppProperty appProperty) throws ServiceException;
	
	@RequestMapping(value = "/appProperty/getById")
	public AppProperty getById(@RequestBody AppProperty appProperty) throws ServiceException;
	
	@RequestMapping(value = "/appProperty/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("appProperty") AppProperty appProperty) throws ServiceException;

	@RequestMapping(value = "/appProperty/getAll")
	public List<AppProperty> getAll(@RequestBody AppProperty appProperty) throws ServiceException;
	
	@RequestMapping(value = "/appProperty/getAppProperty")
	public List<AppProperty> getAppProperty(@RequestBody Map<String,String> map) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得财务和定性的评级指标
	 * @param map
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-6-23 上午8:43:37
	 */
	@RequestMapping(value = "/appProperty/getAppPropertyByTableFlag")
	public Map<String,Object> getAppPropertyByTableFlag(@RequestBody Map<String,String> map) throws ServiceException;

	/**
	*@Description: 获取使用该指标项的评级模型
	*@Author: lyb
	*@date: 2019/8/29
	*/
	@RequestMapping(value = "/appProperty/getEvalScenceConfigListByAppPro")
	public List<EvalScenceConfig> getEvalScenceConfigListByAppPro(@RequestBody AppProperty appProperty) throws ServiceException;

	@RequestMapping(value = "/appProperty/getTermValidity")
	int getTermValidity(@RequestParam("cusNo")String cusNo, @RequestParam("rptDate")String rptDate, @RequestParam("submitMonth")String submitMonth, @RequestParam("accountId")String accountId)throws ServiceException;
	@RequestMapping(value = "/appProperty/getExamineAccount")
	String getExamineAccount(@RequestParam("cusNo")String cusNo, @RequestParam("rptDate")String rptDate, @RequestParam("submitMonth")String submitMonth, @RequestParam("accountId")String accountId)throws ServiceException;
}
