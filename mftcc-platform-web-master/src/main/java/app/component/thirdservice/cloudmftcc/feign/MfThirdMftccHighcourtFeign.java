package  app.component.thirdservice.cloudmftcc.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.thirdservice.cloudmftcc.entity.MfThirdMftccHighcourt;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfThirdMftccHighcourtBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jan 08 14:16:19 CST 2018
**/

@FeignClient("mftcc-platform-factor")
public interface MfThirdMftccHighcourtFeign {
	
	@RequestMapping(value = "/mfThirdMftccHighcourt/insert")
	public void insert(@RequestBody MfThirdMftccHighcourt mfThirdMftccHighcourt) throws Exception;
	
	@RequestMapping(value = "/mfThirdMftccHighcourt/delete")
	public void delete(@RequestBody MfThirdMftccHighcourt mfThirdMftccHighcourt) throws Exception;
	
	@RequestMapping(value = "/mfThirdMftccHighcourt/update")
	public void update(@RequestBody MfThirdMftccHighcourt mfThirdMftccHighcourt) throws Exception;
	
	@RequestMapping(value = "/mfThirdMftccHighcourt/getById")
	public MfThirdMftccHighcourt getById(@RequestBody MfThirdMftccHighcourt mfThirdMftccHighcourt) throws Exception;
	
	@RequestMapping(value = "/mfThirdMftccHighcourt/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfThirdMftccHighcourt") MfThirdMftccHighcourt mfThirdMftccHighcourt) throws Exception;

	@RequestMapping(value = "/mfThirdMftccHighcourt/getLawQueryCusInfo")
	public Map<String, Object> getLawQueryCusInfo(@RequestParam("cusNo") String cusNo,@RequestParam("relType")  String relType) throws Exception;

	@RequestMapping(value = "/mfThirdMftccHighcourt/getLawCusJSONArray")
	public JSONArray getLawCusJSONArray(@RequestParam("cusNo") String cusNo,@RequestParam("appId")  String appId) throws Exception;

	@RequestMapping(value = "/mfThirdMftccHighcourt/getLawEnforCountByCusNo")
	public int getLawEnforCountByCusNo(@RequestParam("cusNo") String cusNo);

	@RequestMapping(value = "/mfThirdMftccHighcourt/lawEnforQuery")
	String lawEnforQuery(@RequestBody Map<String, String> paramMap) throws Exception;
	
}
