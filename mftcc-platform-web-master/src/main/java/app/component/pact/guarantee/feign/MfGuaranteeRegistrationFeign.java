package app.component.pact.guarantee.feign;

import app.component.pact.guarantee.entity.MfGuaranteeRegistration;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface MfGuaranteeRegistrationFeign {
	@RequestMapping("/mfGuaranteeRegistration/insert")
	public void insert(@RequestBody MfGuaranteeRegistration mfGuaranteeRegistration) throws Exception;

	@RequestMapping("/mfGuaranteeRegistration/delete")
	public void delete(@RequestBody MfGuaranteeRegistration mfGuaranteeRegistration) throws Exception;

	@RequestMapping("/mfGuaranteeRegistration/update")
	public void update(@RequestBody MfGuaranteeRegistration mfGuaranteeRegistration) throws Exception;

	@RequestMapping("/mfGuaranteeRegistration/getById")
	public MfGuaranteeRegistration getById(@RequestBody MfGuaranteeRegistration mfGuaranteeRegistration) throws Exception;

	@RequestMapping("/mfGuaranteeRegistration/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfGuaranteeRegistration/findMaintainInfoByPage")
	Ipage findMaintainInfoByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfGuaranteeRegistration/findLiftByPage")
	Ipage findLiftByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfGuaranteeRegistration/findReceivedByPage")
    Ipage findReceivedByPage(@RequestBody Ipage ipage)throws Exception;

	@RequestMapping("/mfGuaranteeRegistration/updateStsTaskInfo")
    void updateStsTaskInfo(@RequestParam("appId") String appId)throws Exception;
}
