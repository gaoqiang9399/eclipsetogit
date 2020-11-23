package app.component.prdct.feign;

import java.util.List;
import java.util.Map;

import app.component.model.entity.MfSysTemplate;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.prdct.entity.MfKindNodeTemplate;
import app.util.toolkit.Ipage;

/**
 * Title: MfKindNodeTemplateBo.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 04 12:30:02 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfKindNodeTemplateFeign {

	@RequestMapping(value = "/mfKindNodeTemplate/insert", method = RequestMethod.POST)
	public Map<String, Object> insert(@RequestBody MfKindNodeTemplate mfKindNodeTemplate) throws Exception;

	@RequestMapping(value = "/mfKindNodeTemplate/delete", method = RequestMethod.POST)
	public void delete(@RequestBody MfKindNodeTemplate mfKindNodeTemplate) throws Exception;

	@RequestMapping(value = "/mfKindNodeTemplate/update", method = RequestMethod.POST)
	public void update(@RequestBody MfKindNodeTemplate mfKindNodeTemplate) throws Exception;

	@RequestMapping(value = "/mfKindNodeTemplate/getById", method = RequestMethod.POST)
	public MfKindNodeTemplate getById(@RequestBody MfKindNodeTemplate mfKindNodeTemplate) throws Exception;

	@RequestMapping(value = "/mfKindNodeTemplate/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfKindNodeTemplate") MfKindNodeTemplate mfKindNodeTemplate) throws Exception;

	/**
	 *
	 * 方法描述： 获取节点模板配置新增弹层列表数据
	 *
	 * @param kindNo
	 * @param nodeNo
	 * @return
	 * @throws Exception
	 *             List<MfSysTemplate>
	 * @author zhs
	 * @date 2017-7-6 下午3:40:38
	 */
	@RequestMapping(value = "/mfKindNodeTemplate/getTemplateForNodeList", method = RequestMethod.POST)
	public List<MfSysTemplate> getTemplateForNodeList(@RequestBody String kindNo, @RequestParam("nodeNo") String nodeNo)
			throws Exception;

	@RequestMapping(value = "/mfKindNodeTemplate/getList", method = RequestMethod.POST)
	public List<MfKindNodeTemplate> getList(@RequestBody MfKindNodeTemplate kindNodeTemplate) throws Exception;

	/**
	 *
	 * 方法描述： 获得节点的模板信息，附带模板详细属性值，名字等
	 *
	 * @param kindNodeTemplate
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeTemplate>
	 * @author 沈浩兵
	 * @date 2017-9-9 下午5:35:09
	 */
	@RequestMapping(value = "/mfKindNodeTemplate/getNodeTemplateList", method = RequestMethod.POST)
	public List<MfKindNodeTemplate> getNodeTemplateList(@RequestBody MfKindNodeTemplate kindNodeTemplate)
			throws Exception;

	@RequestMapping(value = "/mfKindNodeTemplate/getCusTemplateConfig")
	public List<MfKindNodeTemplate> getCusTemplateConfig(@RequestBody MfKindNodeTemplate kindNodeTemplate)
			throws Exception ;
	/**
	 * @描述 产品配置中节点配置模板保存
	 * @参数 [mfKindNodeTemplate]
	 * @返回值 java.util.Map<java.lang.String,java.lang.Object>
	 * @创建人  shenhaobing
	 * @创建时间 2019/9/23
	 * @修改人和其它信息
	 */
	@RequestMapping(value = "/mfKindNodeTemplate/insertForKind", method = RequestMethod.POST)
	public Map<String, Object> insertForKind(@RequestBody MfKindNodeTemplate mfKindNodeTemplate) throws Exception;
}
