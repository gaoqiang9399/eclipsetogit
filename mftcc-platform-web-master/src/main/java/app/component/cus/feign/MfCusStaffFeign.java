package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusStaff;
import app.util.toolkit.Ipage;

import java.util.List;

/**
* Title: MfCusStaffBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jun 01 09:33:50 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusStaffFeign {
	@RequestMapping("/mfCusStaff/insert")
	public MfCusStaff insert(@RequestBody MfCusStaff mfCusStaff) throws Exception;
	
	@RequestMapping("/mfCusStaff/delete")
	public void delete(@RequestBody MfCusStaff mfCusStaff) throws Exception;
	
	@RequestMapping("/mfCusStaff/update")
	public MfCusStaff update(@RequestBody MfCusStaff mfCusStaff) throws Exception;
	
	@RequestMapping("/mfCusStaff/getById")
	public MfCusStaff getById(@RequestBody MfCusStaff mfCusStaff) throws Exception;

	@RequestMapping("/mfCusStaff/getByCusNoAndYear")
	public List<MfCusStaff> getByCusNoAndYear(@RequestBody MfCusStaff mfCusStaff) throws Exception;

	@RequestMapping("/mfCusStaff/getByCusNoAndYearExcludeSelf")
	public List<MfCusStaff> getByCusNoAndYearExcludeSelf(@RequestBody MfCusStaff mfCusStaff) throws Exception;
	
	@RequestMapping("/mfCusStaff/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
