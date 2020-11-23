package  app.component.risk.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
//import app.component.lease.entity.LeaseItemInfo;
import app.component.risk.entity.RiskItemThreashode;
import app.util.toolkit.Ipage;

/**
* Title: RiskItemThreashodeBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Feb 23 06:49:07 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface RiskItemThreashodeFeign {
	
	@RequestMapping(value = "/riskItemThreashode/insert")
	public void insert(@RequestBody RiskItemThreashode riskItemThreashode) throws ServiceException;
	
	@RequestMapping(value = "/riskItemThreashode/delete")
	public void delete(@RequestBody RiskItemThreashode riskItemThreashode) throws ServiceException;
	
	@RequestMapping(value = "/riskItemThreashode/update")
	public void update(@RequestBody RiskItemThreashode riskItemThreashode) throws ServiceException;
	
	@RequestMapping(value = "/riskItemThreashode/getById")
	public RiskItemThreashode getById(@RequestBody RiskItemThreashode riskItemThreashode) throws ServiceException;
	
	@RequestMapping(value = "/riskItemThreashode/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("riskItemThreashode") RiskItemThreashode riskItemThreashode) throws ServiceException;
	
	@RequestMapping(value = "/riskItemThreashode/getListPage")
	public Ipage getListPage(@RequestBody Ipage ipg,@RequestParam("riskItemThreashode") RiskItemThreashode riskItemThreashode) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据拦截编号获得关联的阀值
	 * @param ipg
	 * @param riskItemThreashode
	 * @return
	 * @throws ServiceException
	 * List<RiskItemThreashode>
	 * @author 沈浩兵
	 * @date 2016-10-27 下午8:38:24
	 */
	@RequestMapping(value = "/riskItemThreashode/getListByItemNo")
	public List<RiskItemThreashode> getListByItemNo(@RequestBody RiskItemThreashode riskItemThreashode) throws ServiceException;
	
	
}
