package  app.component.risk.feign;

import java.util.List;
import java.util.Map;

import app.component.risk.entity.RiskItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.util.toolkit.Ipage;

/**
* Title: RiskItemBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Feb 23 06:42:31 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface RiskItemFeign {
	
	@RequestMapping(value = "/riskItem/insert")
	public void insert(@RequestBody RiskItem riskItem) throws ServiceException;
	
	@RequestMapping(value = "/riskItem/delete")
	public void delete(@RequestBody RiskItem riskItem) throws ServiceException;
	
	@RequestMapping(value = "/riskItem/update")
	public void update(@RequestBody RiskItem riskItem) throws ServiceException;
	
	@RequestMapping(value = "/riskItem/getById")
	public RiskItem getById(@RequestBody RiskItem riskItem) throws ServiceException;
	
	@RequestMapping(value = "/riskItem/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	
	@RequestMapping(value = "/riskItem/getRiskItemNo")
	public String getRiskItemNo() throws ServiceException;

	/**
	 * 
	 * 方法描述： 根据拦截项编号以及类型获取要件要展示的字段列表
	 * @param dataMap
	 * @return
	 * List<RiskItem>
	 * @author zhs
	 * @date 2017-5-16 下午5:53:08
	 */
	@RequestMapping(value = "/riskItem/getDocRiskList")
	public List<RiskItem> getDocRiskList(@RequestBody Map<String, Object> dataMap) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得风险指标
	 * @param riskItem
	 * @return
	 * @throws ServiceException
	 * List<RiskItem>
	 * @author 沈浩兵
	 * @date 2017-8-8 下午3:20:12
	 */
	@RequestMapping(value = "/riskItem/getRiskItemList")
	public List<RiskItem> getRiskItemList(@RequestBody RiskItem riskItem) throws Exception;
}
