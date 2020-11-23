package  app.component.examine.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.examine.entity.MfExamineDocTemplate;
import app.util.toolkit.Ipage;

/**
* Title: MfExamineDocTemplateBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 03 10:56:54 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamineDocTemplateFeign {
	@RequestMapping("/mfExamineDocTemplate/insert")
	public Map<String, Object> insert(@RequestBody MfExamineDocTemplate mfExamineDocTemplate) throws Exception;
	@RequestMapping("/mfExamineDocTemplate/delete")
	public void delete(@RequestBody MfExamineDocTemplate mfExamineDocTemplate) throws Exception;
	@RequestMapping("/mfExamineDocTemplate/update")
	public void update(@RequestBody MfExamineDocTemplate mfExamineDocTemplate) throws Exception;
	@RequestMapping("/mfExamineDocTemplate/getById")
	public MfExamineDocTemplate getById(@RequestBody MfExamineDocTemplate mfExamineDocTemplate) throws Exception;
	@RequestMapping("/mfExamineDocTemplate/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 获得检查模型下配置的模板
	 * @param mfExamineDocTemplate
	 * @return
	 * @throws Exception
	 * List<MfExamineDocTemplate>
	 * @author 沈浩兵
	 * @date 2017-8-6 下午11:41:16
	 */
	@RequestMapping("/mfExamineDocTemplate/getMfExamineDocTemplateList")
	public List<MfExamineDocTemplate> getMfExamineDocTemplateList(@RequestBody MfExamineDocTemplate mfExamineDocTemplate) throws Exception;
	
}
