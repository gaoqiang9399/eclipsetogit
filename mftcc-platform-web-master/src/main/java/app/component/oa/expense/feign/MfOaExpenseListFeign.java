package  app.component.oa.expense.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.oa.expense.entity.MfOaExpenseList;
import app.util.toolkit.Ipage;

/**
* Title: MfOaExpenseListBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jun 15 10:29:56 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfOaExpenseListFeign {
	@RequestMapping(value = "/mfOaExpenseList/insert")
	public void insert(@RequestBody MfOaExpenseList mfOaExpenseList) throws ServiceException;
	
	@RequestMapping(value = "/mfOaExpenseList/delete")
	public void delete(@RequestBody MfOaExpenseList mfOaExpenseList) throws ServiceException;
	
	@RequestMapping(value = "/mfOaExpenseList/update")
	public void update(@RequestBody MfOaExpenseList mfOaExpenseList) throws ServiceException;
	
	@RequestMapping(value = "/mfOaExpenseList/getById")
	public MfOaExpenseList getById(@RequestBody MfOaExpenseList mfOaExpenseList) throws ServiceException;
	
	@RequestMapping(value = "/mfOaExpenseList/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
}
