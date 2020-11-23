package app.component.pact.guarantee.feign;

import app.component.pact.guarantee.entity.MfGuaranteeLift;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfGuaranteeLiftFeign {
	@RequestMapping("/mfGuaranteeLift/insert")
	public MfGuaranteeLift insert(@RequestBody MfGuaranteeLift mfGuaranteeLift) throws Exception;

	@RequestMapping("/mfGuaranteeLift/delete")
	public void delete(@RequestBody MfGuaranteeLift mfGuaranteeLift) throws Exception;

	@RequestMapping("/mfGuaranteeLift/update")
	public void update(@RequestBody MfGuaranteeLift mfGuaranteeLift) throws Exception;

	@RequestMapping("/mfGuaranteeLift/getById")
	public MfGuaranteeLift getById(@RequestBody MfGuaranteeLift mfGuaranteeLift) throws Exception;

	@RequestMapping("/mfGuaranteeLift/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfGuaranteeLift/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
						   @RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping("/mfGuaranteeLift/findRenewInsuranceByPage")
    Ipage findRenewInsuranceByPage(@RequestBody Ipage ipage)throws Exception;

	@RequestMapping("/mfGuaranteeLift/findLogoutReopenByPage")
	Ipage findLogoutReopenByPage(@RequestBody Ipage ipage)throws Exception;
}
