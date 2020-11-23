package app.component.pact.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.MfBusDeductFail;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusDeductFailBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Sep 09 14:18:17 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusDeductFailFeign {

	@RequestMapping(value = "/mfBusDeductFail/insert")
	public void insert(@RequestBody MfBusDeductFail mfBusDeductFail) throws Exception;

	@RequestMapping(value = "/mfBusDeductFail/delete")
	public void delete(@RequestBody MfBusDeductFail mfBusDeductFail) throws Exception;

	@RequestMapping(value = "/mfBusDeductFail/update")
	public void update(@RequestBody MfBusDeductFail mfBusDeductFail) throws Exception;

	@RequestMapping(value = "/mfBusDeductFail/getById")
	public MfBusDeductFail getById(@RequestBody MfBusDeductFail mfBusDeductFail) throws Exception;

	@RequestMapping(value = "/mfBusDeductFail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfBusDeductFail") MfBusDeductFail mfBusDeductFail) throws Exception;

	@RequestMapping(value = "/mfBusDeductFail/findWithFincInfoByPage")
	public Ipage findWithFincInfoByPage(@RequestBody Ipage ipage)
			throws Exception;

	@RequestMapping(value = "/mfBusDeductFail/deleteFake")
	public void deleteFake(@RequestBody MfBusDeductFail mfBusDeductFail) throws Exception;

	@RequestMapping(value = "/mfBusDeductFail/doDeductByOne")
	public Map<String, Object> doDeductByOne(@RequestBody MfBusDeductFail mfBusDeductFail) throws Exception;

}
