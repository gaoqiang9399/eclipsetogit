package  app.component.model.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.model.entity.LoanTemplateTagBase;
import app.component.model.entity.LoanTemplateTagSet;
import app.util.toolkit.Ipage;

/**
* Title: LoanTemplateTagSetBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Sep 05 18:28:09 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface LoanTemplateTagSetFeign {
	
	@RequestMapping(value = "/loanTemplateTagSet/insert")
	public void insert(@RequestBody LoanTemplateTagSet loanTemplateTagSet) throws ServiceException;
	
	@RequestMapping(value = "/loanTemplateTagSet/delete")
	public void delete(@RequestBody LoanTemplateTagSet loanTemplateTagSet) throws ServiceException;
	
	@RequestMapping(value = "/loanTemplateTagSet/update")
	public void update(@RequestBody LoanTemplateTagSet loanTemplateTagSet) throws ServiceException;
	/**
	 * 
	 * 方法描述： 保存标签配置信息
	 * @param loanTemplateTagSet
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2016-9-6 下午9:03:09
	 */
	@RequestMapping(value = "/loanTemplateTagSet/updateTagSet")
	public void updateTagSet(@RequestBody LoanTemplateTagSet loanTemplateTagSet) throws ServiceException;
	
	@RequestMapping(value = "/loanTemplateTagSet/getById")
	public LoanTemplateTagSet getById(@RequestBody LoanTemplateTagSet loanTemplateTagSet) throws ServiceException;
	
	@RequestMapping(value = "/loanTemplateTagSet/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("loanTemplateTagSet") LoanTemplateTagSet loanTemplateTagSet) throws ServiceException;
	/**
	 * 
	 * 方法描述： 
	 * @param modelNo
	 * @return
	 * @throws ServiceException
	 * List<LoanTemplateTagBase>
	 * @author 沈浩兵
	 * @date 2016-9-7 上午9:58:07
	 */
	@RequestMapping(value = "/loanTemplateTagSet/getTagsBaseList")
	public List<LoanTemplateTagBase> getTagsBaseList(@RequestBody String modelNo) throws ServiceException;
}
