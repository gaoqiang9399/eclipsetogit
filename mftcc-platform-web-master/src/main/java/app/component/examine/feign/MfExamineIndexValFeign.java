package  app.component.examine.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.examine.entity.MfExamineIndexVal;
import app.util.toolkit.Ipage;

/**
* Title: MfExamineIndexValBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jul 26 23:52:34 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamineIndexValFeign {
	@RequestMapping("/MfExamineIndexVal/insert")
	public void insert(@RequestBody MfExamineIndexVal mfExamineIndexVal) throws Exception;
	@RequestMapping("/MfExamineIndexVal/delete")
	public void delete(@RequestBody MfExamineIndexVal mfExamineIndexVal) throws Exception;
	@RequestMapping("/MfExamineIndexVal/update")
	public void update(@RequestBody MfExamineIndexVal mfExamineIndexVal) throws Exception;
	@RequestMapping("/MfExamineIndexVal/getById")
	public MfExamineIndexVal getById(@RequestBody MfExamineIndexVal mfExamineIndexVal) throws Exception;
	@RequestMapping("/MfExamineIndexVal/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamineIndexVal") MfExamineIndexVal mfExamineIndexVal) throws Exception;
	
}
