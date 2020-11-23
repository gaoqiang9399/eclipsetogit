package app.component.pledge.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pledge.entity.MfBusFundDetail;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfBusFundDetailFeign {
	@RequestMapping(value = "/mfBusFundDetailController/insert")
	public MfBusFundDetail insert(@RequestBody MfBusFundDetail mfBusFundDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFundDetailController/update")
	public String update(@RequestBody MfBusFundDetail mfBusFundDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFundDetailController/delete")
	public void delete(@RequestBody MfBusFundDetail mfBusFundDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFundDetailController/getMfBusFundDetailById")
	public MfBusFundDetail getMfBusFundDetailById(@RequestBody MfBusFundDetail mfBusFundDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFundDetailController/getMfBusFundDetailList")
	public List<MfBusFundDetail> getMfBusFundDetailList(@RequestBody MfBusFundDetail mfBusFundDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFundDetailController/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
