package  app.component.collateral.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfReceivablesTransferApproHis;
import app.util.toolkit.Ipage;

/**
* Title: MfReceivablesTransferApproHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu May 11 11:24:37 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReceivablesTransferApproHisFeign {
	
	@RequestMapping(value = "/mfReceivablesTransferApproHis/insert")
	public void insert(@RequestBody MfReceivablesTransferApproHis mfReceivablesTransferApproHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesTransferApproHis/delete")
	public void delete(@RequestBody MfReceivablesTransferApproHis mfReceivablesTransferApproHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesTransferApproHis/update")
	public void update(@RequestBody MfReceivablesTransferApproHis mfReceivablesTransferApproHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesTransferApproHis/getById")
	public MfReceivablesTransferApproHis getById(@RequestBody MfReceivablesTransferApproHis mfReceivablesTransferApproHis) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesTransferApproHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReceivablesTransferApproHis") MfReceivablesTransferApproHis mfReceivablesTransferApproHis) throws Exception;
	
}
