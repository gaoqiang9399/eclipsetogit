package app.component.oa.mattersreported.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.mattersreported.entity.MfOaMattersReportedHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaMattersReportedHisFeign {
	@RequestMapping("/MfOaMattersReportedHis/insert")
	public void insert(@RequestBody MfOaMattersReportedHis mfOaMattersReportedHis) throws Exception;

	@RequestMapping("/MfOaMattersReportedHis/delete")
	public void delete(@RequestBody MfOaMattersReportedHis mfOaMattersReportedHis) throws Exception;

	@RequestMapping("/MfOaMattersReportedHis/update")
	public void update(@RequestBody MfOaMattersReportedHis mfOaMattersReportedHis) throws Exception;

	@RequestMapping("/MfOaMattersReportedHis/getById")
	public MfOaMattersReportedHis getById(@RequestBody MfOaMattersReportedHis mfOaMattersReportedHis) throws Exception;

	@RequestMapping("/MfOaMattersReportedHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
