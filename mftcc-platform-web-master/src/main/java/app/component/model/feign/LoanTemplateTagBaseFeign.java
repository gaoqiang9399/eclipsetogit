package  app.component.model.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.model.entity.LoanTemplateTagBase;
import app.util.toolkit.Ipage;

/**
* Title: LoanTemplateTagBaseBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Sep 05 18:26:08 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface LoanTemplateTagBaseFeign {
	
	@RequestMapping(value = "/loanTemplateTagBase/insert")
	public void insert(@RequestBody LoanTemplateTagBase loanTemplateTagBase) throws ServiceException;
	
	@RequestMapping(value = "/loanTemplateTagBase/delete")
	public void delete(@RequestBody LoanTemplateTagBase loanTemplateTagBase) throws ServiceException;
	
	@RequestMapping(value = "/loanTemplateTagBase/update")
	public void update(@RequestBody LoanTemplateTagBase loanTemplateTagBase) throws ServiceException;
	
	@RequestMapping(value = "/loanTemplateTagBase/getById")
	public LoanTemplateTagBase getById(@RequestBody LoanTemplateTagBase loanTemplateTagBase) throws ServiceException;
	
	@RequestMapping(value = "/loanTemplateTagBase/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("loanTemplateTagBase") LoanTemplateTagBase loanTemplateTagBase) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得基础标签list
	 * @param loanTemplateTagBase
	 * @return
	 * @throws ServiceException
	 * List<LoanTemplateTagBase>
	 * @author 沈浩兵
	 * @date 2016-9-5 下午8:19:55
	 */
	@RequestMapping(value = "/loanTemplateTagBase/getTagBaseList")
	public List<LoanTemplateTagBase> getTagBaseList(@RequestBody LoanTemplateTagBase loanTemplateTagBase) throws ServiceException;
	
}
