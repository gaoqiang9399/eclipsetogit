package  app.component.cus.relation.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.relation.entity.MfCusRelation;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
* Title: MfCusRelationBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Oct 11 15:47:03 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusRelationFeign {
	
	@RequestMapping(value = "/mfCusRelation/insert")
	public MfCusRelation insert(@RequestBody MfCusRelation mfCusRelation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/Sysinsert")
	public void Sysinsert(@RequestBody MfCusRelation mfCusRelation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/SysinsertForPers")
	public void SysinsertForPers(@RequestBody MfCusRelation mfCusRelation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/delete")
	public void delete(@RequestBody MfCusRelation mfCusRelation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/update")
	public MfCusRelation update(@RequestBody MfCusRelation mfCusRelation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/getById")
	public MfCusRelation getById(@RequestBody MfCusRelation mfCusRelation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusRelation") MfCusRelation mfCusRelation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/findByPage")
	public List<MfCusRelation> findByPage(@RequestBody MfCusRelation mfCusRelation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/getListByCusNo")
	public List<MfCusRelation> getListByCusNo(@RequestBody MfCusRelation mfCusRelation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/getJsonStrForRelation")
	public JSONObject getJsonStrForRelation(@RequestBody MfCusCustomer mfCusCustomer) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelation/getRelationByCusNo")
	public boolean getRelationByCusNo(@RequestParam(name="cusNo") String cusNo) throws ServiceException;
	/**
	 * 特殊客户类型得关联关系数据源
	 * @param cusNo
	 * @param baseType
	 * @return
	 */
	@RequestMapping(value = "/mfCusRelation/getJsonStrForSERelation")
	public Map<String, Object> getJsonStrForSERelation(@RequestParam("cusNo")String cusNo,
			@RequestParam("baseType")String baseType) throws ServiceException;
	/**
	 * 
	 *<p>Description:判断该客户是否与其他客户存在关系 </p> 
	 *@param mfCusRelation
	 *@return
	 *@throws ServiceException
	 *@author 周凯强
	 *@date 2018年7月5日下午5:31:17
	 */
	@RequestMapping(value = "/mfCusRelation/isCustomerRelation")
	public List<MfCusRelation> isCustomerRelation(@RequestBody MfCusRelation mfCusRelation) throws ServiceException;
}
