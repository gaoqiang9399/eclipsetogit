package  app.component.interfaces.mobileinterface.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfAppCusIntegrity;
import app.util.toolkit.Ipage;

/**
* Title: MfAppCusIntegrityBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Nov 11 10:50:04 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfAppCusIntegrityFeign{
	
	@RequestMapping(value = "/mfAppCusIntegrity/insert")
	public void insert(@RequestBody  MfAppCusIntegrity mfAppCusIntegrity) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/delete")
	public void delete(@RequestBody  MfAppCusIntegrity mfAppCusIntegrity) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/update")
	public void update(@RequestBody  MfAppCusIntegrity mfAppCusIntegrity) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/getById")
	public MfAppCusIntegrity getById(@RequestBody  MfAppCusIntegrity mfAppCusIntegrity) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("mfAppCusIntegrity") MfAppCusIntegrity mfAppCusIntegrity) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/updateIntegrity")
	public Map<String, Object> updateIntegrity(@RequestBody  MfAppCusIntegrity mfAppCusIntegrity) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/doCheckIntegrity")
	public Map<String, Object> doCheckIntegrity(@RequestBody  String cusNo,@RequestParam("cheackItems") String cheackItems) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/doCheckIntegrityForPaid")
	public Map<String, Object> doCheckIntegrityForPaid(@RequestBody  String cusNo,@RequestParam("cheackItems") String cheackItems) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/doCheckIntegrityForOld")
	public Map<String, Object> doCheckIntegrityForOld(@RequestBody  String cusNo,@RequestParam("cheackItems") String cheackItems) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/doCheckIntegrityForWx")
	public Map<String, Object> doCheckIntegrityForWx(@RequestBody  String cusNo,@RequestParam("cheackItems") String cheackItems) throws Exception;

	@RequestMapping(value = "/mfAppCusIntegrity/getAppCusIntegrity")
	public Map<String, Object> getAppCusIntegrity(@RequestBody  String cusNo) throws Exception;
	
	
}
