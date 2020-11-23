package app.component.tuning.feign;

import app.component.auth.entity.MfCusCreditTuningDetail;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
* Title: MfCusCreditTuningDetailFeign.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 26 13:09:22 CST 2018
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditTuningDetailFeign {
	
	@RequestMapping("/mfCusCreditTuningDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusCreditTuningDetail/insert")
	public void insert(@RequestBody MfCusCreditTuningDetail mfCusCreditTuningDetail) throws Exception;


	@RequestMapping("/mfCusCreditTuningDetail/getById")
	public MfCusCreditTuningDetail getById(@RequestBody MfCusCreditTuningDetail mfCusCreditTuningDetail) throws Exception;

	@RequestMapping("/mfCusCreditTuningDetail/delete")
	public void delete(@RequestBody MfCusCreditTuningDetail mfCusCreditTuningDetail) throws Exception;

	@RequestMapping("/mfCusCreditTuningDetail/update")
	public void update(@RequestBody MfCusCreditTuningDetail mfCusCreditTuningDetail) throws Exception;
	
	
}
