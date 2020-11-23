package  app.component.collateral.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfReceivablesRebateAppHis;
import app.util.toolkit.Ipage;

/**
* Title: MfReceivablesRebateAppHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 15 11:31:02 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReceivablesRebateAppHisFeign {
	
	@RequestMapping(value = "/mfReceivablesRebateAppHis/insert")
	public void insert(@RequestBody MfReceivablesRebateAppHis mfReceivablesRebateAppHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesRebateAppHis/delete")
	public void delete(@RequestBody MfReceivablesRebateAppHis mfReceivablesRebateAppHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesRebateAppHis/update")
	public void update(@RequestBody MfReceivablesRebateAppHis mfReceivablesRebateAppHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesRebateAppHis/getById")
	public MfReceivablesRebateAppHis getById(@RequestBody MfReceivablesRebateAppHis mfReceivablesRebateAppHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesRebateAppHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReceivablesRebateAppHis") MfReceivablesRebateAppHis mfReceivablesRebateAppHis) throws Exception;
	
}
