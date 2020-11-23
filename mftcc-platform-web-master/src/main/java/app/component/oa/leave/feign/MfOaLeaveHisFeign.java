package app.component.oa.leave.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.leave.entity.MfOaLeaveHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaLeaveHisFeign {
	@RequestMapping("/MfOaLeaveHis/insert")
	public void insert(@RequestBody MfOaLeaveHis mfOaLeaveHis) throws Exception;

	@RequestMapping("/MfOaLeaveHis/delete")
	public void delete(@RequestBody MfOaLeaveHis mfOaLeaveHis) throws Exception;

	@RequestMapping("/MfOaLeaveHis/update")
	public void update(@RequestBody MfOaLeaveHis mfOaLeaveHis) throws Exception;

	@RequestMapping("/MfOaLeaveHis/getById")
	public MfOaLeaveHis getById(@RequestBody MfOaLeaveHis mfOaLeaveHis) throws Exception;

	@RequestMapping("/MfOaLeaveHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
