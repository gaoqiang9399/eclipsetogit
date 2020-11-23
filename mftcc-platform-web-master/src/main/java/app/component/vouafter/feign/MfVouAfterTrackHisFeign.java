package app.component.vouafter.feign;

import app.component.vouafter.entity.MfVouAfterTrackHis;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface MfVouAfterTrackHisFeign {
	@RequestMapping("/MfVouAfterTrackHis/insert")
	public void insert(@RequestBody MfVouAfterTrackHis mfVouAfterTrackHis) throws Exception;

	@RequestMapping("/MfVouAfterTrackHis/delete")
	public void delete(@RequestBody MfVouAfterTrackHis mfVouAfterTrackHis) throws Exception;

	@RequestMapping("/MfVouAfterTrackHis/update")
	public void update(@RequestBody MfVouAfterTrackHis mfVouAfterTrackHis) throws Exception;

	@RequestMapping("/MfVouAfterTrackHis/getById")
	public MfVouAfterTrackHis getById(@RequestBody MfVouAfterTrackHis mfVouAfterTrackHis) throws Exception;

	@RequestMapping("/MfVouAfterTrackHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
