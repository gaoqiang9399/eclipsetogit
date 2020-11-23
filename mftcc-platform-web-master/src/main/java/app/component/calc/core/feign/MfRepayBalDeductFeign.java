package  app.component.calc.core.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfRepayBalDeduct;
import app.util.toolkit.Ipage;

/**
* Title: MfRepayBalDeductBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 26 17:28:50 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfRepayBalDeductFeign {
	
	@RequestMapping(value = "/mfRepayBalDeduct/insert")
	public void insert(@RequestBody MfRepayBalDeduct mfRepayBalDeduct) throws Exception;
	
	@RequestMapping(value = "/mfRepayBalDeduct/delete")
	public void delete(@RequestBody MfRepayBalDeduct mfRepayBalDeduct) throws Exception;
	
	@RequestMapping(value = "/mfRepayBalDeduct/update")
	public void update(@RequestBody MfRepayBalDeduct mfRepayBalDeduct) throws Exception;
	
	@RequestMapping(value = "/mfRepayBalDeduct/getById")
	public MfRepayBalDeduct getById(@RequestBody MfRepayBalDeduct mfRepayBalDeduct) throws Exception;
	
	@RequestMapping(value = "/mfRepayBalDeduct/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfRepayBalDeduct") MfRepayBalDeduct mfRepayBalDeduct) throws Exception;
    
	/**
	 * 通过fincId 获取结余冲抵表 列表信息
	 * @param mfRepayBalDeduct
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfRepayBalDeduct/getMfRepayBalDeductList")
	public List<MfRepayBalDeduct> getMfRepayBalDeductList(
			MfRepayBalDeduct mfRepayBalDeduct)throws Exception;

	
}
