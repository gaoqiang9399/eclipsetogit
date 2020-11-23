package  app.component.calc.core.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfRepayPlanChild;
import app.util.toolkit.Ipage;

/**
* Title: MfRepayPlanChildBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jun 22 10:07:03 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfRepayPlanChildFeign {
	
	@RequestMapping(value = "/mfRepayPlanChild/insert")
	public void insert(@RequestBody MfRepayPlanChild mfRepayPlanChild) throws Exception;
	
	@RequestMapping(value = "/mfRepayPlanChild/delete")
	public void delete(@RequestBody MfRepayPlanChild mfRepayPlanChild) throws Exception;
	
	@RequestMapping(value = "/mfRepayPlanChild/update")
	public void update(@RequestBody MfRepayPlanChild mfRepayPlanChild) throws Exception;
	/**
	 * 批量插入还款计划子表
	 * @param mfRepayPlanChilds
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfRepayPlanChild/insert")
	public void insert(@RequestBody List<MfRepayPlanChild> mfRepayPlanChilds)  throws Exception;
	
	@RequestMapping(value = "/mfRepayPlanChild/getById")
	public MfRepayPlanChild getById(@RequestBody MfRepayPlanChild mfRepayPlanChild) throws Exception;
	
	@RequestMapping(value = "/mfRepayPlanChild/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfRepayPlanChild") MfRepayPlanChild mfRepayPlanChild) throws Exception;
	
}
