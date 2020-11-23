package app.component.oa.fulltopart.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.fulltopart.entity.MfOaFullToPartHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaFullToPartHisFeign {
	@RequestMapping("/MfOaFullToPartHis/insert")
	public void insert(@RequestBody MfOaFullToPartHis mfOaFullToPartHis) throws Exception;

	@RequestMapping("/MfOaFullToPartHis/delete")
	public void delete(@RequestBody MfOaFullToPartHis mfOaFullToPartHis) throws Exception;

	@RequestMapping("/MfOaFullToPartHis/update")
	public void update(@RequestBody MfOaFullToPartHis mfOaFullToPartHis) throws Exception;

	@RequestMapping("/MfOaFullToPartHis/getById")
	public MfOaFullToPartHis getById(@RequestBody MfOaFullToPartHis mfOaFullToPartHis) throws Exception;

	@RequestMapping("/MfOaFullToPartHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}