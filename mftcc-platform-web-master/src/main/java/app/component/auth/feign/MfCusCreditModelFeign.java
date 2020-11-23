package  app.component.auth.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.auth.entity.MfCusCreditModel;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCreditModelBo.java
* Description:授信模型配置业务控制
* @author:LJW
* @Thu Feb 23 16:13:12 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditModelFeign {
	
	@RequestMapping(value = "/mfCusCreditModel/insert")
	public void insert(@RequestBody MfCusCreditModel mfCusCreditModel) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditModel/delete")
	public void delete(@RequestBody MfCusCreditModel mfCusCreditModel) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditModel/update")
	public void update(@RequestBody MfCusCreditModel mfCusCreditModel) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditModel/getById")
	public MfCusCreditModel getById(@RequestBody MfCusCreditModel mfCusCreditModel) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditModel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditModel") MfCusCreditModel mfCusCreditModel) throws ServiceException;
	
	/**
	 * 根据客户类型编号查询
	 * @author LJW
	 * date 2017-2-28
	 */
	@RequestMapping(value = "/mfCusCreditModel/getByCusTypeNo")
	public MfCusCreditModel getByCusTypeNo(@RequestBody MfCusCreditModel mfCusCreditModel) throws ServiceException;
	
	/**
	 * 根据客户编号查询模型总数
	 * @author LJW
	 * date 2017-3-7
	 */
	@RequestMapping(value = "/mfCusCreditModel/getByCusTypeNoSum")
	public int getByCusTypeNoSum(@RequestBody MfCusCreditModel mfCusCreditModel) throws ServiceException;
	
	/**
	 * 根据表单id查询模型数据
	 * @author LJW
	 * date 2017-3-7
	 */
	@RequestMapping(value = "/mfCusCreditModel/getByCreditFormIds")
	public List<MfCusCreditModel> getByCreditFormIds(@RequestBody MfCusCreditModel mfCusCreditModel) throws ServiceException;
	
}
