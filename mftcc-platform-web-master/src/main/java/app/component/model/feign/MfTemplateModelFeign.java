package  app.component.model.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.model.entity.MfTemplateModel;
import app.util.toolkit.Ipage;

/**
* Title: MfTemplateModelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Nov 19 11:18:13 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfTemplateModelFeign {
	
	@RequestMapping(value = "/mfTemplateModel/insert")
	public MfTemplateModel insert(@RequestBody MfTemplateModel mfTemplateModel) throws ServiceException;
	
	@RequestMapping(value = "/mfTemplateModel/delete")
	public void delete(@RequestBody MfTemplateModel mfTemplateModel) throws ServiceException;
	
	@RequestMapping(value = "/mfTemplateModel/update")
	public void update(@RequestBody MfTemplateModel mfTemplateModel) throws ServiceException;
	
	@RequestMapping(value = "/mfTemplateModel/getById")
	public MfTemplateModel getById(@RequestBody MfTemplateModel mfTemplateModel) throws ServiceException;
	
	@RequestMapping(value = "/mfTemplateModel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfTemplateModel") MfTemplateModel mfTemplateModel) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 获取模型列表
	 * @param mfTemplateModel
	 * @return
	 * @throws ServiceException
	 * List<MfTemplateModel>
	 * @author zhs
	 * @date 2016-11-19 上午11:42:42
	 */
	@RequestMapping(value = "/mfTemplateModel/getTemplateModelList")
	public List<MfTemplateModel> getTemplateModelList(@RequestBody MfTemplateModel mfTemplateModel) throws ServiceException;
	
}
