package  app.component.collateral.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfReceivablesDisputedAppHis;
import app.util.toolkit.Ipage;

/**
* Title: MfReceivablesDisputedAppHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 15 18:31:05 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReceivablesDisputedAppHisFeign {
	
	@RequestMapping(value = "/mfReceivablesDisputedAppHis/insert")
	public void insert(@RequestBody MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesDisputedAppHis/delete")
	public void delete(@RequestBody MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesDisputedAppHis/update")
	public void update(@RequestBody MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesDisputedAppHis/getById")
	public MfReceivablesDisputedAppHis getById(@RequestBody MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesDisputedAppHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReceivablesDisputedAppHis") MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis) throws Exception;
	
}
