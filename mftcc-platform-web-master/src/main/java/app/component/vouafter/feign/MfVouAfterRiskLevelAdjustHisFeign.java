package app.component.vouafter.feign;

import app.component.vouafter.entity.MfVouAfterRiskLevelAdjustHis;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface MfVouAfterRiskLevelAdjustHisFeign {
	@RequestMapping("/MfVouAfterRiskLevelAdjustHis/insert")
	public void insert(@RequestBody MfVouAfterRiskLevelAdjustHis mfVouAfterRiskLevelAdjustHis) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjustHis/delete")
	public void delete(@RequestBody MfVouAfterRiskLevelAdjustHis mfVouAfterRiskLevelAdjustHis) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjustHis/update")
	public void update(@RequestBody MfVouAfterRiskLevelAdjustHis mfVouAfterRiskLevelAdjustHis) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjustHis/getById")
	public MfVouAfterRiskLevelAdjustHis getById(@RequestBody MfVouAfterRiskLevelAdjustHis mfVouAfterRiskLevelAdjustHis) throws Exception;

	@RequestMapping("/MfVouAfterRiskLevelAdjustHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
