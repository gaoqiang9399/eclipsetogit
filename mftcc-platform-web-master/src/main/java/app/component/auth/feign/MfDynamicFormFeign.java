package  app.component.auth.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.auth.entity.MfDynamicForm;
import app.util.toolkit.Ipage;

/**
* Title: MfDynamicFormBo.java
* Description:客户授信申请业务控制操作
* @author:LJW
* @Wed Feb 22 18:08:09 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfDynamicFormFeign {
	
	@RequestMapping(value = "/mfDynamicForm/insert")
	public void insert(@RequestBody MfDynamicForm mfDynamicForm) throws ServiceException;
	
	@RequestMapping(value = "/mfDynamicForm/delete")
	public void delete(@RequestBody MfDynamicForm mfDynamicForm) throws ServiceException;
	
	@RequestMapping(value = "/mfDynamicForm/update")
	public void update(@RequestBody MfDynamicForm mfDynamicForm) throws ServiceException;
	
	@RequestMapping(value = "/mfDynamicForm/getById")
	public MfDynamicForm getById(@RequestBody MfDynamicForm mfDynamicForm) throws ServiceException;
	
	@RequestMapping(value = "/mfDynamicForm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	/**
	 * 查询授信申请动态表单列表
	 * @author LJW
	 * date 2017-2-24
	 */
	@RequestMapping(value = "/mfDynamicForm/getDynamicForms")
	public List<MfDynamicForm> getDynamicForms(@RequestBody MfDynamicForm mfDynamicForm) throws ServiceException;
	
}
