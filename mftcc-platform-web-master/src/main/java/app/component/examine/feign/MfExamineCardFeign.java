package  app.component.examine.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.examine.entity.MfExamineCard;
import app.util.toolkit.Ipage;

/**
* Title: MfExamineCardBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jul 24 14:26:01 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamineCardFeign {
	@RequestMapping("/mfExamineCard/insert")
	public MfExamineCard insert(@RequestBody MfExamineCard mfExamineCard) throws Exception;
	@RequestMapping("/mfExamineCard/delete")
	public void delete(@RequestBody MfExamineCard mfExamineCard) throws Exception;
	@RequestMapping("/mfExamineCard/update")
	public void update(@RequestBody MfExamineCard mfExamineCard) throws Exception;
	@RequestMapping("/mfExamineCard/getById")
	public MfExamineCard getById(@RequestBody MfExamineCard mfExamineCard) throws Exception;
	@RequestMapping("/mfExamineCard/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamineCard") MfExamineCard mfExamineCard) throws Exception;
	
}
