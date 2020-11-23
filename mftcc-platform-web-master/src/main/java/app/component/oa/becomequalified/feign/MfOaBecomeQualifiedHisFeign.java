package app.component.oa.becomequalified.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.becomequalified.entity.MfOaBecomeQualifiedHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaBecomeQualifiedHisFeign {
	@RequestMapping("/MfOaBecomeQualifiedHis/insert")
	public void insert(@RequestBody MfOaBecomeQualifiedHis mfOaBecomeQualifiedHis) throws Exception;

	@RequestMapping("/MfOaBecomeQualifiedHis/delete")
	public void delete(@RequestBody MfOaBecomeQualifiedHis mfOaBecomeQualifiedHis) throws Exception;

	@RequestMapping("/MfOaBecomeQualifiedHis/update")
	public void update(@RequestBody MfOaBecomeQualifiedHis mfOaBecomeQualifiedHis) throws Exception;

	@RequestMapping("/MfOaBecomeQualifiedHis/getById")
	public MfOaBecomeQualifiedHis getById(@RequestBody MfOaBecomeQualifiedHis mfOaBecomeQualifiedHis) throws Exception;

	@RequestMapping("/MfOaBecomeQualifiedHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
