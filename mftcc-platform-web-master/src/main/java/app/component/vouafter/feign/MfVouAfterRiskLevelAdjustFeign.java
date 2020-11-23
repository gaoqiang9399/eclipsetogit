package app.component.vouafter.feign;

import app.component.vouafter.entity.MfVouAfterRiskLevelAdjust;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfVouAfterRiskLevelAdjustFeign {
	@RequestMapping("/MfVouAfterRiskLevelAdjust/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjust/insert")
	public void insert(@RequestBody MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjust/submitProcess")
	public MfVouAfterRiskLevelAdjust submitProcess(@RequestBody MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjust/getById")
	public MfVouAfterRiskLevelAdjust getById(@RequestBody MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjust/delete")
	public void delete(@RequestBody MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjust/update")
	public void update(@RequestBody MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjust/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjust/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
                           @RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjust/getMultiBusList")
    List<MfVouAfterRiskLevelAdjust> getMultiBusList(@RequestBody MfVouAfterRiskLevelAdjust mfVouAfterRiskLevelAdjust)throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjust/findVouAfterRiskLevelAdjustByPage")
    Ipage findVouAfterRiskLevelAdjustByPage(@RequestBody Ipage ipage)throws Exception;
}
