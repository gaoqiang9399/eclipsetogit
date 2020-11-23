package  app.component.app.feign;

import java.util.List;

import app.component.app.entity.MfBusApplyHis;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.util.toolkit.Ipage;

/**
* Title: MfBusApplyHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu May 26 11:35:25 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusApplyHisFeign {
	
	@RequestMapping(value = "/mfBusApplyHis/insert")
	public void insert(@RequestBody MfBusApplyHis mfBusApplyHis) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApplyHis/delete")
	public void delete(@RequestBody MfBusApplyHis mfBusApplyHis) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApplyHis/update")
	public void update(@RequestBody MfBusApplyHis mfBusApplyHis) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApplyHis/getById")
	public MfBusApplyHis getById(@RequestBody MfBusApplyHis mfBusApplyHis) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApplyHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusApplyHis") MfBusApplyHis mfBusApplyHis) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据业务编号获得其审批历史信息
	 * @param mfBusApplyHis
	 * @return
	 * @throws ServiceException
	 * List<MfBusApplyHis>
	 * @author 沈浩兵
	 * @date 2016-12-13 下午8:48:48
	 */
	@RequestMapping(value = "/mfBusApplyHis/getListByAppId")
	public List<MfBusApplyHis> getListByAppId(@RequestBody MfBusApplyHis mfBusApplyHis) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 查询审批历史按照登记时间升序排序
	 * @param mfBusApplyHis
	 * @return
	 * @throws ServiceException
	 * List<MfBusApplyHis>
	 * @author lwq
	 * @date 2017-9-28 上午10:38:00
	 */
	@RequestMapping(value = "/mfBusApplyHis/getListByAppIdForOffice")
	public List<MfBusApplyHis> getListByAppIdForOffice(@RequestBody MfBusApplyHis mfBusApplyHis) throws ServiceException;

	@RequestMapping(value = "/mfBusApplyHis/findByAppId")
	List<MfBusApplyHis> findByAppId(@RequestBody MfBusApplyHis mfBusApplyHis)throws ServiceException;


	@RequestMapping(value = "/mfBusApplyHis/findByNodeName")
	public MfBusApplyHis findByNodeName(@RequestBody MfBusApplyHis mfBusApplyHis) throws Exception;


	@RequestMapping(value = "/mfBusApplyHis/getListPriAppId")
	public List<MfBusApplyHis> getListPriAppId(@RequestBody MfBusApplyHis mfBusApplyHis) throws Exception;


}
