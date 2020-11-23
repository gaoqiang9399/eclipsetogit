package app.component.pledge.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pledge.entity.MfBusFund;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfBusFundFeign {
	@RequestMapping(value = "/mfBusFundController/insert")
	public MfBusFund insert(@RequestBody MfBusFund mfBusFund) throws Exception;
	
	@RequestMapping(value = "/mfBusFundController/update")
	public String update(@RequestBody MfBusFund mfBusFund) throws Exception;
	
	@RequestMapping(value = "/mfBusFundController/delete")
	public void delete(@RequestBody MfBusFund mfBusFund) throws Exception;
	
	@RequestMapping(value = "/mfBusFundController/getMfBusFundById")
	public MfBusFund getMfBusFundById(@RequestBody MfBusFund mfBusFund) throws Exception;
	
	@RequestMapping(value = "/mfBusFundController/getMfBusFundList")
	public List<MfBusFund> getMfBusFundList(@RequestBody MfBusFund mfBusFund) throws Exception;
	
	@RequestMapping(value = "/mfBusFundController/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
