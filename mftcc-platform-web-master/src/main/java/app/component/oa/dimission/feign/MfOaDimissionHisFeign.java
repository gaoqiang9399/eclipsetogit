package app.component.oa.dimission.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.dimission.entity.MfOaDimissionHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaDimissionHisFeign {
	@RequestMapping("/MfOaDimissionHis/insert")
	public void insert(@RequestBody MfOaDimissionHis mfOaDimissionHis) throws Exception;

	@RequestMapping("/MfOaDimissionHis/delete")
	public void delete(@RequestBody MfOaDimissionHis mfOaDimissionHis) throws Exception;

	@RequestMapping("/MfOaDimissionHis/update")
	public void update(@RequestBody MfOaDimissionHis mfOaDimissionHis) throws Exception;

	@RequestMapping("/MfOaDimissionHis/getById")
	public MfOaDimissionHis getById(@RequestBody MfOaDimissionHis mfOaDimissionHis) throws Exception;

	@RequestMapping("/MfOaDimissionHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
