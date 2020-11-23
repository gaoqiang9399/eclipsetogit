package app.component.pact.extension.feign;

import app.component.pact.extension.entity.MfBusExtensionResultDetail;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Title: MfBusExtensionResultDetailBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Sep 18 11:43:04 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusExtensionResultDetailFeign {

	@RequestMapping(value = "/mfBusExtensionResultDetail/insert")
	public void insert(@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail) throws Exception;

	@RequestMapping(value = "/mfBusExtensionResultDetail/delete")
	public void delete(@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail) throws Exception;

	@RequestMapping(value = "/mfBusExtensionResultDetail/update")
	public void update(@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail) throws Exception;

	@RequestMapping(value = "/mfBusExtensionResultDetail/getById")
	public MfBusExtensionResultDetail getById(@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail)
			throws Exception;

	@RequestMapping(value = "/mfBusExtensionResultDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusExtensionResultDetail/getExtensionResultDetailList")
	public List<MfBusExtensionResultDetail> getExtensionResultDetailList(
			@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail) throws Exception;

	@RequestMapping(value = "/mfBusExtensionResultDetail/getExtensionResultDetailByFincId")
	public MfBusExtensionResultDetail getExtensionResultDetailByFincId(
			@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail) throws Exception;

}
