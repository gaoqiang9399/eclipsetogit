package  app.component.collateral.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.MfBusCollateral;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfBusCollateralBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Mar 08 10:33:09 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusCollateralFeign {
	
	@RequestMapping(value = "/mfBusCollateral/insert")
	public void insert(@RequestBody MfBusCollateral mfBusCollateral) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateral/delete")
	public void delete(@RequestBody MfBusCollateral mfBusCollateral) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateral/update")
	public void update(@RequestBody MfBusCollateral mfBusCollateral) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateral/getById")
	public MfBusCollateral getById(@RequestBody MfBusCollateral mfBusCollateral) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateral/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusCollateral") MfBusCollateral mfBusCollateral) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得选择押品分页列表
	 * @param ipage
	 * @param mfBusCollateral
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-4-13 上午9:56:17
	 */
//	@RequestMapping(value = "/mfBusCollateral/getCollateralListByCusNoPage")
	//public Ipage getCollateralListByCusNoPage(@RequestBody Ipage ipage,@RequestParam("mfBusCollateral") MfBusCollateral mfBusCollateral,@RequestParam("appId") String appId) throws ServiceException;

	@RequestMapping(value = "/mfBusCollateral/getAll")
	public List<MfBusCollateral> getAll(@RequestBody MfBusCollateral mfBusCollateral) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateral/getAllIn")
	public List<MfBusCollateral> getAllIn() throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateral/getCollateralByCusNo")
	public List<MfBusCollateral> getCollateralByCusNo(@RequestBody MfBusCollateral mfBusCollateral) throws ServiceException;
	//调用规则引擎，判断预警
	@RequestMapping(value = "/mfBusCollateral/getCollateralWarning")
	public Map<String,Object> getCollateralWarning(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	//押品查询移除解冻状态
	@RequestMapping(value = "/mfBusCollateral/removeStatus")
	public JSONArray removeStatus()throws ServiceException;
}
