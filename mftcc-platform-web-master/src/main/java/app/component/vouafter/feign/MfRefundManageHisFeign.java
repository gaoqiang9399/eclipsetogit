package app.component.vouafter.feign;

import app.component.vouafter.entity.MfRefundManageHis;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface MfRefundManageHisFeign {
	@RequestMapping("/MfRefundManageHis/insert")
	public void insert(@RequestBody MfRefundManageHis mfRefundManageHis) throws Exception;

	@RequestMapping("/MfRefundManageHis/delete")
	public void delete(@RequestBody MfRefundManageHis mfRefundManageHis) throws Exception;

	@RequestMapping("/MfRefundManageHis/update")
	public void update(@RequestBody MfRefundManageHis mfRefundManageHis) throws Exception;

	@RequestMapping("/MfRefundManageHis/getById")
	public MfRefundManageHis getById(@RequestBody MfRefundManageHis mfRefundManageHis) throws Exception;

	@RequestMapping("/MfRefundManageHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
