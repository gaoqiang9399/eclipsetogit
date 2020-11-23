package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusProfitInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusProfitInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 04 09:03:12 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusProfitInfoFeign {
	
	@RequestMapping("/mfCusProfitInfo/insert")
	public void insert(@RequestBody MfCusProfitInfo mfCusProfitInfo) throws Exception;
	
	@RequestMapping("/mfCusProfitInfo/delete")
	public void delete(@RequestBody MfCusProfitInfo mfCusProfitInfo) throws Exception;
	
	@RequestMapping("/mfCusProfitInfo/update")
	public void update(@RequestBody MfCusProfitInfo mfCusProfitInfo) throws Exception;
	
	@RequestMapping("/mfCusProfitInfo/getById")
	public MfCusProfitInfo getById(@RequestBody MfCusProfitInfo mfCusProfitInfo) throws Exception;
	
	@RequestMapping("/mfCusProfitInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
