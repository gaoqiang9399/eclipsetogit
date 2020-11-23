package app.component.model.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.model.entity.MfTemplateBizConfig;
import app.util.toolkit.Ipage;

/**
 * Title: MfTemplateBizConfigBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 05 11:43:57 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfTemplateBizConfigFeign {

	@RequestMapping(value = "/mfTemplateBizConfig/insert")
	public void insert(@RequestBody MfTemplateBizConfig mfTemplateBizConfig) throws ServiceException;

	@RequestMapping(value = "/mfTemplateBizConfig/delete")
	public void delete(@RequestBody MfTemplateBizConfig mfTemplateBizConfig) throws ServiceException;

	@RequestMapping(value = "/mfTemplateBizConfig/update")
	public void update(@RequestBody MfTemplateBizConfig mfTemplateBizConfig) throws ServiceException;

	@RequestMapping(value = "/mfTemplateBizConfig/getById")
	public MfTemplateBizConfig getById(@RequestBody MfTemplateBizConfig mfTemplateBizConfig) throws ServiceException;

	@RequestMapping(value = "/mfTemplateBizConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * 查询业务文档模板配置
	 * 
	 * @param templateBizConfig
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-7-5 上午11:44:48
	 */
	@RequestMapping(value = "/mfTemplateBizConfig/getBizConfigList")
	public List<MfTemplateBizConfig> getBizConfigList(@RequestBody MfTemplateBizConfig templateBizConfig) throws Exception;

	/**
	 * 方法描述：  根据mf_template_show.showtype  node_no, mf_template_biz_config的appId获取文档的唯一编号
	 * @param map
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-8-2 下午2:47:36
	 */
	@RequestMapping(value = "/mfTemplateBizConfig/getfTemplateBizConfigId")
	public String getfTemplateBizConfigId(@RequestBody Map<String, String> map)throws Exception;
	/**
	 * 根据appId 和 模板号 查询出数据
	 * @param mfTemplateBizConfig
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfTemplateBizConfig/getByAppIdAndTemplateNo")
	public MfTemplateBizConfig getByAppIdAndTemplateNo(
			MfTemplateBizConfig mfTemplateBizConfig)throws Exception;

	/**
	 * 
	 * 方法描述：查询业务文档模板配置（虑重）
	 * @param templateBizConfig
	 * @return
	 * @throws Exception
	 * List<MfTemplateBizConfig>
	 * @author zhs
	 * @date 2018-1-22 下午5:14:57
	 */
	@RequestMapping(value = "/mfTemplateBizConfig/getBizConfigListFilterRepeat")
	public List<MfTemplateBizConfig> getBizConfigListFilterRepeat(@RequestBody MfTemplateBizConfig templateBizConfig) throws Exception;

	/**
	 * 
	 * 方法描述：查询业务文档模板配置（虑重）
	 * @param templateBizConfig
	 * @return Ipage
	 * @throws Exception
	 * List<MfTemplateBizConfig>
	 * @author ldy
	 * @date 2018-8-1 下午5:14:57
	 */
	@RequestMapping(value = "/mfTemplateBizConfig/getBizConfigIpageFilterRepeat")
	public Ipage getBizConfigIpageFilterRepeat(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfTemplateBizConfig/validateTemplateIsInput")
	public Map<String,Object> validateTemplateIsInput(@RequestBody MfTemplateBizConfig mfTemplateBizConfig) throws Exception;

	@RequestMapping(value = "/mfTemplateBizConfig/deleteTemplateSaveInfo")
	public void deleteTemplateSaveInfo(@RequestBody MfTemplateBizConfig mfTemplateBizConfig);

	@RequestMapping(value = "/mfTemplateBizConfig/getDueDiligenceReport")
	public List<MfTemplateBizConfig> getDueDiligenceReport(@RequestBody MfTemplateBizConfig templateBizConfig) throws Exception;

}
