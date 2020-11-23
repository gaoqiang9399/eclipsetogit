package app.component.pledge.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.ParmDic;
import app.component.pledge.entity.MfPledgeClass;
import app.util.toolkit.Ipage;

/**
 * Title: MfPledgeClassBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 20 11:18:22 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfPledgeClassFeign {

	@RequestMapping(value = "/mfPledgeClass/insert")
	public void insert(@RequestBody MfPledgeClass mfPledgeClass) throws ServiceException;

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param mfPledgeClass
	 * @throws ServiceException
	 *             void
	 * @author 沈浩兵
	 * @date 2016-9-21 上午8:57:40
	 */
	@RequestMapping(value = "/mfPledgeClass/insertCopeFormId")
	public MfPledgeClass insertCopeFormId(@RequestBody ParmDic parmDic, @RequestParam("dataMap") Map<String, Object> dataMap)
			throws ServiceException;

	@RequestMapping(value = "/mfPledgeClass/delete")
	public void delete(@RequestBody MfPledgeClass mfPledgeClass) throws ServiceException;

	@RequestMapping(value = "/mfPledgeClass/update")
	public void update(@RequestBody MfPledgeClass mfPledgeClass) throws ServiceException;

	@RequestMapping(value = "/mfPledgeClass/getById")
	public MfPledgeClass getById(@RequestBody MfPledgeClass mfPledgeClass) throws ServiceException;

	@RequestMapping(value = "/mfPledgeClass/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfPledgeClass") MfPledgeClass mfPledgeClass)
			throws ServiceException;
}
