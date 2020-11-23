package  app.component.auth.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.auth.entity.MfCusCreditContractDetail;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCreditContractDetailBo.java
* Description:客户授信申请业务操作控制
* @author:LJW
* @Mon Feb 27 10:43:09 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditContractDetailFeign {
	
	@RequestMapping(value = "/mfCusCreditContractDetail/insert")
	public MfCusCreditContractDetail insert(@RequestBody MfCusCreditContractDetail mfCusCreditContractDetail) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditContractDetail/delete")
	public void delete(@RequestBody MfCusCreditContractDetail mfCusCreditContractDetail) throws Exception;
	

	@RequestMapping(value = "/mfCusCreditContractDetail/update")
	public void update(@RequestBody MfCusCreditContractDetail mfCusCreditContractDetail) throws Exception;
	
	
	@RequestMapping(value = "/mfCusCreditContractDetail/getById")
	public MfCusCreditContractDetail getById(@RequestBody MfCusCreditContractDetail mfCusCreditContractDetail) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditContractDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditContractDetail") MfCusCreditContractDetail mfCusCreditContractDetail) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditContractDetail/getMfCusCreditContractDetailList")
	public List<MfCusCreditContractDetail> getMfCusCreditContractDetailList(@RequestBody  MfCusCreditContractDetail mfCusCreditContractDetail) throws Exception;
	/**
	 * 
	 * 方法描述： 验证申请金额是否大于资金机构总额的及资金机构项目额度
	 * 
	 * @param childPactId
	 * @param appAmt
	 * @return
	 * @throws Exception
	 * Map<String, Object>
	 * @author 沈浩兵
	 * @date 2018年4月11日 下午2:23:29
	 *//*
	@RequestMapping(value = "/mfCusCreditContractDetail/checkCusNoFundCreditAmt")
	public Map<String, Object> checkCusNoFundCreditAmt(@RequestParam("childPactId") String childPactId,@RequestParam("appAmt") Double appAmt) throws Exception;
	*/
	@RequestMapping(value = "/mfCusCreditContractDetail/findByCreditAppId")
	public List<MfCusCreditContractDetail> findByCreditAppId(@RequestParam("appId") String appId);
}
