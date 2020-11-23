package app.component.oa.debt.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.oa.debt.entity.MfOaDebtReturnHis;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaDebtReturnHisBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 22 10:32:09 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaDebtReturnHisFeign {
	@RequestMapping(value = "/mfOaDebtReturnHis/insert")
	public void insert(@RequestBody MfOaDebtReturnHis mfOaDebtReturnHis) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtReturnHis/delete")
	public void delete(@RequestBody MfOaDebtReturnHis mfOaDebtReturnHis) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtReturnHis/update")
	public void update(@RequestBody MfOaDebtReturnHis mfOaDebtReturnHis) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtReturnHis/getById")
	public MfOaDebtReturnHis getById(@RequestBody MfOaDebtReturnHis mfOaDebtReturnHis) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtReturnHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfOaDebtReturnHis/insert")
	void insert(@RequestBody MfOaDebtReturnHis mfOaDebtReturnHis, @RequestParam("opNo") String opNo)
			throws ServiceException;
}
