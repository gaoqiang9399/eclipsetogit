package  app.component.risk.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.risk.entity.RiskPreventItemRel;
import app.util.toolkit.Ipage;

/**
* Title: RiskPreventItemRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 03 07:18:05 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface RiskPreventItemRelFeign {
	
	@RequestMapping(value = "/riskPreventItemRel/insert")
	public void insert(@RequestBody RiskPreventItemRel riskPreventItemRel) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventItemRel/delete")
	public void delete(@RequestBody RiskPreventItemRel riskPreventItemRel) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventItemRel/update")
	public void update(@RequestBody RiskPreventItemRel riskPreventItemRel) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventItemRel/getById")
	public RiskPreventItemRel getById(@RequestBody RiskPreventItemRel riskPreventItemRel) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventItemRel/getByRelNo")
	public RiskPreventItemRel getByRelNo(@RequestBody RiskPreventItemRel riskPreventItemRel) throws ServiceException;

	
	@RequestMapping(value = "/riskPreventItemRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("riskPreventItemRel") RiskPreventItemRel riskPreventItemRel) throws ServiceException;

	@RequestMapping(value = "/riskPreventItemRel/getAll")
	public List<RiskPreventItemRel> getAll(@RequestBody RiskPreventItemRel riskPreventItemRel) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据维度组合编号获得组合下的拦截项编号
	 * @param riskPreventItemRel
	 * @return
	 * @throws ServiceException
	 * List<RiskPreventItemRel>
	 * @author 沈浩兵
	 * @date 2016-10-27 下午3:36:19
	 */
	@RequestMapping(value = "/riskPreventItemRel/getAllByGenNo")
	public List<RiskPreventItemRel> getAllByGenNo(@RequestBody RiskPreventItemRel riskPreventItemRel) throws ServiceException;
	
	
}
