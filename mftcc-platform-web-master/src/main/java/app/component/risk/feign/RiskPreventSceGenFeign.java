package  app.component.risk.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.risk.entity.RiskPreventSceGen;
import app.util.toolkit.Ipage;

/**
* Title: RiskPreventSceGenBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 03 07:15:46 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface RiskPreventSceGenFeign {
	
	@RequestMapping(value = "/riskPreventSceGen/insert")
	public void insert(@RequestBody RiskPreventSceGen riskPreventSceGen) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventSceGen/delete")
	public void delete(@RequestBody RiskPreventSceGen riskPreventSceGen) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventSceGen/update")
	public void update(@RequestBody RiskPreventSceGen riskPreventSceGen) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventSceGen/getById")
	public RiskPreventSceGen getById(@RequestBody RiskPreventSceGen riskPreventSceGen) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventSceGen/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("riskPreventSceGen") RiskPreventSceGen riskPreventSceGen) throws ServiceException;

	@RequestMapping(value = "/riskPreventSceGen/getAll")
	public List<RiskPreventSceGen> getAll(@RequestBody RiskPreventSceGen riskPreventSceGen) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得根据维度信息匹配到风险拦截维度组合信息
	 * @param dimeMap
	 * @return
	 * @throws ServiceException
	 * List<RiskPreventSceGen>
	 * @author 沈浩兵
	 * @date 2016-10-27 下午3:33:42
	 */
	@RequestMapping(value = "/riskPreventSceGen/getMatchedAll")
	public List<RiskPreventSceGen> getMatchedAll(@RequestBody Map<String,Object> dimeMap) throws ServiceException;

	/**
	 * @author czk
	 * @Description: 验证该维度组合是否已经存在，如果存在返回true
	 * date 2016-11-3
	 * @param riskPreventSceGen
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/riskPreventSceGen/doCheckExist")
	public boolean doCheckExist(@RequestBody String dimeVal) throws ServiceException;


}
