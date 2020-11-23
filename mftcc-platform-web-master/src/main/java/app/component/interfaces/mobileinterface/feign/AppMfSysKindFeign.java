package  app.component.interfaces.mobileinterface.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.prdct.entity.MfSysKind;
import app.util.toolkit.Ipage;


/**
* Title: MfSysKindBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 05 14:31:11 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface AppMfSysKindFeign{
	
	@RequestMapping(value = "/appMfSysKind/getById")
	public Map<String, Object> getById(@RequestBody  String kindNo) throws Exception;

	@RequestMapping(value = "/appMfSysKind/findByPage")
	public List<Map<String, Object>> findByPage(@RequestBody  Ipage ipage,@RequestParam("mfSysKind") MfSysKind mfSysKind) throws Exception;

	@RequestMapping(value = "/appMfSysKind/getFrontById")
	public Map<String, Object> getFrontById(@RequestBody  String kindNo) throws Exception;

	@RequestMapping(value = "/appMfSysKind/findFrontByPage")
	public List<Map<String, Object>> findFrontByPage(@RequestBody  Ipage ipg,@RequestParam("mfSysKind") MfSysKind mfSysKind) throws Exception;
	

	
}
