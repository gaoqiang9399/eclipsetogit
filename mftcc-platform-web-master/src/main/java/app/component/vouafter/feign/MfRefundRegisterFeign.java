package app.component.vouafter.feign;

import app.component.vouafter.entity.MfRefundRegister;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfRefundRegisterFeign {
	@RequestMapping("/MfRefundRegister/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfRefundRegister/insert")
	public void insert(@RequestBody MfRefundRegister mfRefundRegister) throws Exception;

	@RequestMapping("/MfRefundRegister/getById")
	public MfRefundRegister getById(@RequestBody MfRefundRegister mfRefundRegister) throws Exception;

	@RequestMapping("/MfRefundRegister/delete")
	public void delete(@RequestBody MfRefundRegister mfRefundRegister) throws Exception;

	@RequestMapping("/MfRefundRegister/update")
	public void update(@RequestBody MfRefundRegister mfRefundRegister) throws Exception;

}
