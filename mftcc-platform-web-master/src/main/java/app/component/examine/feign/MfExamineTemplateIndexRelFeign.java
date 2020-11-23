package  app.component.examine.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.examine.entity.MfExamineTemplateIndexRel;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
* Title: MfExamineTemplateIndexRelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jul 24 14:29:55 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamineTemplateIndexRelFeign {
	
	@RequestMapping("/mfExamineTemplateIndexRel/insert")
	public Map<String,Object> insert(@RequestBody JSONObject indexRelJsonObj) throws Exception;
	@RequestMapping("/mfExamineTemplateIndexRel/delete")
	public void delete(@RequestBody MfExamineTemplateIndexRel mfExamineTemplateIndexRel) throws Exception;
	@RequestMapping("/mfExamineTemplateIndexRel/update")
	public Map<String,Object> update(@RequestBody JSONObject indexRelJsonObj) throws Exception;
	@RequestMapping("/mfExamineTemplateIndexRel/getById")
	public MfExamineTemplateIndexRel getById(@RequestBody MfExamineTemplateIndexRel mfExamineTemplateIndexRel) throws Exception;
	@RequestMapping("/mfExamineTemplateIndexRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamineTemplateIndexRel") MfExamineTemplateIndexRel mfExamineTemplateIndexRel) throws Exception;
	/**
	 * 
	 * 方法描述： 获得检查指标数据
	 * @param mfExamineTemplateIndexRel
	 * @return
	 * @throws Exception
	 * List<MfExamineTemplateIndexRel>
	 * @author 沈浩兵
	 * @date 2017-7-28 下午5:44:48
	 */
	@RequestMapping("/mfExamineTemplateIndexRel/getTemplateIndexRel")
	public List<MfExamineTemplateIndexRel> getTemplateIndexRel(@RequestBody MfExamineTemplateIndexRel mfExamineTemplateIndexRel) throws Exception;
	
}
