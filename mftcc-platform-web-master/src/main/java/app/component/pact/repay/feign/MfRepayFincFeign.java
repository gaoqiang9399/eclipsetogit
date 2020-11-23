package app.component.pact.repay.feign;

import app.component.pact.entity.MfRepayFinc;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Title: MfRepayFincBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 14 10:46:31 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfRepayFincFeign {

	@RequestMapping(value = "/mfRepayFinc/insert")
	public void insert(@RequestBody MfRepayFinc mfRepayFinc)throws Exception;

	@RequestMapping(value = "/mfRepayFinc/delete")
	public void delete(@RequestBody MfRepayFinc mfRepayFinc) throws Exception;

	@RequestMapping(value = "/mfRepayFinc/update")
	public void update(@RequestBody MfRepayFinc mfRepayFinc)throws Exception;

	@RequestMapping(value = "/mfRepayFinc/getById")
	public MfRepayFinc getById(@RequestBody MfRepayFinc mfRepayFinc) throws Exception;

	@RequestMapping(value = "/mfRepayFinc/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfRepayFinc/getByRepayAppId")
	public List<MfRepayFinc> getByRepayAppId(@RequestBody MfRepayFinc mfRepayFinc) throws Exception;
}
