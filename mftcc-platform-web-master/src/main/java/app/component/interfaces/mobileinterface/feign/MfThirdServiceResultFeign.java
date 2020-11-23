package  app.component.interfaces.mobileinterface.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfThirdServiceResult;
import app.util.toolkit.Ipage;

/**
* Title: MfThirdServiceResultBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jul 27 20:48:06 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfThirdServiceResultFeign{
	
	@RequestMapping(value = "/mfThirdServiceResult/insert")
	public void insert(@RequestBody  MfThirdServiceResult mfThirdServiceResult) throws Exception;

	@RequestMapping(value = "/mfThirdServiceResult/delete")
	public void delete(@RequestBody  MfThirdServiceResult mfThirdServiceResult) throws Exception;

	@RequestMapping(value = "/mfThirdServiceResult/update")
	public void update(@RequestBody  MfThirdServiceResult mfThirdServiceResult) throws Exception;

	@RequestMapping(value = "/mfThirdServiceResult/getById")
	public MfThirdServiceResult getById(@RequestBody  MfThirdServiceResult mfThirdServiceResult) throws Exception;

	@RequestMapping(value = "/mfThirdServiceResult/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("mfThirdServiceResult") MfThirdServiceResult mfThirdServiceResult) throws Exception;
	
	
	
}
