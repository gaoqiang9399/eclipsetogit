package app.component.oa.debtexpense.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.oa.debtexpense.entity.MfOaDebtexpense;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaDebtexpenseBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Jan 22 10:12:02 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaDebtexpenseFeign {
	@RequestMapping(value = "/mfOaDebtexpense/insert")
	public void insert(@RequestBody MfOaDebtexpense mfOaDebtexpense) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtexpense/delete")
	public void delete(@RequestBody MfOaDebtexpense mfOaDebtexpense) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtexpense/deleteByRelId")
	public void deleteByRelId(@RequestBody MfOaDebtexpense mfOaDebtexpense) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtexpense/update")
	public void update(@RequestBody MfOaDebtexpense mfOaDebtexpense) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtexpense/getById")
	public MfOaDebtexpense getById(@RequestBody MfOaDebtexpense mfOaDebtexpense) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtexpense/getByRelId")
	public MfOaDebtexpense getByRelId(@RequestBody MfOaDebtexpense mfOaDebtexpense) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtexpense/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtexpense/getOaDebtexpenseCount")
	public int getOaDebtexpenseCount(@RequestBody MfOaDebtexpense mfOaDebtexpense) throws ServiceException;

}
