package app.component.pact.feign;

import app.component.pact.entity.MfRepayApply;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Title: MfBusCreditorBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Sep 25 17:45:15 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfRepayApplyFeign {

	@RequestMapping(value = "/mfRepayApply/insert")
	public void insert(@RequestBody Map<String, Object> parmMap) throws Exception;
	@RequestMapping(value = "/mfRepayApply/delete")
	public void delete(@RequestBody MfRepayApply mfRepayApply) throws Exception;
	@RequestMapping(value = "/mfRepayApply/update")
	public void update(@RequestBody MfRepayApply mfRepayApply) throws Exception;
	@RequestMapping(value = "/mfRepayApply/getById")
	public MfRepayApply getById(@RequestBody MfRepayApply mfRepayApply) throws Exception;
	@RequestMapping(value = "/mfRepayApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 *
	 * 方法描述： 还款确认说明记录
	 * @return
	 * @throws Exception
	 * String
	 * @author cd
	 * @date 2019-6-26 09.04
	 */
	@RequestMapping(value = "/mfRepayApply/insertConfirmData")
	public void insertConfirmData(@RequestBody MfRepayApply mfRepayApply)throws Exception;
}
