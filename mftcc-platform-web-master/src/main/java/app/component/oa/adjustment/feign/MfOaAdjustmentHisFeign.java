package app.component.oa.adjustment.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.adjustment.entity.MfOaAdjustmentHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaAdjustmentHisFeign {
	@RequestMapping("/MfOaAdjustmentHis/insert")
	public void insert(@RequestBody MfOaAdjustmentHis mfOaAdjustmentHis) throws Exception;

	@RequestMapping("/MfOaAdjustmentHis/delete")
	public void delete(@RequestBody MfOaAdjustmentHis mfOaAdjustmentHis) throws Exception;

	@RequestMapping("/MfOaAdjustmentHis/update")
	public void update(@RequestBody MfOaAdjustmentHis mfOaAdjustmentHis) throws Exception;

	@RequestMapping("/MfOaAdjustmentHis/getById")
	public MfOaAdjustmentHis getById(@RequestBody MfOaAdjustmentHis mfOaAdjustmentHis) throws Exception;

	@RequestMapping("/MfOaAdjustmentHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
