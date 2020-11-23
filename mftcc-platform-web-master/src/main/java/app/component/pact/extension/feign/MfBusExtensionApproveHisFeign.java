package app.component.pact.extension.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.extension.entity.MfBusExtensionApproveHis;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusExtensionApproveHisBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 06 11:07:23 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusExtensionApproveHisFeign {
	@RequestMapping(value = "/mfBusExtensionApproveHis/insert")
	public void insert(@RequestBody MfBusExtensionApproveHis mfBusExtensionApproveHis) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApproveHis/delete")
	public void delete(@RequestBody MfBusExtensionApproveHis mfBusExtensionApproveHis) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApproveHis/update")
	public void update(@RequestBody MfBusExtensionApproveHis mfBusExtensionApproveHis) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApproveHis/getById")
	public MfBusExtensionApproveHis getById(@RequestBody MfBusExtensionApproveHis mfBusExtensionApproveHis)
			throws Exception;

	@RequestMapping(value = "/mfBusExtensionApproveHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfBusExtensionApproveHis") MfBusExtensionApproveHis mfBusExtensionApproveHis)
			throws Exception;

}
