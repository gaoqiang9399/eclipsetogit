package app.component.oa.human.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.human.entity.MfOaHumanResourcesHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaHumanResourcesHisFeign {
	@RequestMapping("/MfOaHumanResourcesHis/insert")
	public void insert(@RequestBody MfOaHumanResourcesHis mfOaHumanResourcesHis) throws Exception;

	@RequestMapping("/MfOaHumanResourcesHis/delete")
	public void delete(@RequestBody MfOaHumanResourcesHis mfOaHumanResourcesHis) throws Exception;

	@RequestMapping("/MfOaHumanResourcesHis/update")
	public void update(@RequestBody MfOaHumanResourcesHis mfOaHumanResourcesHis) throws Exception;

	@RequestMapping("/MfOaHumanResourcesHis/getById")
	public MfOaHumanResourcesHis getById(@RequestBody MfOaHumanResourcesHis mfOaHumanResourcesHis) throws Exception;

	@RequestMapping("/MfOaHumanResourcesHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}