package app.component.model.feign;

import app.component.model.entity.MfTemplateTagBase;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
* Title: MfTemplateTagBaseBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 24 17:21:13 CST 2017
**/

@FeignClient("mftcc-platform-templete-tag")
public interface MfNewTemplateTagBaseFeign {

	@RequestMapping(value = "/mfTemplateTagBase/insert")
	public void insert(@RequestBody MfTemplateTagBase mfTemplateTagBase) throws Exception;

	@RequestMapping(value = "/mfTemplateTagBase/delete")
	public void delete(@RequestBody MfTemplateTagBase mfTemplateTagBase) throws Exception;

	@RequestMapping(value = "/mfTemplateTagBase/update")
	public void update(@RequestBody MfTemplateTagBase mfTemplateTagBase) throws Exception;

	@RequestMapping(value = "/mfTemplateTagBase/getById")
	public MfTemplateTagBase getById(@RequestBody MfTemplateTagBase mfTemplateTagBase) throws Exception;

	@RequestMapping(value = "/mfTemplateTagBase/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfTemplateTagBase") MfTemplateTagBase mfTemplateTagBase) throws Exception;
	/**
	 * 
	 * 方法描述： 获得分页列表
	 * @param ipage
	 * @param mfTemplateTagBase
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-12-20 下午12:16:44
	 */
	@RequestMapping(value = "/mfTemplateTagBase/getTagBaseListByPage")
	public Ipage getTagBaseListByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 获得用于展示的标签信息
	 * @param mfTemplateTagBase
	 * @return
	 * @throws Exception
	 * MfTemplateTagBase
	 * @author 沈浩兵
	 * @date 2017-12-20 下午4:14:07
	 */
	@RequestMapping(value = "/mfTemplateTagBase/getTemplateTagBaseShowById")
	public MfTemplateTagBase getTemplateTagBaseShowById(@RequestBody MfTemplateTagBase mfTemplateTagBase) throws Exception;
	/**
	 * 
	 * 方法描述： 更新启用标识
	 * @param mfTemplateTagBase
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2018-1-3 下午7:35:55
	 */
	@RequestMapping(value = "/mfTemplateTagBase/updateUseFlag")
	public void updateUseFlag(@RequestBody MfTemplateTagBase mfTemplateTagBase) throws Exception;
	/**
	 * 
	 * 方法描述： 根据标签分组获得最大sort
	 * @param groupFlag
	 * @return
	 * @throws Exception
	 * Integer
	 * @author 沈浩兵
	 * @date 2018-1-18 上午10:28:30
	 */
	@RequestMapping(value = "/mfTemplateTagBase/getMaxSort")
	public Integer getMaxSort(@RequestBody String groupFlag) throws Exception;

	/**
	 * 
	 * 方法描述： 获取流程表中业务审批的时间
	 * @param timeMap
	 * @return
	 * @throws Exception
	 * String
	 * @author lzshuai
	 * @date 2018-1-26 上午11:50:52
	 */
	@RequestMapping(value = "/mfTemplateTagBase/getEndTimeOfWfHistTask")
	public String getEndTimeOfWfHistTask(@RequestBody Map<String, String> timeMap)  throws Exception;
}
