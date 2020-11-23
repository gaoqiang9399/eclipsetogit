package  app.component.examine.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.examine.entity.MfExamineIndexSub;
import app.util.toolkit.Ipage;

/**
* Title: MfExamineIndexSubBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jul 24 14:27:45 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamineIndexSubFeign {
	@RequestMapping("/mfExamineIndexSub/insert")
	public void insert(@RequestBody MfExamineIndexSub mfExamineIndexSub) throws Exception;
	@RequestMapping("/mfExamineIndexSub/delete")
	public void delete(@RequestBody MfExamineIndexSub mfExamineIndexSub) throws Exception;
	@RequestMapping("/mfExamineIndexSub/update")
	public void update(@RequestBody MfExamineIndexSub mfExamineIndexSub) throws Exception;
	@RequestMapping("/mfExamineIndexSub/getById")
	public MfExamineIndexSub getById(@RequestBody MfExamineIndexSub mfExamineIndexSub) throws Exception;
	@RequestMapping("/mfExamineIndexSub/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamineIndexSub") MfExamineIndexSub mfExamineIndexSub) throws Exception;
	/**
	 * 
	 * 方法描述： 根据检查项编号获得子项
	 * @param mfExamineIndexSub
	 * @return
	 * @throws Exception
	 * List<MfExamineIndexSub>
	 * @author 沈浩兵
	 * @date 2017-8-19 下午2:42:45
	 */
	@RequestMapping("/mfExamineIndexSub/getMfEvalIndexSubList")
	public List<MfExamineIndexSub> getMfEvalIndexSubList(@RequestBody MfExamineIndexSub mfExamineIndexSub) throws Exception;
	
}
