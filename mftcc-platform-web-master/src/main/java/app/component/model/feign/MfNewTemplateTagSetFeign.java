package app.component.model.feign;

import app.base.ServiceException;

import app.component.model.entity.MfTemplateTagSet;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
* Title: MfTemplateTagSetBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sun Jun 25 09:23:41 CST 2017
**/

@FeignClient("mftcc-platform-templete-tag")
public interface MfNewTemplateTagSetFeign {
	
	@RequestMapping(value = "/mfTemplateTagSet/insert")
	public void insert(@RequestBody MfTemplateTagSet mfTemplateTagSet) throws Exception;
	
	@RequestMapping(value = "/mfTemplateTagSet/delete")
	public void delete(@RequestBody MfTemplateTagSet mfTemplateTagSet) throws Exception;
	
	@RequestMapping(value = "/mfTemplateTagSet/update")
	public void update(@RequestBody MfTemplateTagSet mfTemplateTagSet) throws Exception;
	
	@RequestMapping(value = "/mfTemplateTagSet/getById")
	public MfTemplateTagSet getById(@RequestBody MfTemplateTagSet mfTemplateTagSet) throws Exception;
	
	@RequestMapping(value = "/mfTemplateTagSet/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 获得模板标签配置数据源 从LoanTemplateTagSetBo迁移
	 * @param modelNo
	 * @return
	 * @throws ServiceException
	 * Map<String, Object>
	 * @author 沈浩兵
	 * @date 2016-9-7 上午9:58:07
	 */
	@RequestMapping(value = "/mfTemplateTagSet/getTagsBaseList")
	public Map<String, Object> getTagsBaseList(@RequestBody String modelNo) throws ServiceException;
	/**
	 *
	 * 方法描述： 保存标签配置信息
	 * @param mfTemplateTagSet
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2016-9-6 下午9:03:09
	 */
	@RequestMapping(value = "/mfTemplateTagSet/updateTagSet")
	public void updateTagSet(@RequestBody MfTemplateTagSet mfTemplateTagSet) throws ServiceException;


}
