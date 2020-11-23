package app.component.cus.feign;


import app.component.cus.entity.MfCusLegalinfo;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient("mftcc-platform-factor")
public interface MfCusLegalinfoFeign {
	@RequestMapping("/mfCusLegalinfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusLegalinfo/insert")
	public void insert(@RequestBody MfCusLegalinfo mfCusLegalinfo) throws Exception;

	@RequestMapping("/mfCusLegalinfo/getById")
	public MfCusLegalinfo getById(@RequestBody MfCusLegalinfo mfCusLegalinfo) throws Exception;

	@RequestMapping("/mfCusLegalinfo/delete")
	public void delete(@RequestBody MfCusLegalinfo mfCusLegalinfo) throws Exception;

	@RequestMapping("/mfCusLegalinfo/update")
	public void update(@RequestBody MfCusLegalinfo mfCusLegalinfo) throws Exception;

}
