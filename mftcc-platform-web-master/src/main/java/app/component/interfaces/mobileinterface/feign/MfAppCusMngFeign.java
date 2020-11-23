package  app.component.interfaces.mobileinterface.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfAppCusMng;
import app.util.toolkit.Ipage;

/**
* Title: MfCusMngBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Sep 05 10:01:02 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfAppCusMngFeign{
	
	@RequestMapping(value = "/mfAppCusMng/insert")
	public Map<String, Object> insert(@RequestBody  MfAppCusMng mfCusMng) throws Exception;

	@RequestMapping(value = "/mfAppCusMng/delete")
	public void delete(@RequestBody  MfAppCusMng mfCusMng) throws Exception;

	@RequestMapping(value = "/mfAppCusMng/update")
	public void update(@RequestBody  MfAppCusMng mfCusMng) throws Exception;

	@RequestMapping(value = "/mfAppCusMng/getById")
	public MfAppCusMng getById(@RequestBody  MfAppCusMng mfCusMng) throws Exception;

	@RequestMapping(value = "/mfAppCusMng/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfAppCusMng/getAllMfCusMng")
	public List<MfAppCusMng> getAllMfCusMng() throws Exception;

	@RequestMapping(value = "/mfAppCusMng/getMfCusMngByCusNo")
	public MfAppCusMng getMfCusMngByCusNo(@RequestBody  String cusNo,@RequestParam("cusMngNo") String cusMngNo) throws Exception;

	@RequestMapping(value = "/mfAppCusMng/getByCondition")
	public List<MfAppCusMng> getByCondition(@RequestBody  MfAppCusMng mfAppCusMng) throws Exception;

	@RequestMapping(value = "/mfAppCusMng/getMfAppCusMngByMin")
	public MfAppCusMng getMfAppCusMngByMin(@RequestBody  String channelType) throws Exception;

	@RequestMapping(value = "/mfAppCusMng/getMfAppCusMngByMin")
	public MfAppCusMng getMfAppCusMngByMin() throws Exception;
	
	
	
}
