package  app.component.eval.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.eval.entity.MfEvalScenceIndexRel;
import app.util.toolkit.Ipage;

/**
* Title: MfEvalScenceIndexRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 17 06:46:26 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfEvalScenceIndexRelFeign {
	
	@RequestMapping(value = "/mfEvalScenceIndexRel/insert")
	public MfEvalScenceIndexRel insert(@RequestBody MfEvalScenceIndexRel mfEvalScenceIndexRel) throws Exception;
	
	@RequestMapping(value = "/mfEvalScenceIndexRel/delete")
	public void delete(@RequestBody MfEvalScenceIndexRel mfEvalScenceIndexRel) throws Exception;
	
	@RequestMapping(value = "/mfEvalScenceIndexRel/update")
	public void update(@RequestBody MfEvalScenceIndexRel mfEvalScenceIndexRel) throws Exception;
	
	@RequestMapping(value = "/mfEvalScenceIndexRel/getById")
	public MfEvalScenceIndexRel getById(@RequestBody MfEvalScenceIndexRel mfEvalScenceIndexRel) throws Exception;
	
	@RequestMapping(value = "/mfEvalScenceIndexRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfEvalScenceIndexRel") MfEvalScenceIndexRel mfEvalScenceIndexRel) throws Exception;
	
	@RequestMapping(value = "/mfEvalScenceIndexRel/getMfEvalScenceIndexRelList")
	public List<MfEvalScenceIndexRel> getMfEvalScenceIndexRelList(@RequestBody String scenceNo) throws Exception;
	
}
