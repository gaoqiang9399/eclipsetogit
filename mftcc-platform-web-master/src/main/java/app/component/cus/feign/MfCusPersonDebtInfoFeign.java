package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonDebtInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonDebtInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Apr 11 09:20:57 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonDebtInfoFeign {
	@RequestMapping("/mfCusPersonDebtInfo/insert")
	public void insert(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonDebtInfo/insertForApp")
	public MfCusPersonDebtInfo insertForApp(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonDebtInfo/delete")
	public void delete(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonDebtInfo/update")
	public void update(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonDebtInfo/getById")
	public MfCusPersonDebtInfo getById(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonDebtInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusPersonDebtInfo/findMfCusPersonDebtInfoList")
	public List<MfCusPersonDebtInfo> findMfCusPersonDebtInfoList(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;


	@RequestMapping("/mfCusPersonDebtInfo/findPersonDebInfoByPage")
	public Ipage findPersonDebInfoByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusPersonDebtInfo/getCusDebtSum")
	public List<MfCusPersonDebtInfo> getCusDebtSum(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;





}
