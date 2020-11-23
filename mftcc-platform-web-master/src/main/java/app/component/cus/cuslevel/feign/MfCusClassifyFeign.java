package  app.component.cus.cuslevel.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.cuslevel.entity.MfCusClassify;
import app.util.toolkit.Ipage;

/**
* Title: MfCusClassifyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jun 24 10:03:33 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusClassifyFeign {
	
	@RequestMapping(value = "/mfCusClassify/insert")
	public void insert(@RequestBody MfCusClassify mfCusClassify) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClassify/insertOrUpdate")
	public MfCusClassify insertOrUpdate(@RequestBody MfCusClassify mfCusClassify) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClassify/delete")
	public void delete(@RequestBody MfCusClassify mfCusClassify) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClassify/update")
	public void update(@RequestBody MfCusClassify mfCusClassify) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClassify/getById")
	public MfCusClassify getById(@RequestBody MfCusClassify mfCusClassify) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClassify/getList")
	public List<MfCusClassify> getList(@RequestBody MfCusClassify mfCusClassify) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClassify/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusClassify") MfCusClassify mfCusClassify) throws ServiceException;
 
	/**
	 * @author czk
	 * @Description: 根据客户号获得客户所有的分类信息，包括历史和当前的
	 * date 2016-12-3
	 */
	@RequestMapping(value = "/mfCusClassify/getByCusNo")
	public List<MfCusClassify> getByCusNo(@RequestBody MfCusClassify mfCusClassify) throws ServiceException;
	/**
	 * @author czk
	 * @Description: 根据客户号获得客户当前的分类信息
	 * date 2016-12-3
	 */
	@RequestMapping(value = "/mfCusClassify/getNewByCusNo")
	public List<MfCusClassify> getNewByCusNo(@RequestBody MfCusClassify mfCusClassify) throws ServiceException;
	/**
	 * @author czk
	 * @Description: 根据客户号获得历史分类信息
	 * date 2016-12-3
	 */
	@RequestMapping(value = "/mfCusClassify/getHisByCusNo")
	public List<MfCusClassify> getHisByCusNo(@RequestBody MfCusClassify mfCusClassify) throws ServiceException;
	/**
	 * 
	 * 方法描述： 添加客户分类
	 * @param dataMap
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-3-20 下午6:09:20
	 */
	@RequestMapping(value = "/mfCusClassify/insertCusClassify")
	public Map<String, Object> insertCusClassify(@RequestBody Map<String,Object> dataMap) throws Exception;

	/**
	 *
	 * add by lance zhao 20180827 客户自动分类
	 * @param parmMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusClassify/cusAutoClassify")
	String cusAutoClassify(@RequestBody Map<String,String> parmMap)  throws Exception;
}
